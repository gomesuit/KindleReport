package kindlereport.web;

import java.util.ArrayList;
import java.util.List;

import kindlereport.dao.KindleMapper;
import kindlereport.model.DateKindleList;
import kindlereport.model.Kindle;
import kindlereport.service.MyBatisService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {
	
	@Autowired
	private KindleMapper kindleMapper;
	private MyBatisService myBatisService = new MyBatisService();

	@RequestMapping("/")
	public String home(Model model) {		
		List<Kindle> kindleList = myBatisService.getKindleList(kindleMapper, 100, 0, 1);
		model.addAttribute("kindleList", kindleList);
		
		return "default";
	}

	@RequestMapping("/basic")
	public String basic(Model model) {		
		List<Kindle> kindleList = myBatisService.getKindleList(kindleMapper, 100, 0, 1);
		model.addAttribute("kindleList", kindleList);
		
		return "basic";
	}
	
	@RequestMapping("/ajax")
	public String ajax(Model model) {		
		//List<Kindle> kindleList = myBatisService.getKindleList(100, 0);
		//model.addAttribute("kindleList", kindleList);
		
		return "ajax";
	}
	
	@RequestMapping("/dayList")
	public String dayList(Model model) {
		int colnum = 4;
		
		List<DateKindleList> dateKindleListList = new ArrayList<DateKindleList>();
		
		List<String> dateList = kindleMapper.selectRereaseDateList();
		for(String date : dateList){
			DateKindleList dateKindleList = new DateKindleList();
			dateKindleList.setReleaseDate(date);
			dateKindleList.setRowKindleList(new ArrayList<List<Kindle>>());
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
		return "dateKindleList";
	}
	
}
