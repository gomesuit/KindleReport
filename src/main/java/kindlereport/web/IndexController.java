package kindlereport.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import kindlereport.service.KindleService;
import kindlereport.web.util.DateUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class IndexController {
	
	@Autowired
	private KindleService kindleService;

	@RequestMapping("/")
	public String home(Model model, HttpServletRequest request) {
		
		request.setAttribute("pageName", "front");
		return "common_frame";
	}

	@RequestMapping("/list")
	public String list(
			@RequestParam(value = "tagId", required = false) List<Integer> tagId,
			Model model, HttpServletRequest request) {

		if (tagId != null) {
			model.addAttribute("tagList", kindleService.getTagListById(tagId));
		}

		request.setAttribute("pageName", "list");
		return "common_frame";
	}

	@RequestMapping("/dateList")
	public String dateList(
			@RequestParam(value = "ajaxDate", required = false) String ajaxDate,
			@RequestParam(value = "ajaxFlg", required = false, defaultValue = "0") int ajaxflg,
			Model model, HttpServletRequest request) {
		
		model.addAttribute("today", DateUtil.getTodayByString());

		if (ajaxflg == 0) {
			request.setAttribute("pageName", "dateList");
		} else {
			model.addAttribute("dateKindleListList", kindleService.getDateKindleList(ajaxDate));
			request.setAttribute("pageName", "dateList_content");
		}
		return "common_frame";
	}

}
