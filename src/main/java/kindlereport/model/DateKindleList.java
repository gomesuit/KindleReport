package kindlereport.model;

import java.util.List;

public class DateKindleList {
	protected String releaseDate;
    protected List<List<Kindle>> rowKindleList;
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
}
