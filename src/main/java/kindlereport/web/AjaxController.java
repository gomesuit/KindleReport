package kindlereport.web;

import java.util.List;

import kindlereport.dao.KindleMapper;
import kindlereport.model.Kindle;
import kindlereport.service.MyBatisService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AjaxController {
	
	@Autowired
	private KindleMapper kindleMapper;
	private MyBatisService myBatisService = new MyBatisService();
	
	@RequestMapping("/json")
	public List<Kindle> ajax(
			@RequestParam(value = "page", required = false, defaultValue = "1") int page,
			@RequestParam(value = "order", required = false, defaultValue = "1") int order,
			Model model) {
		int limit = 24;
		int offset = (page - 1) * limit;
		List<Kindle> kindleList = myBatisService.getKindleList(kindleMapper, limit, offset, order);
		return kindleList;
	}
	
}
