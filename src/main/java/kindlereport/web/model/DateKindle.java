package kindlereport.web.model;

import java.util.List;

import kindlereport.model.KindleTile;

public class DateKindle {
	protected String releaseDate;
	protected String dateString;
    protected List<KindleTile> kindleList;
	protected String sidebarId;
    
	public String getReleaseDate() {
		return releaseDate;
	}
	public void setReleaseDate(String releaseDate) {
		this.releaseDate = releaseDate;
	}
	public List<KindleTile> getKindleList() {
		return kindleList;
	}
	public void setKindleList(List<KindleTile> kindleList) {
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
