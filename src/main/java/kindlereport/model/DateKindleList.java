package kindlereport.model;

import java.util.List;

public class DateKindleList {
	protected String releaseDate;
	protected String dateString;
    protected List<Kindle> kindleList;
	protected String sidebarId;
    
	public String getReleaseDate() {
		return releaseDate;
	}
	public void setReleaseDate(String releaseDate) {
		this.releaseDate = releaseDate;
	}
	public List<Kindle> getKindleList() {
		return kindleList;
	}
	public void setKindleList(List<Kindle> kindleList) {
		this.kindleList = kindleList;
	}
	public String getSidebarId() {
		return sidebarId;
	}
	public void setSidebarId(String sidebarId) {
		this.sidebarId = sidebarId;
	}
	public String getDateString() {
		return dateString;
	}
	public void setDateString(String dateString) {
		this.dateString = dateString;
	}
}
