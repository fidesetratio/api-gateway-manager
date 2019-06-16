package com.app.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.app.controller.datatables.SimplePaginator;
import com.app.controller.datatables.TablePaginator;
import com.app.controller.datatables.models.PaginationCriteria;
import com.app.controller.datatables.models.TablePage;

@Controller
@RequestMapping("/customsecondtemplate")
public class CustomSecondTemplateController extends SingleTemplateController  {
	
	
	public String index(HttpServletRequest request, Model model) {
		String data= super.index(request, model);
		String target = getTarget(request, "custom-list");
		model.addAttribute("titleprovider", "Jagiring");
		model.addAttribute("providercontent","fragments/"+target);
		return data;
	}
	
	@RequestMapping(value="/lists",method=RequestMethod.POST,produces="application/json")
	public @ResponseBody TablePage products(@RequestBody PaginationCriteria treq) {
		System.out.println("patar");
		TablePage tablePage =  paginator.getPage(treq);
		System.out.println(tablePage.getData());
		return tablePage;
	}
	
	private TablePaginator paginator = new SimplePaginator(
			new UserTableRepository());
	
	
	
	



}
