package com.app.controller;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.app.manager.model.AuthenticationProvider;
import com.app.manager.model.RoleCategory;

@Controller
@RequestMapping("/authentication")
public class AuthenticationProviderController {

	@RequestMapping("/provider")
	public String provider(Model model){
		AuthenticationProvider authenticationProvider = new AuthenticationProvider();
		model.addAttribute("authenticationProvider",authenticationProvider);
		return "provider-management";
	}
	@RequestMapping(value="/provider-form-submit",method=RequestMethod.POST)
	public String providerSubmit(@Valid @ModelAttribute("authenticationProvider")  AuthenticationProvider authenticationProvider, BindingResult bindingResult, Model model){
		if (bindingResult.hasErrors()) {
			model.addAttribute("authenticationProvider",authenticationProvider);
			return "fragments/addprovider";
		}
		
		return "fragments/addprovider";
				
	}
	
}
