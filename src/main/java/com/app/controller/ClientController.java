package com.app.controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
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
import com.app.controller.template.DataTablesWidget;
import com.app.controller.template.SimpleCrud;
import com.app.rest.model.ClientDetails;
import com.app.rest.model.EntityResponse;

@Controller
@RequestMapping("/client")
public class ClientController extends SimpleCrud  {
	
	private Logger logger = LoggerFactory.getLogger(ClientController.class);

	private static String uri = "http://localhost:8787/";
	@Override
	public DataTablesWidget init() {
		// TODO Auto-generated method stub
		
		DataTablesWidget widget = new DataTablesWidget();
		widget.setTitle("Client Lists");
		widget.setDestination("/client");
		widget.addHeader("Client Id");
		widget.addHeader("Client Id");
		widget.addHeader("ResourceIds");
		widget.addHeader("Client Secret");
		widget.addHeader("AccessTokenValidity");
		return widget;
	}

	@Override
	public TablePage listsPage(PaginationCriteria treq) {
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
			logger.info("there is an error here");
			for(FieldError error:bindingResult.getFieldErrors()) {
				System.out.println(error.getField()+":"+error.getDefaultMessage());
			}
			
			model.addAttribute("clientDetail", clientDetail);
			return "fragments/addclient";
		}
		
		RestTemplate restTemplate = new RestTemplate();
		try {
			HttpEntity<ClientDetails> request = new HttpEntity<ClientDetails>(clientDetail);
			ResponseEntity<String> result =  restTemplate.postForEntity(uri+"manager/saveclient", request, String.class);			
			}catch (Exception e) {
				e.printStackTrace();
				return "fragments/error";
			}
		System.out.println("patar timotius");		
		return "fragments/ok";
	}
	
	
	
	@RequestMapping(value="/modify",method=RequestMethod.POST,consumes="application/json",produces = { MediaType.TEXT_HTML_VALUE,
            MediaType.APPLICATION_XHTML_XML_VALUE })
	public String modify(@RequestBody ClientDetails detail,Model model){
			logger.info("modify"+detail.getClientId());

			RestTemplate restTemplate = new RestTemplate();
			HttpEntity<ClientDetails> request = new HttpEntity<ClientDetails>(detail);
			ClientDetails d = new ClientDetails();
			try {
				d = restTemplate.getForObject(uri+"manager/viewclient/"+detail.getClientId(), ClientDetails.class);
			}catch(Exception e) {
					e.printStackTrace();
			}
			model.addAttribute("clientDetail", d);
			return "fragments/modifyclient";
	}
	
	
	
	@RequestMapping(value="/modify/submit",method=RequestMethod.POST)
	public String modifysubmit(@Valid @ModelAttribute("clientDetail")  ClientDetails clientDetail, BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			logger.info("there is an error here");
			for(FieldError error:bindingResult.getFieldErrors()) {
				System.out.println(error.getField()+":"+error.getDefaultMessage());
			};
			
			
			model.addAttribute("clientDetail", clientDetail);
			return "fragments/addclient";
		}
		
		RestTemplate restTemplate = new RestTemplate();
		try {
			HttpEntity<ClientDetails> request = new HttpEntity<ClientDetails>(clientDetail);
			ResponseEntity<String> result =  restTemplate.postForEntity(uri+"manager/saveclient", request, String.class);			
			}catch (Exception e) {
				e.printStackTrace();
				return "fragments/error";
		}
		System.out.println("modify submit");		
		return "fragments/ok";
	}
	

	
	@RequestMapping(value="/remove",method=RequestMethod.POST,produces="application/json")
	public @ResponseBody EntityResponse remove(@RequestBody  List<ClientDetails> list) {
		logger.info("size:"+list.size());
		
		
		for(ClientDetails details:list){
			ClientDetails clientDetails = details;
			logger.info("clientDetails id ="+clientDetails.getClientId());
			RestTemplate restTemplate = new RestTemplate();
			HttpEntity<ClientDetails> request = new HttpEntity<ClientDetails>(clientDetails);
			try {
				restTemplate.getForObject(uri+"manager/deleteclient/"+clientDetails.getClientId(), String.class);
				}catch(Exception e) {
					e.printStackTrace();
			}
			
		}
		
		EntityResponse response = new EntityResponse();
		
		
		return response;
		
		
		
	}

	
	
	

	
	
	
}
