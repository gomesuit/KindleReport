package kindlereport.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

	@RequestMapping("/")
	public String home(Model model) {
		//model.addAttribute("title", "title");
		//model.addAttribute("image", "/image/sample.jpg");
		Map<String, String> map = new HashMap<String, String>();
		map.put("title", "title");
		map.put("image", "/image/sample.jpg");
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		list.add(map);
		list.add(map);
		list.add(map);
		list.add(map);
		list.add(map);
		list.add(map);
		list.add(map);
		list.add(map);
		list.add(map);
		list.add(map);
		list.add(map);
		list.add(map);
		list.add(map);
		list.add(map);
		list.add(map);
		list.add(map);
		list.add(map);
		list.add(map);
		list.add(map);
		list.add(map);
		model.addAttribute("booklist", list);
		return "default";
	}
	
}
