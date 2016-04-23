package kindlereport.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import kindlereport.model.KindleTile;
import kindlereport.service.KindleService;
import kindlereport.web.model.DateKindle;
import kindlereport.web.model.OrderType;
import kindlereport.web.util.DateUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class IndexController {
	private static final Logger logger = LoggerFactory.getLogger(IndexController.class);
	
	@Autowired
	private KindleService kindleService;

	@RequestMapping("/")
	public String home(Model model, HttpServletRequest request) {
		
		List<KindleTile> newRegistList = kindleService.getKindleList(5, 0, OrderType.insertTimeDesc, null, false);
		logger.debug("newRegistList = {}", newRegistList);
		model.addAttribute("newRegistList", newRegistList);
		
		DateKindle dateKindle = kindleService.getDateKindleList(DateUtil.getTodayByString()).get(0);
		List<KindleTile> todayReleaseList = dateKindle.getKindleList();
		logger.debug("todayReleaseList = {}", todayReleaseList);
		model.addAttribute("todayReleaseList", todayReleaseList);
		
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
			return "common_frame";
		} else {
			model.addAttribute("dateKindleListList", kindleService.getDateKindleList(ajaxDate));
			return "dateList_content";
		}
	}

}
