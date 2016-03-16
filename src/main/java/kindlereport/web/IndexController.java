package kindlereport.web;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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
	public String home(Model model) {

		return "front";
	}

	@RequestMapping("/list")
	public String list(
			@RequestParam(value = "tagId", required = false) List<Integer> tagId,
			Model model) {

		if (tagId != null) {
			model.addAttribute("tagList", kindleService.getTagListById(tagId));
		}

		return "list";
	}

	@RequestMapping("/dateList")
	public String dateList(
			@RequestParam(value = "ajaxDate", required = false) String ajaxDate,
			@RequestParam(value = "ajaxFlg", required = false, defaultValue = "0") int ajaxflg,
			Model model) {
		
		model.addAttribute("today", getTodayByString());

		if (ajaxflg == 0) {
			return "dateList";
		} else {
			model.addAttribute("dateKindleListList", kindleService.getDateKindleList(ajaxDate));
			return "dateList_content";
		}
	}

	private String getTodayByString() {
		SimpleDateFormat sdf = new SimpleDateFormat(DateUtil.KINDLE_DATE_FORMAT, Locale.JAPAN);
		return sdf.format(new Date());
	}

}
