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
import kindlereport.model.Kindle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

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
	public String dateList(Model model) {
		int colnum = 6;
		
		List<DateKindleList> dateKindleListList = new ArrayList<DateKindleList>();
		
		List<String> dateList = kindleMapper.selectRereaseDateList(getCurrentDate());
		int sidebarId = 0;
		for(String date : dateList){
			DateKindleList dateKindleList = new DateKindleList();
			dateKindleList.setReleaseDate(date);
			dateKindleList.setDateString(dateConvert(date));
			dateKindleList.setRowKindleList(new ArrayList<List<Kindle>>());
			sidebarId++;
			dateKindleList.setSidebarId("sidebar" + sidebarId);
			List<Kindle> KindleList = kindleMapper.selectDayKindleList(date);
			for(int i = 0; KindleList.size() > i; i = i + colnum){
				int start = i;
				int end = i + colnum;
				if(KindleList.size() < end){
					end = KindleList.size();
				}
				dateKindleList.getRowKindleList().add(KindleList.subList(start, end));
			}
			dateKindleListList.add(dateKindleList);
		}
		
		model.addAttribute("dateKindleListList", dateKindleListList);
		model.addAttribute("today", getToday());
		model.addAttribute("LIST_PAGE_URL", LIST_PAGE_URL);
		model.addAttribute("DATELIST_PAGE_URL", DATELIST_PAGE_URL);
		return DATELIST_PAGE_URL;
	}

    private String getCurrentDate(){
    	SimpleDateFormat sdf = new SimpleDateFormat(KINDLE_DATE_FORMAT, Locale.JAPAN);
    	
    	Calendar calendar = Calendar.getInstance();
    	calendar.setTime(new Date());
    	calendar.add(Calendar.DAY_OF_MONTH, -3);
    	
    	return sdf.format(calendar.getTime());
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
