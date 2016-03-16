package kindlereport.web.model;

public class WebSocketReceive {
	protected int sw;
	protected String asin;
	protected int id;
	
	public void setSw(int sw) {
		this.sw = sw;
	}
	public int getSw() {
		return sw;
	}
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
	public boolean isComment() {
		return sw == 1;
	}
	public boolean isTagRegist() {
		return sw == 2;
	}
	public boolean isTagDelete() {
		return sw == 3;
	}
}
