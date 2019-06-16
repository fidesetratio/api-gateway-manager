package com.app.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.app.controller.datatables.SimplePaginator;
import com.app.controller.datatables.TablePaginator;
import com.app.controller.datatables.models.PaginationCriteria;
import com.app.controller.datatables.models.TablePage;
import com.app.rest.model.ClientDetails;
import com.app.services.ManagerAuthServices;

@Controller
@RequestMapping("/client")
public class ClientController extends SingleTemplateController  {

	private static String uri = "http://localhost:8787/";
	
	public String index(HttpServletRequest request, Model model) {
		String data= super.index(request, model);
		String target = getTarget(request, "client-lists");
		model.addAttribute("titleprovider", "Client Lists");
		model.addAttribute("providercontent","fragments/"+target);
		return data;
	}
	
	
	
	@RequestMapping(value="/lists",method=RequestMethod.POST,produces="application/json")
	public @ResponseBody TablePage products(@RequestBody PaginationCriteria treq) {
		System.out.println("patar");
	
		TablePaginator paginator = new SimplePaginator(new ManagerAuthServices(uri + "manager/listclients"));
		TablePage tablePage =  paginator.getPage(treq);
		return tablePage;
	}
	
	
	
}
