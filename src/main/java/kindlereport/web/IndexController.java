package kindlereport.web;

import java.util.ArrayList;
import java.util.List;

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
	
	@Autowired
	private KindleMapper kindleMapper;
	
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
		
		List<String> dateList = kindleMapper.selectRereaseDateList();
		int sidebarId = 0;
		for(String date : dateList){
			DateKindleList dateKindleList = new DateKindleList();
			dateKindleList.setReleaseDate(date);
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
		model.addAttribute("LIST_PAGE_URL", LIST_PAGE_URL);
		model.addAttribute("DATELIST_PAGE_URL", DATELIST_PAGE_URL);
		return DATELIST_PAGE_URL;
	}
	
}
