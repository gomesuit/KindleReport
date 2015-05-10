package kindlereport.web;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import kindlereport.dao.CommentMapper;
import kindlereport.dao.KindleMapper;
import kindlereport.model.Comment;
import kindlereport.model.Kindle;
import kindlereport.service.MyBatisService;

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
	
	@Autowired
	private KindleMapper kindleMapper;
	@Autowired
	private CommentMapper commentMapper;
	private MyBatisService myBatisService = new MyBatisService();
	
	@RequestMapping("tile")
	public List<Kindle> tile(
			@RequestParam(value = "page", required = false, defaultValue = "1") int page,
			@RequestParam(value = "order", required = false, defaultValue = "1") int order,
			Model model) {
		int limit = 24;
		int offset = (page - 1) * limit;
		List<Kindle> kindleList = myBatisService.getKindleList(kindleMapper, limit, offset, order);
		return kindleList;
	}

	@RequestMapping(value = "comment/register", produces = "application/json", method = RequestMethod.POST)
	public int commentRegister(@RequestBody Comment comment, HttpServletRequest request){
		comment.setRegisterDateTime(getCurrentTime());
		comment.setIpAddr(request.getRemoteAddr());
		commentMapper.insertComment(comment);
		return comment.getId();
	}

    private Date getCurrentTime(){
    	Date date = new Date();
    	
    	return date;
    }
	
}
