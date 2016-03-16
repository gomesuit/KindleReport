package kindlereport.web;

import java.util.List;

import kindlereport.mapper.CommentMapper;
import kindlereport.mapper.KindleMapper;
import kindlereport.mapper.TagMapper;
import kindlereport.model.Comment;
import kindlereport.model.KindleDetail;
import kindlereport.model.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class DetailsController {
	@Autowired
	private KindleMapper kindleMapper;
	@Autowired
	private CommentMapper commentMapper;
	@Autowired
	private TagMapper tagMapper;
	
	@RequestMapping(value = "/items/{asin}", method = RequestMethod.GET)
	public String ajax(
			@PathVariable String asin,
			@RequestParam(value = "ajaxflg", required = false, defaultValue = "0") int ajaxflg,
			Model model) {
		KindleDetail kindle = kindleMapper.selectKindleByAsin(asin);
		model.addAttribute("kindle", kindle);
		List<Comment> commentList = commentMapper.selectComment(asin);
		model.addAttribute("commentList", commentList);
		List<Tag> tagList = tagMapper.selectTagsByAsin(asin);
		model.addAttribute("tagList", tagList);
		
		model.addAttribute("LIST_PAGE_URL", "list");
		model.addAttribute("DATELIST_PAGE_URL", "dateList");
		if(ajaxflg == 1){
			return "items" + "_content";
		}else{
			return "items";
		}
	}
	
}
