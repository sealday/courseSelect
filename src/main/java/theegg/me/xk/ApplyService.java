package theegg.me.xk;

import java.util.Collection;

public interface ApplyService {

	Collection<Apply> getCurrentList();

	Collection<Apply> getHistoryList();

	boolean apply(Apply apply);

	boolean cancel(int id);

	boolean redo(int id);

}
