package theegg.me.xk;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.stereotype.Component;

@Component
public class DefaultApplyService implements ApplyService {

	private AtomicInteger count = new AtomicInteger(0);

	private ConcurrentHashMap<Integer, Apply> currentList = new ConcurrentHashMap<>();

	private ConcurrentHashMap<Integer, Apply> historyList = new ConcurrentHashMap<>();

	private static final String LOGIN_URL = "http://jwxt.sdu.edu.cn:7890/pls/wwwbks/bks_login2.login";
	private static final String APPLY_URL = "http://jwxt.sdu.edu.cn:7890/pls/wwwbks/xk.CourseInput";

	public DefaultApplyService() {
		HttpURLConnection.setFollowRedirects(false);

		doRealApply();
	}

	@Override
	public Collection<Apply> getCurrentList() {
		return currentList.values();
	}

	@Override
	public Collection<Apply> getHistoryList() {
		return historyList.values();
	}

	@Override
	public boolean apply(Apply apply) {

		int id = count.getAndAdd(1);
		apply.setId(id);
		currentList.put(id, apply);

		return true;
	}

	@Override
	public boolean cancel(int id) {

		Apply apply = currentList.remove(id);
		historyList.put(id, apply);

		return false;
	}

	@Override
	public boolean redo(int id) {

		Apply apply = historyList.remove(id);
		currentList.put(id, apply);

		return false;
	}

	private void doRealApply() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				while (true) {
					for (Apply apply : currentList.values()) {
						try {
							realApply(apply.getStuid(), apply.getPasswd(),
									apply.getCourseNumber(),
									apply.getCourseOrder());
						} catch (IOException e) {
							e.printStackTrace();
						} 
					}
				}
			}
		}

		).start();
	}

	private void realApply(String stuid, String pwd, String p_qxrxk,
			String p_qxrxk_kxh) throws IOException {

		URL url = new URL(LOGIN_URL);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setDoOutput(true);
		con.setRequestMethod("POST");

		StringBuffer pString = new StringBuffer();
		pString.append("stuid=" + stuid);
		pString.append("&pwd=" + pwd);

		OutputStream os = con.getOutputStream();
		os.write(pString.toString().getBytes());
		os.close();

		con.connect();
		String cookie = con.getHeaderField("Set-Cookie");
		cookie += ";expire=Session;domain=jwxt.sdu.edu.cn";

		url = new URL(APPLY_URL);
		con = (HttpURLConnection) url.openConnection();
		con.setRequestProperty("Cookie", cookie);
		con.setDoOutput(true);
		con.setRequestMethod("POST");

		StringBuffer courseString = new StringBuffer();
		courseString.append("p_qxrxk=" + p_qxrxk);
		courseString.append("&p_qxrxk_kxh=" + p_qxrxk_kxh);

		System.out.println(p_qxrxk);
		os = con.getOutputStream();
		os.write(courseString.toString().getBytes());
		os.close();

		InputStream in = con.getInputStream();

		BufferedReader breader = new BufferedReader(new InputStreamReader(in));

		while (true) {
			String str = breader.readLine();
			if (str == null)
				break;
			else {
				break;
			}
		}
	}

}
