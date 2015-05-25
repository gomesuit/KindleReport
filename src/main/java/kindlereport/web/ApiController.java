package kindlereport.web;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import kindlereport.dao.CommentMapper;
import kindlereport.dao.KindleMapper;
import kindlereport.dao.TagMapper;
import kindlereport.model.Comment;
import kindlereport.model.KindleTile;
import kindlereport.model.Tag;
import kindlereport.model.TagMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api")
public class ApiController {
	private static final Logger logger = LoggerFactory.getLogger(ApiController.class);
	private static final String KINDLE_DATE_FORMAT = "yyyy-MM-dd";
	
	@Autowired
	private KindleMapper kindleMapper;
	@Autowired
	private CommentMapper commentMapper;
	@Autowired
	private TagMapper tagMapper;
	
	@RequestMapping("tile")
	public List<KindleTile> tile(
			@RequestParam(value = "page", required = false, defaultValue = "1") int page,
			@RequestParam(value = "order", required = false, defaultValue = "1") int order,
			@RequestParam(value = "tagId", required = false, defaultValue = "0") int tagId[],
			Model model) {
		int limit = 24;
		int offset = (page - 1) * limit;
		List<KindleTile> kindleList;
		
		Map<String,Object> requestParam = new HashMap<String,Object>();
		requestParam.put("limit", limit);
		requestParam.put("offset", offset);
		requestParam.put("order", order);
		requestParam.put("tagId", tagId[0]);
		
		logger.info("{}", tagId);
		
		if(tagId[0] == 0){
			kindleList = kindleMapper.selectKindleList(requestParam);
		}else{
			kindleList = kindleMapper.selectKindleListByTag(requestParam);
		}
		
		return kindleList;
	}

	@RequestMapping(value = "comment/register", produces = "application/json", method = RequestMethod.POST)
	public int commentRegister(@RequestBody Comment comment, HttpServletRequest request){
		comment.setRegisterDateTime(getCurrentTime());
		comment.setIpAddr(request.getRemoteAddr());
		commentMapper.insertComment(comment);
		return comment.getId();
	}

	@RequestMapping(value = "tag/register", produces = "application/json", method = RequestMethod.POST)
	public int tagRegister(@RequestBody TagMap tagMap){
		Tag tag = tagMapper.selectTagByName(tagMap.getName());
		if(tag == null){
			tag = new Tag();
			tag.setName(tagMap.getName());
			tagMapper.insertTag(tag);
		}
		tagMap.setTagId(tag.getId());
		tagMapper.insertTagMap(tagMap);
		
		return tagMap.getTagId();
	}

	@RequestMapping(value = "tag/delete", produces = "application/json", method = RequestMethod.POST)
	public int tagDeleter(@RequestBody TagMap tagMap){
		tagMapper.deleteTagMap(tagMap);
		if(tagMapper.countTagMap(tagMap.getTagId()) == 0){
			tagMapper.deleteTag(tagMap.getTagId());
		}
		
		return tagMap.getTagId();
	}

    private Date getCurrentTime(){
    	Date date = new Date();
    	
    	return date;
    }
    
    
	@RequestMapping("dateList")
	public List<String> dateList() {
		List<String> dateList = kindleMapper.selectRereaseDateList(getCurrentDate());
		return dateList;
	}

    private String getCurrentDate(){
    	SimpleDateFormat sdf = new SimpleDateFormat(KINDLE_DATE_FORMAT, Locale.JAPAN);
    	
    	Calendar calendar = Calendar.getInstance();
    	calendar.setTime(new Date());
    	calendar.add(Calendar.DAY_OF_MONTH, -3);
    	
    	return sdf.format(calendar.getTime());
    }
}
