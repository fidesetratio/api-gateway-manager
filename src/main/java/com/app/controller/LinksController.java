package com.app.controller;

import java.util.ArrayList;
import java.util.Arrays;
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

import com.app.manager.model.Link;
import com.app.manager.model.RoleCategory;
import com.app.manager.model.Roles;
import com.app.manager.repo.LinkRepository;
import com.app.manager.repo.RolesCategoriesRepository;

@Controller
@RequestMapping("/links")
public class LinksController {
	

	@Autowired
	private LinkRepository linkRepository;

	@Autowired
	private RolesCategoriesRepository roleCategories;

	
	@RequestMapping("/links")
	public String links(Model model){
		List<Link> links = new ArrayList<Link>();
		links = (List<Link>)linkRepository.findAll();
		
		
		Link link = new Link();
		
		List<RoleCategory> listCategories = (List<RoleCategory>)roleCategories.findAll();
		
		model.addAttribute("listCategories",listCategories);		
		model.addAttribute("link",link);
		model.addAttribute("links",links);
		return "links-management";
	}

	
	@RequestMapping(value="/link-form-submit",method=RequestMethod.POST)
	public String linkSubmit(@Valid @ModelAttribute("link")  Link link, BindingResult bindingResult, Model model,@RequestParam(name = "roleText") String roleText){
		
		
		
		if(link !=  null) {
			if(!roleText.trim().equals("")) {
				List<String> roles = new ArrayList<String>(Arrays.asList(roleText.split(";")));
				link.setRoles(roles);
			};
		}
		
		if(link.getCategoryId()>0) {
			RoleCategory category = roleCategories.findByRoleCategoryId(link.getCategoryId());
			List<String> rt = new ArrayList<String>();
			for(Roles r:category.getRoles()) {
				rt.add(r.getRoleName());
			}
			if(rt.size()>0) {
				link.setRoles(rt);
			}
			
		}
		if (bindingResult.hasErrors()) {
			model.addAttribute("link",link);
			return "fragments/addlinks";
			
		}else {
			
			linkRepository.save(link);
		
		}
		
			return "fragments/addlinks";
		
	}
	
	
	@RequestMapping(value="/list-links",method=RequestMethod.GET)
	public String listRoles(Model model){
		List<Link> links = new ArrayList<Link>();
		links = (List<Link>)linkRepository.findAll();
		model.addAttribute("links",links);
		return "fragments/tablelinks";
	}
	
	@RequestMapping(value="/delete-link",method=RequestMethod.GET)
	public String deleteRole(@RequestParam(value = "linkId", required = false) Long linkId, Model model){
			if(linkId != null) {
				Link l = linkRepository.findByLinkId(linkId);
				linkRepository.delete(l);
			};
		
			return "fragments/ok";
		
	}


}
