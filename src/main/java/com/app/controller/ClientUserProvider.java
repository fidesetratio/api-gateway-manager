package com.app.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import com.app.rest.model.ClientDetails;
import com.app.rest.model.FormModelTest;

@Controller
@RequestMapping("/clientuser")
public class ClientUserProvider extends SingleTemplateController {
	private static Logger logger = LoggerFactory.getLogger(ClientUserProvider.class);
	
	
	private static String uri = "http://localhost:8787/";

	public String index(HttpServletRequest request, Model model) {
		String data= super.index(request, model);
		String target = getTarget(request, "client-user-lists");
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<ClientDetails[]> response   = restTemplate.getForEntity(uri + "manager/listclients", ClientDetails[].class);
		ClientDetails[] a = response.getBody();
		model.addAttribute("clientLists",a);
		model.addAttribute("titleprovider", "Client User List");
		model.addAttribute("providercontent","fragments/"+target);
		return data;
	}
	
	@RequestMapping(value="/submitcopy", method=RequestMethod.POST)
	public String submitcopy(HttpServletRequest request) {
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
		return "fragments/ok";
	}
	
	@RequestMapping(value="/delete",method=RequestMethod.GET)
	public String delete(@RequestParam(value="clientId",required=false) String clientId) {
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
	
	@RequestMapping(value="/copy",method=RequestMethod.GET)
	public String copy(@RequestParam(value="clientId",required=false) String clientId,Model model) {
		 ClientDetails clientDetail = new ClientDetails();
		 clientDetail.setClientId(clientId);
		 model.addAttribute("clientDetail", clientDetail);
		 return "fragments/copy-client-user";
	}
	
	
	@RequestMapping("/addclientuser")
	public String addclientuser(Model model) {
		FormModelTest formModelTest = new FormModelTest();
	
		model.addAttribute("formModelTest",formModelTest);
		return "fragments/add-client-user";
	}
	
	
}
