package kindlereport.model;

import java.util.Date;

public class Comment {
	protected String asin;
	protected int id;
	protected Date registerDateTime;
	protected String content;
	
	public String getAsin() {
		return asin;
	}
	public void setAsin(String asin) {
		this.asin = asin;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Date getRegisterDateTime() {
		return registerDateTime;
	}
	public void setRegisterDateTime(Date registerDateTime) {
		this.registerDateTime = registerDateTime;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
}
