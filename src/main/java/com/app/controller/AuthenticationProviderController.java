package com.app.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.app.manager.model.AuthenticationProvider;
import com.app.manager.model.RoleCategory;
import com.app.manager.repo.AuthenticationProviderRepository;

@Controller
@RequestMapping("/authentication")
public class AuthenticationProviderController {
	
	@Autowired
	private AuthenticationProviderRepository authenticationProviderRepo;

	@RequestMapping("/provider")
	public String provider(Model model){
		AuthenticationProvider authenticationProvider = new AuthenticationProvider();
		List<AuthenticationProvider> list = (List<AuthenticationProvider>) authenticationProviderRepo.findAll();
		model.addAttribute("authenticationProviderList",list);
		
		model.addAttribute("authenticationProvider",authenticationProvider);
		return "provider-management";
	}
	@RequestMapping(value="/provider-form-submit",method=RequestMethod.POST)
	public String providerSubmit(@Valid @ModelAttribute("authenticationProvider")  AuthenticationProvider authenticationProvider, BindingResult bindingResult, Model model){
		
		if(authenticationProvider != null) {
			if(authenticationProvider.getProviderName() != null) {
	
				String providerName = authenticationProvider.getProviderName();
				
					AuthenticationProvider prov = authenticationProviderRepo.findByProviderName(providerName);
					
				  if(prov != null) {
				  bindingResult.rejectValue("providerName","error.providerName",
				  "Provider already exist"); };
				 
				}
		};
		
		
		if (bindingResult.hasErrors()) {
			model.addAttribute("authenticationProvider",authenticationProvider);
			return "fragments/addprovider";
		}
		
		
		
		authenticationProviderRepo.save(authenticationProvider);
		return "fragments/addprovider";
			
	}
	

	@RequestMapping(value="/list-provider",method=RequestMethod.GET)
	public String listProvider(Model model){
		List<AuthenticationProvider> list = new ArrayList<AuthenticationProvider>();
		list = (List<AuthenticationProvider>) authenticationProviderRepo.findAll();
		model.addAttribute("authenticationProviderList",list);
		
		return "fragments/tableprovider";
	}

	
	@RequestMapping(value="/delete-provider",method=RequestMethod.GET)
	public String deleteProvider(@RequestParam(value = "providerId", required = false) Long providerId, Model model){
			if(providerId != null) {
				AuthenticationProvider provider = authenticationProviderRepo.findByProviderId(providerId);
				authenticationProviderRepo.delete(provider);
			};
			return "fragments/ok";
		
	}
}
