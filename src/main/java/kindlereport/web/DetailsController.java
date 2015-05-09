package kindlereport.web;

import java.util.List;

import kindlereport.dao.CommentMapper;
import kindlereport.dao.KindleMapper;
import kindlereport.model.Comment;
import kindlereport.model.Kindle;
import kindlereport.service.MyBatisService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("items")
public class DetailsController {
	private static final String DETAILS_PAGE_URL = "items";
	private static final String LIST_PAGE_URL = "list";
	
	@Autowired
	private KindleMapper kindleMapper;
	@Autowired
	private CommentMapper commentMapper;
	private MyBatisService myBatisService = new MyBatisService();
	
	@RequestMapping(value = "{asin}", method = RequestMethod.GET)
	public String ajax(
			@PathVariable String asin,
			@RequestParam(value = "ajaxflg", required = false, defaultValue = "0") int ajaxflg,
			Model model) {
		Kindle kindle = kindleMapper.selectKindleByAsin(asin);
		model.addAttribute("kindle", kindle);
		List<Comment> commentList = commentMapper.selectComment(asin);
		model.addAttribute("commentList", commentList);
		model.addAttribute("LIST_PAGE_URL", LIST_PAGE_URL);
		if(ajaxflg == 1){
			return "items_content";
		}else{
			return "items";
		}
	}
	
}
