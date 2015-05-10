package kindlereport.model;

import java.util.List;

public class DateKindleList {
	protected String releaseDate;
    protected List<List<Kindle>> rowKindleList;
	protected String sidebarId;
    
	public String getReleaseDate() {
		return releaseDate;
	}
	public void setReleaseDate(String releaseDate) {
		this.releaseDate = releaseDate;
	}
	public List<List<Kindle>> getRowKindleList() {
		return rowKindleList;
	}
	public void setRowKindleList(List<List<Kindle>> rowKindleList) {
		this.rowKindleList = rowKindleList;
	}
	public String getSidebarId() {
		return sidebarId;
	}
	public void setSidebarId(String sidebarId) {
		this.sidebarId = sidebarId;
	}
}
