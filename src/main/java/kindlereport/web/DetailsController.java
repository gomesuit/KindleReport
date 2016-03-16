package kindlereport.web;

import kindlereport.mapper.CommentMapper;
import kindlereport.mapper.KindleMapper;
import kindlereport.mapper.TagMapper;

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
		
		// kindle
		model.addAttribute("kindle", kindleMapper.selectKindleByAsin(asin));
		
		// comment
		model.addAttribute("commentList", commentMapper.selectComment(asin));
		
		// tagList
		model.addAttribute("tagList", tagMapper.selectTagsByAsin(asin));
		
		if(ajaxflg == 1){
			// ajaxリクエストの場合はコンテンツのみ返す
			return "items_content";
		}else{
			return "items";
		}
	}
	
}
