package kindlereport.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kindlereport.dao.KindleMapper;
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
	
}
