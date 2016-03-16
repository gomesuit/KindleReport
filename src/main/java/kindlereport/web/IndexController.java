package kindlereport.web;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import kindlereport.mapper.KindleMapper;
import kindlereport.mapper.TagMapper;
import kindlereport.web.model.DateKindleList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class IndexController {
	private static final String KINDLE_DATE_FORMAT = "yyyy-MM-dd";
	
	@Autowired
	private KindleMapper kindleMapper;
	@Autowired
	private TagMapper tagMapper;

	@RequestMapping("/")
	public String front(Model model) {
		model.addAttribute("LIST_PAGE_URL", "list");
		model.addAttribute("DATELIST_PAGE_URL", "dateList");
		model.addAttribute("FRONT_PAGE_URL", "/");
		
		return "front";
	}
	
	@RequestMapping("/list")
	public String ajax(
			@RequestParam(value = "tagId", required = false) List<Integer> tagId,
			Model model) {
		model.addAttribute("LIST_PAGE_URL", "list");
		model.addAttribute("DATELIST_PAGE_URL", "dateList");
		if(tagId != null){
			model.addAttribute("tagList", tagMapper.selectTagListById(tagId));
		}
		
		return "list";
	}

	@RequestMapping("/dateList")
	public String dateList(
			@RequestParam(value = "ajaxDate", required = false) String ajaxDate,
			@RequestParam(value = "ajaxFlg", required = false, defaultValue = "0") int ajaxflg,
			Model model) {
		model.addAttribute("LIST_PAGE_URL", "list");
		model.addAttribute("DATELIST_PAGE_URL", "dateList");
		model.addAttribute("today", getToday());
		
		if(ajaxflg == 0){
			return "dateList";
		}else{
			List<DateKindleList> dateKindleListList = new ArrayList<DateKindleList>();
			dateKindleListList.add(createDateKindleList(ajaxDate));
			model.addAttribute("dateKindleListList", dateKindleListList);
			return "dateList" + "_content";
		}
	}
	
    private DateKindleList createDateKindleList(String date){
		DateKindleList dateKindleList = new DateKindleList();
		dateKindleList.setReleaseDate(date);
		dateKindleList.setDateString(dateConvert(date));
		dateKindleList.setSidebarId("sidebar" + date);
		dateKindleList.setKindleList(kindleMapper.selectDayKindleList(date));
		
		return dateKindleList;
    }

    private String getToday(){
    	SimpleDateFormat sdf = new SimpleDateFormat(KINDLE_DATE_FORMAT, Locale.JAPAN);
    	return sdf.format(new Date());
    }

    private String dateConvert(String releaseDate){
    	SimpleDateFormat sdf = new SimpleDateFormat(KINDLE_DATE_FORMAT, Locale.JAPAN);
    	SimpleDateFormat returnSdf = new SimpleDateFormat("M/d(E)", Locale.JAPAN);
    	Date date = null;
    	
    	try {
    		date = sdf.parse(releaseDate);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}  
    	return returnSdf.format(date);
    }
	
}
