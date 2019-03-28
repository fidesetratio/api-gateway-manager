package com.app.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.app.manager.model.Link;
import com.app.manager.repo.LinkRepository;

@Controller
@RequestMapping("/links")
public class LinksController {
	

	@Autowired
	private LinkRepository roleRepository;

	@RequestMapping("/links")
	public String links(Model model){
		List<Link> links = new ArrayList<Link>();
		links = (List<Link>)roleRepository.findAll();
		for(Link l:links){
			System.out.println(l.getUrl());
		}
		model.addAttribute("links",links);
		return "links-management";
	}

}
