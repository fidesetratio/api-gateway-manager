package com.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/links")
public class LinksController {

	@RequestMapping("/links")
	public String links(Model model){
		return "links-management";
	}

}
