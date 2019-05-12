package com.app.controller;

import java.util.List;

import org.assertj.core.util.Arrays;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import com.app.rest.model.ClientDetails;

@Controller
@RequestMapping("/provider")
public class ProviderController {
	
	private static String uri = "http://localhost:8787/";
	
	@RequestMapping("/providerlist")
	public String providerlist(Model model){
		
		try {		
			 RestTemplate restTemplate = new RestTemplate();
			 ResponseEntity<ClientDetails[]> response   = restTemplate.getForEntity(uri + "/manager/listclients", ClientDetails[].class);
			 ClientDetails[] a = response.getBody();
			 model.addAttribute("clientLists",a);

		}catch(Exception e) {
			
		}finally {
			
		}
		 return "provider-list";
	}
	

}
