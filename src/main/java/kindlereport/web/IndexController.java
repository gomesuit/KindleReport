package kindlereport.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kindlereport.dao.KindleMapper;
import kindlereport.model.DateKindleList;
import kindlereport.model.Kindle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {
	
	@Autowired
	private KindleMapper kindleMapper;

	@RequestMapping("/")
	public String home(Model model) {		
		List<Kindle> kindleList = kindleMapper.selectKindleList();
		model.addAttribute("kindleList", kindleList);
		
		return "default";
	}
	
	@RequestMapping("/dayList")
	public String dayList(Model model) {
		List<DateKindleList> dateKindleListList = new ArrayList<DateKindleList>();
		
		List<String> dateList = kindleMapper.selectRereaseDateList();
		for(String date : dateList){
			DateKindleList dateKindleList = new DateKindleList();
			dateKindleList.setReleaseDate(date);
			dateKindleList.setRowKindleList(new ArrayList<List<Kindle>>());
			List<Kindle> KindleList = kindleMapper.selectDayKindleList(date);
			for(int i = 0; KindleList.size() > i; i = i + 4){
				int start = i;
				int end = i + 4;
				if(KindleList.size() < end){
					end = KindleList.size();
				}
				dateKindleList.getRowKindleList().add(KindleList.subList(start, end));
			}
			dateKindleListList.add(dateKindleList);
		}
		
		model.addAttribute("dateKindleListList", dateKindleListList);
		return "dateKindleList";
	}
	
}
