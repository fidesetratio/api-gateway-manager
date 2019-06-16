package com.app.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import com.app.rest.model.ClientDetails;

@Controller
@RequestMapping("/provider")
public class ProviderController {
	
	private static String uri = "http://localhost:8787/";
	
	private static Logger logger = LoggerFactory.getLogger(ProviderController.class);
	
	
	@RequestMapping("/providerlist")
	public String providerlist(HttpServletRequest request,Model model){
		try {		
			 String target  = ServletRequestUtils.getStringParameter(request, "target", "provider-lists");
			 logger.info("target:"+target);
			 
			 
			 if(target.equals("provider-lists")) {
				 RestTemplate restTemplate = new RestTemplate();
				 ResponseEntity<ClientDetails[]> response   = restTemplate.getForEntity(uri + "manager/listclients", ClientDetails[].class);
				 ClientDetails[] a = response.getBody();
				 model.addAttribute("clientLists",a);
			 }
			 if(target.equals("add-provider")) {
				 ClientDetails clientDetail = new ClientDetails();
				 model.addAttribute("clientDetail", clientDetail);
			 }
			 if(target.equals("copy-provider")) {
				 ClientDetails clientDetail = new ClientDetails();
				 String clientId = ServletRequestUtils.getStringParameter(request, "client_id");
				 clientDetail.setClientId(clientId);
				 model.addAttribute("clientDetail", clientDetail);
			
			 }
			 model.addAttribute("titleprovider", "Provider List");
			 model.addAttribute("providercontent","fragments/"+target);
		}catch(Exception e) {
				e.printStackTrace();
				return "fragments/error";
		};
		
		 return "provider-list";
	}
	
	
	
	@RequestMapping(value="/copy", method=RequestMethod.POST)
	public String copy(HttpServletRequest request) {
		try {
			String clientId = ServletRequestUtils.getStringParameter(request, "clientId");
			String newClientId = ServletRequestUtils.getStringParameter(request, "newClientId");
			logger.info("clientId:"+clientId);
			logger.info("new client Id:"+newClientId);
			RestTemplate restTemplate = new RestTemplate();
			logger.info("url:"+uri+"copyclient/"+clientId+"/"+newClientId);
			ResponseEntity<String> response   = restTemplate.getForEntity(uri + "manager/copyclient/"+clientId+"/"+newClientId,String.class);
			String r = response.getBody();
			logger.info("error:"+r);
		} catch (ServletRequestBindingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "redirect:/provider/providerlist";
	}
	
	@RequestMapping(value="/submit",method=RequestMethod.POST)
	public String linkSubmit(@Valid @ModelAttribute("clientDetail")  ClientDetails clientDetail, BindingResult bindingResult, Model model){
			RestTemplate restTemplate = new RestTemplate();
		 	String msg = null;
			if(bindingResult.hasErrors()) {
				
			}else {
				msg = "Succesfully Submit";
				try {
				HttpEntity<ClientDetails> request = new HttpEntity<ClientDetails>(clientDetail);
				ResponseEntity<String> result =  restTemplate.postForEntity(uri+"manager/saveclient", request, String.class);
				System.out.println("status code:"+result.getStatusCode());
				
				}catch (Exception e) {
					e.printStackTrace();
					msg = "Error connection is happen, please check to your admin";
					return "fragments/error";
					
					
				}
				clientDetail = new ClientDetails();
				model.addAttribute("clientDetail", clientDetail);
	
			}
			
			model.addAttribute("msg", msg);
			return "fragments/add-provider.html";
	}
		

	
	@RequestMapping(value="/deleteProvider",method=RequestMethod.GET)
	public String deleteProvider(@RequestParam(value = "clientId",required = false) String clientId,Model model) 
	{
		RestTemplate restTemplate = new RestTemplate();
		ClientDetails clientDetails = new ClientDetails();
		clientDetails.setClientId(clientId);
		HttpEntity<ClientDetails> request = new HttpEntity<ClientDetails>(clientDetails);
		try {
		restTemplate.getForObject(uri+"manager/deleteclient/"+clientId, String.class);
		}catch(Exception e) {
			e.printStackTrace();
			return "fragments/error";
			
		}
		return "fragments/ok";
	}
	
	
	
	
	

}
