package com.app.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.app.controller.datatables.models.PaginationCriteria;
import com.app.controller.datatables.models.TablePage;
import com.app.controller.template.DataTablesWidget;
import com.app.controller.template.SelectInput;
import com.app.controller.template.SelectValue;
import com.app.controller.template.SimpleCrud;
import com.app.manager.model.AuthenticationProvider;
import com.app.manager.repo.AuthenticationProviderRepository;
import com.app.rest.model.ClientDetails;
import com.app.rest.model.EntityResponse;

@Controller
@RequestMapping("/clienttemp")
public class ClientTempController  extends SimpleCrud {


	@Autowired
	private AuthenticationProviderRepository authRepo;
	
	private Logger logger = LoggerFactory.getLogger(ClientTempController.class);
	

	private static String uri = "http://localhost:8787/";
	
	@Override
	public DataTablesWidget init() {
		DataTablesWidget widget = new DataTablesWidget();
		widget.setTitle("Client Lists");
		widget.setDestination("/clienttemp");
		widget.addHeader("Client Id");
		widget.addHeader("Client Id");
		widget.addHeader("ResourceIds");
		widget.addHeader("Client Secret");
		widget.addHeader("AccessTokenValidity");
		SelectInput selectInput = new SelectInput("Role Category","categoryid");
		selectInput.addSelect("Please Select Provider","0");
		List<AuthenticationProvider> list =	(List<AuthenticationProvider>) authRepo.findAll();
		for(AuthenticationProvider p:list) {
			selectInput.addSelect(p.getProviderName(),Long.toString(p.getProviderId()));
		};
		
		widget.setSelectInput(selectInput);
		return widget;
	}

	@Override
	public TablePage listsPage(PaginationCriteria treq) {
		String requestUri = uri;
		SelectValue select = treq.getSelectcategory();
		if(select != null) {
			Optional<AuthenticationProvider> opt = 	authRepo.findById(Long.parseLong(select.getValue()));
			if(opt.isPresent()){
				AuthenticationProvider o = opt.get();
				if(o != null) {
					requestUri = getHost(o.getUrl()); 
					
				}
			};
		};		
		
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<TablePage> response   = restTemplate.postForEntity(requestUri + "manager/listclientstable", treq, TablePage.class);
		return response.getBody();
	}

	@RequestMapping(value="/add",method=RequestMethod.GET)
	public String add(Model model,@RequestParam(name="categoryid",required = false) Long categoryid){
	
		model.addAttribute("categoryid", categoryid);
		model.addAttribute("clientDetail", new ClientDetails());
			return "fragments/addclient";
	}
	@RequestMapping(value="/add/submit",method=RequestMethod.POST)
	public String submit(@Valid @ModelAttribute("clientDetail")  ClientDetails clientDetail,@RequestParam(name="categoryid",required = false) Long categoryid, BindingResult bindingResult, Model model) {
	

		String requestUri = uri;
		Optional<AuthenticationProvider> opt = 	authRepo.findById(categoryid);
		if(opt.isPresent()){
			AuthenticationProvider o = opt.get();
			if(o != null) {
				requestUri = getHost(o.getUrl()); 
				
			}
		};
		
		if (bindingResult.hasErrors()) {
			logger.info("there is an error here");
			for(FieldError error:bindingResult.getFieldErrors()) {
				System.out.println(error.getField()+":"+error.getDefaultMessage());
			}
			model.addAttribute("categoryId", categoryid);
			model.addAttribute("clientDetail", clientDetail);
			return "fragments/addclient";
		}
		
		RestTemplate restTemplate = new RestTemplate();
		try {
			HttpEntity<ClientDetails> request = new HttpEntity<ClientDetails>(clientDetail);
			ResponseEntity<String> result =  restTemplate.postForEntity(requestUri+"manager/saveclient", request, String.class);			
			}catch (Exception e) {
				e.printStackTrace();
				return "fragments/error";
			}	
		return "fragments/ok";
	}
	
	
	
	@RequestMapping(value="/modify",method=RequestMethod.POST,consumes="application/json",produces = { MediaType.TEXT_HTML_VALUE,
            MediaType.APPLICATION_XHTML_XML_VALUE })
	public String modify(@RequestBody ClientDetails detail,@RequestParam(name="categoryid",required=false) String categoryId,Model model){
			String requestUri = uri;
			Optional<AuthenticationProvider> opt = 	authRepo.findById(Long.parseLong(categoryId));
			if(opt.isPresent()){
				AuthenticationProvider o = opt.get();
				if(o != null) {
					requestUri = getHost(o.getUrl()); 
					
				}
			};
			RestTemplate restTemplate = new RestTemplate();
			HttpEntity<ClientDetails> request = new HttpEntity<ClientDetails>(detail);
			ClientDetails d = new ClientDetails();
			try {
				d = restTemplate.getForObject(requestUri+"manager/viewclient/"+detail.getClientId(), ClientDetails.class);
			}catch(Exception e) {
					e.printStackTrace();
			}
			model.addAttribute("clientDetail", d);
			model.addAttribute("categoryid", categoryId);			
			return "fragments/modifyclient";
	}
	
	
	
	@RequestMapping(value="/modify/submit",method=RequestMethod.POST)
	public String modifysubmit(@Valid @ModelAttribute("clientDetail")  ClientDetails clientDetail,@RequestParam(name="categoryid",required=false) String categoryId, BindingResult bindingResult, Model model) {
		
		String requestUri = uri;
	
		Optional<AuthenticationProvider> opt = 	authRepo.findById(Long.parseLong(categoryId));
		if(opt.isPresent()){
			AuthenticationProvider o = opt.get();
			if(o != null) {
				requestUri = getHost(o.getUrl()); 
				System.out.println("categoryId"+requestUri);		
			}
		};
		
		
		if (bindingResult.hasErrors()) {
			logger.info("there is an error here");
			for(FieldError error:bindingResult.getFieldErrors()) {
				System.out.println(error.getField()+":"+error.getDefaultMessage());
			};
			model.addAttribute("categoryid", categoryId);	
			model.addAttribute("clientDetail", clientDetail);
			return "fragments/modifyclient";
		}
		
		RestTemplate restTemplate = new RestTemplate();
		try {
			HttpEntity<ClientDetails> request = new HttpEntity<ClientDetails>(clientDetail);
			ResponseEntity<String> result =  restTemplate.postForEntity(requestUri+"manager/saveclient", request, String.class);			
			}catch (Exception e) {
				e.printStackTrace();
				return "fragments/error";
		}
		System.out.println("modify submit");		
		return "fragments/ok";
	}
	

	
	@RequestMapping(value="/remove",method=RequestMethod.POST,produces="application/json")
	public @ResponseBody EntityResponse remove(@RequestBody  List<ClientDetails> list,@RequestParam(name="categoryid",required=false) String categoryId) {
		logger.info("size:"+list.size());
		System.out.println("categoryIddad:"+categoryId);
		
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

	
	private static String getHost(String host){
		int pos = host.trim().indexOf("oauth/check_token");
		return host.substring(0,pos);
	}
	

}
