package kindlereport.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kindlereport.mapper.CommentMapper;
import kindlereport.mapper.KindleMapper;
import kindlereport.mapper.TagMapper;
import kindlereport.model.Comment;
import kindlereport.model.KindleTile;
import kindlereport.model.ReceiveTag;
import kindlereport.model.Tag;
import kindlereport.model.TagMap;
import kindlereport.web.model.DateKindle;
import kindlereport.web.util.DateUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class KindleService {	
	@Autowired
	private KindleMapper kindleMapper;
	@Autowired
	private TagMapper tagMapper;
	@Autowired
	private CommentMapper commentMapper;
	
	public List<Tag> getTagListById(List<Integer> tagId){
		return tagMapper.selectTagListById(tagId);
	}
	
	public List<DateKindle> getDateKindleList(String date){
		List<DateKindle> dateKindleList = new ArrayList<>();
		dateKindleList.add(createDateKindleList(date));
		
		return dateKindleList;
	}

	private DateKindle createDateKindleList(String date) {
		DateKindle dateKindleList = new DateKindle();
		dateKindleList.setReleaseDate(date);
		dateKindleList.setDateString(DateUtil.dateFormatter(date));
		dateKindleList.setSidebarId("sidebar" + date);
		dateKindleList.setKindleList(kindleMapper.selectDayKindleList(date));

		return dateKindleList;
	}

	public int registComment(Comment comment, String remoteAddr) {
		comment.setRegisterDateTime(new Date());
		comment.setIpAddr(remoteAddr);
		commentMapper.insertComment(comment);
		
		return comment.getId();
	}
	
	@Transactional
	public int deleteTag(TagMap tagMap) {
		tagMapper.deleteTagMap(tagMap);

		// タグが一つもない場合タグそのものを削除する
		if (tagMapper.countTagMap(tagMap.getTagId()) == 0) {
			tagMapper.deleteTag(tagMap.getTagId());
		}
		
		return tagMap.getTagId();
	}

	@Transactional
	public int registTag(TagMap tagMap) {
		Tag tag = tagMapper.selectTagByName(tagMap.getName());

		// 未登録タグの場合DBに登録する
		if (tag == null) {
			tag = new Tag();
			tag.setName(tagMap.getName());
			tagMapper.insertTag(tag);
		}

		tagMap.setTagId(tag.getId());
		tagMapper.insertTagMap(tagMap);
		
		return tagMap.getTagId();
	}

	public List<Tag> getTagListById(ReceiveTag receiveTag) {
		receiveTag.setName(receiveTag.getName() + "%");
		return tagMapper.selectTagByNameLike(receiveTag);
	}

	public List<KindleTile> getKindleList(
			int limit, int offset, int order,
			List<Integer> tagIdList, boolean limitedFree) {

		Map<String, Object> requestParam = new HashMap<String, Object>();
		requestParam.put("limit", limit);
		requestParam.put("offset", offset);
		requestParam.put("order", order);
		requestParam.put("tagId", tagIdList);
		requestParam.put("limitedFree", limitedFree);

		List<KindleTile> kindleList = null;

		if (tagIdList == null) {
			kindleList = kindleMapper.selectKindleList(requestParam);
		} else {
			kindleList = kindleMapper.selectKindleListByTag(requestParam);
		}
		
		return kindleList;
	}
}
