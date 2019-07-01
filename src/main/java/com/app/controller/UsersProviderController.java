package com.app.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import com.app.rest.model.ClientDetails;
import com.app.rest.model.OauthUsers;

@Controller
@RequestMapping("/userprovider")
public class UsersProviderController {

	private static String uri = "http://localhost:8787/";
	private static Logger logger = LoggerFactory.getLogger(UsersProviderController.class);
	
	
	
	@RequestMapping("/userlist")
	public String userlist(HttpServletRequest request,Model model){
		try {		
			 String target  = ServletRequestUtils.getStringParameter(request, "target", "userprovider-list");
			 logger.info("target:"+target);
			 
			 if(target.equals("userprovider-list")) {
				 RestTemplate restTemplate = new RestTemplate();
				 ResponseEntity<OauthUsers[]> response   = (ResponseEntity<OauthUsers[]>) restTemplate.getForEntity(uri + "manager/listusers", OauthUsers[].class);
				 OauthUsers[] a = response.getBody();
				 model.addAttribute("oauthUsers",a);
			 }
			 if(target.equals("add-providerusers")) {
				 OauthUsers oauthUsers = new OauthUsers();
				 model.addAttribute("oauthUser", oauthUsers);
			 }
			 if(target.equals("copy-provider")) {
				 ClientDetails clientDetail = new ClientDetails();
				 String clientId = ServletRequestUtils.getStringParameter(request, "client_id");
				 clientDetail.setClientId(clientId);
				 model.addAttribute("clientDetail", clientDetail);
			
			 }
			 model.addAttribute("titleprovider", "Users List");
			 model.addAttribute("providercontent","fragments/"+target);
		}catch(Exception e) {
				e.printStackTrace();
				return "fragments/error";
		};
		
		 return "provider-list";
	}
	
	
}
