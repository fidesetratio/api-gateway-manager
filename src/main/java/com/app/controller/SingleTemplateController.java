package com.app.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/singletemplate")
public class SingleTemplateController {

	
	protected final String template = "single-template";
	
	public String getTarget(HttpServletRequest request,String template) {
		return ServletRequestUtils.getStringParameter(request, "target", template);
	}
	
	
	

	@RequestMapping("/index")
	public String index(HttpServletRequest request,Model model){
		 String target  = ServletRequestUtils.getStringParameter(request, "target", "single-list");
		 model.addAttribute("titleprovider", "Users List");
		 model.addAttribute("providercontent","fragments/"+target);
		 return "single-template";
	}
	
	
	
	
}
