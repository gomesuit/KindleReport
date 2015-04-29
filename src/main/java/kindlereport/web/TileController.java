package kindlereport.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import kindlereport.dao.KindleMapper;
import kindlereport.model.DateKindleList;
import kindlereport.model.Kindle;
import kindlereport.service.MyBatisService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("tile")
public class TileController {
	
	@Autowired
	private KindleMapper kindleMapper;
	private MyBatisService myBatisService = new MyBatisService();

	@RequestMapping("/")
	public String home(Model model) {		
		List<Kindle> kindleList = myBatisService.getKindleList(kindleMapper, 100, 0, 1);
		model.addAttribute("kindleList", kindleList);
		
		return "default";
	}
	
	@RequestMapping("/ajax")
	public String ajax(Model model) {		
		//List<Kindle> kindleList = myBatisService.getKindleList(100, 0);
		//model.addAttribute("kindleList", kindleList);
		
		return "ajax";
	}

	@RequestMapping(value = "{page}", method = RequestMethod.GET)
	public String ajax(@PathVariable int page,
			@RequestParam(value = "order", required = false, defaultValue = "1") int order,
			Model model) {
		int limit = 24;
		int offset = page * limit;
		List<Kindle> kindleList = myBatisService.getKindleList(kindleMapper, limit, offset, order);
		model.addAttribute("kindleList", kindleList);
		
		return "tile";
	}
	
}
