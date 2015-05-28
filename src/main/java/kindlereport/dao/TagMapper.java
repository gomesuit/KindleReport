package kindlereport.dao;

import java.util.List;

import kindlereport.model.ReceiveTag;
import kindlereport.model.Tag;
import kindlereport.model.TagMap;

public interface TagMapper {
	void insertTag(Tag tag);
	void insertTagMap(TagMap tagMap);
	List<Tag> selectTagsByAsin(String asin);
	Tag selectTagById(int id);
	List<Tag> selectTagListById(List<Integer> tagId);
	Tag selectTagByName(String name);
	List<Tag> selectTagByNameLike(ReceiveTag receiveTag);
	void deleteTagMap(TagMap tagMap);
	int countTagMap(int tagId);
	void deleteTag(int id);
}
