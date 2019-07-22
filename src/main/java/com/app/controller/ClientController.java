package com.app.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
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
import com.app.rest.model.EntityResponse;

@Controller
@RequestMapping("/client")
public class ClientController extends SingleTemplateController  {

	private static String uri = "http://localhost:8787/";
	
	private static Logger logger = LoggerFactory.getLogger(ClientController.class);
	
	public String index(HttpServletRequest request, Model model) {
		String data= super.index(request, model);
		String target = getTarget(request, "client-lists");
		model.addAttribute("titleprovider", "Client Lists");
		List<String> headers = new ArrayList<String>();
		headers.add("Client Id");
		headers.add("Client Id");
		headers.add("ResourceIds");
		headers.add("Client Secret");
		headers.add("AccessTokenValidity");
		model.addAttribute("headers", headers);
		model.addAttribute("table_url", "/client");
		model.addAttribute("button","true");
		model.addAttribute("providercontent","fragments/"+target);
		return data;
	}


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
	
	
	@RequestMapping(value="/remove",method=RequestMethod.POST,produces="application/json")
	public @ResponseBody EntityResponse remove(@RequestBody List<ClientDetails> list) {
		logger.info("remove client id");
		System.out.println("fun and action");
		for(ClientDetails c:list) {
			logger.info("c="+c.getClientId());
				
		}
		EntityResponse response =  new EntityResponse();
		response.setMessage("hehhe");
		return response;
	}
	
	
	
	@RequestMapping(value="/modify",method=RequestMethod.POST,consumes="application/json",produces = { MediaType.TEXT_HTML_VALUE,
            MediaType.APPLICATION_XHTML_XML_VALUE })
	public String modify(@RequestBody ClientDetails detail,Model model){
			logger.info("modify"+detail.getClientId());
			model.addAttribute("clientDetail", new ClientDetails());
			return "fragments/addclient";
	}
	
	
}
