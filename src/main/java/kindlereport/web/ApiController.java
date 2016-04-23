package kindlereport.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import kindlereport.mapper.KindleMapper;
import kindlereport.model.Comment;
import kindlereport.model.KindleTile;
import kindlereport.model.ReceiveTag;
import kindlereport.model.Tag;
import kindlereport.model.TagMap;
import kindlereport.service.KindleService;
import kindlereport.web.util.DateUtil;

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
public class ApiController {
	private static final Logger logger = LoggerFactory.getLogger(ApiController.class);

	@Autowired
	private KindleMapper kindleMapper;

	@Autowired
	private KindleService kindleService;
	
	@RequestMapping("/api/tile")
	public List<KindleTile> tile(
			@RequestParam(value = "page", required = false, defaultValue = "1") int page,
			@RequestParam(value = "order", required = false, defaultValue = "1") int order,
			@RequestParam(value = "tagId", required = false) List<Integer> tagIdList,
			@RequestParam(value = "limitedFree", required = false, defaultValue = "false") boolean limitedFree,
			Model model) {
		
		int limit = 24;
		int offset = (page - 1) * limit;
		
		List<KindleTile> kindleList = kindleService.getKindleList(limit, offset, order, tagIdList, limitedFree);

		return kindleList;
	}

	@RequestMapping(value = "/api/comment/register", produces = "application/json", method = RequestMethod.POST)
	public int commentRegister(@RequestBody Comment comment, HttpServletRequest request) {
		String ipAddr = request.getRemoteAddr();
		int commentId = kindleService.registComment(comment, ipAddr);
		
		return commentId;
	}

	@RequestMapping(value = "/api/tag/register", produces = "application/json", method = RequestMethod.POST)
	public int tagRegister(@RequestBody TagMap tagMap) {
		int tagId = kindleService.registTag(tagMap);

		return tagId;
	}

	@RequestMapping(value = "/api/tag/delete", produces = "application/json", method = RequestMethod.POST)
	public int tagDeleter(@RequestBody TagMap tagMap) {
		int tagId = kindleService.deleteTag(tagMap);

		return tagId;
	}

	@RequestMapping(value = "/api/tag/select", produces = "application/json", method = RequestMethod.POST)
	public List<Tag> tagSelecter(@RequestBody ReceiveTag receiveTag) {
		List<Tag> tagList = kindleService.getTagListById(receiveTag);

		return tagList;
	}

	@RequestMapping("/api/dateList")
	public List<String> dateList() {
		List<String> dateList = kindleMapper.selectRereaseDateList(DateUtil.getCurrentDate());
		return dateList;
	}
}
