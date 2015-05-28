package kindlereport.model;

import java.util.List;

public class ReceiveTag {
	protected String name;
	protected List<Integer> tagIdList;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<Integer> getTagIdList() {
		return tagIdList;
	}
	public void setTagIdList(List<Integer> tagIdList) {
		this.tagIdList = tagIdList;
	}
}
