package kindlereport.web;

import java.util.List;
import java.util.Map;

import kindlereport.dao.KindleMapper;
import kindlereport.model.Kindle;
import kindlereport.service.MyBatisService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("items")
public class ItemsController {
	
	@Autowired
	private KindleMapper kindleMapper;
	private MyBatisService myBatisService = new MyBatisService();

	@RequestMapping("/")
	public String home(Model model) {		
		List<Kindle> kindleList = myBatisService.getKindleList(kindleMapper, 100, 0, 1);
		model.addAttribute("kindleList", kindleList);
		
		return "default";
	}
	
	@RequestMapping(value = "{asin}", method = RequestMethod.GET)
	public String ajax(@PathVariable String asin, Model model) {
		Map<String, String> kindle = kindleMapper.selectKindle(asin);
		model.addAttribute("kindle", kindle);
		
		return "items";
	}
	
}
