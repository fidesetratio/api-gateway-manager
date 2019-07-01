package com.app.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.app.controller.datatables.models.PaginationCriteria;
import com.app.controller.datatables.models.TablePage;
import com.app.rest.model.ClientDetails;

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
	
	
	
	/*
	 * @RequestMapping(value="/liststwo",method=RequestMethod.POST,produces=
	 * "application/json") public @ResponseBody TablePage products(@RequestBody
	 * PaginationCriteria treq) { System.out.println("patar"); TablePaginator
	 * paginator = new SimplePaginator(new ManagerAuthServices(uri +
	 * "manager/listclients")); TablePage tablePage = paginator.getPage(treq);
	 * return tablePage; }
	 */
	

	@RequestMapping(value="/lists",method=RequestMethod.POST,produces="application/json")
	public @ResponseBody TablePage lists(@RequestBody PaginationCriteria treq) {
		RestTemplate restTemplate = new RestTemplate();
		
		
		
		ResponseEntity<TablePage> response   = restTemplate.postForEntity(uri + "manager/listclientstable", treq, TablePage.class);
		return response.getBody();
	}
	
	
	@RequestMapping(value="/add",method=RequestMethod.GET)
	public String add(Model model){
			model.addAttribute("clientDetail", new ClientDetails());
			return "fragments/addclient";
	}
	@RequestMapping(value="/add/submit",method=RequestMethod.POST)
	public String submit(@Valid @ModelAttribute("clientDetail")  ClientDetails clientDetail, BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			System.out.println("error");
			for(FieldError error:bindingResult.getFieldErrors()) {
				System.out.println(error.getField()+":"+error.getDefaultMessage());
			}
			
			model.addAttribute("clientDetail", clientDetail);
			return "fragments/addclient";
		}
		System.out.println("patar timotius");		
		return "fragments/ok";
	}
}
