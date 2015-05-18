package kindlereport.web;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import kindlereport.dao.KindleMapper;
import kindlereport.model.DateKindleList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/")
public class IndexController {
	private static final String LIST_PAGE_URL = "list";
	private static final String DATELIST_PAGE_URL = "dateList";
	private static final String FRONT_PAGE_URL = "front";
	private static final String KINDLE_DATE_FORMAT = "yyyy-MM-dd";
	
	@Autowired
	private KindleMapper kindleMapper;

	@RequestMapping("/")
	public String front(Model model) {
		model.addAttribute("LIST_PAGE_URL", LIST_PAGE_URL);
		model.addAttribute("DATELIST_PAGE_URL", DATELIST_PAGE_URL);
		model.addAttribute("FRONT_PAGE_URL", "/");
		
		return FRONT_PAGE_URL;
	}
	
	@RequestMapping(LIST_PAGE_URL)
	public String ajax(Model model) {
		model.addAttribute("LIST_PAGE_URL", LIST_PAGE_URL);
		model.addAttribute("DATELIST_PAGE_URL", DATELIST_PAGE_URL);
		
		return LIST_PAGE_URL;
	}

	@RequestMapping(DATELIST_PAGE_URL)
	public String dateList(
			@RequestParam(value = "ajaxDate", required = false) String ajaxDate,
			@RequestParam(value = "ajaxFlg", required = false, defaultValue = "0") int ajaxflg,
			Model model) {
		model.addAttribute("LIST_PAGE_URL", LIST_PAGE_URL);
		model.addAttribute("DATELIST_PAGE_URL", DATELIST_PAGE_URL);
		model.addAttribute("today", getToday());
		
		if(ajaxflg == 0){
			return DATELIST_PAGE_URL;
		}else{
			List<DateKindleList> dateKindleListList = new ArrayList<DateKindleList>();
			dateKindleListList.add(createDateKindleList(ajaxDate));
			model.addAttribute("dateKindleListList", dateKindleListList);
			return DATELIST_PAGE_URL + "_content";
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
