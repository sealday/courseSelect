package theegg.me.xk;

import org.hibernate.validator.constraints.NotBlank;

public class Apply {

	private int id;
	@NotBlank
	private String stuid;
	@NotBlank
	private String passwd;
	@NotBlank
	private String courseNumber;
	@NotBlank
	private String courseOrder;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getStuid() {
		return stuid;
	}

	public void setStuid(String stuid) {
		this.stuid = stuid;
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	public String getCourseNumber() {
		return courseNumber;
	}

	public void setCourseNumber(String courseNumber) {
		this.courseNumber = courseNumber;
	}

	public String getCourseOrder() {
		return courseOrder;
	}

	public void setCourseOrder(String courseOrder) {
		this.courseOrder = courseOrder;
	}

}
