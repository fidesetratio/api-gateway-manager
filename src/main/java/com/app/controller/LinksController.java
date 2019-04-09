package com.app.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import com.app.manager.model.AuthenticationProvider;
import com.app.manager.model.Link;
import com.app.manager.model.RoleCategory;
import com.app.manager.model.Roles;
import com.app.manager.repo.AuthenticationProviderRepository;
import com.app.manager.repo.LinkRepository;
import com.app.manager.repo.RolesCategoriesRepository;

@Controller
@RequestMapping("/links")
public class LinksController {

	
	
	@Autowired
	private Environment env;
	

	@Autowired
	private LinkRepository linkRepository;

	
	@Autowired
	private AuthenticationProviderRepository authenticationProviderRepository;

	@Autowired
	private RolesCategoriesRepository roleCategories;

	
	@RequestMapping("/links")
	public String links(Model model){
		
		List<Link> links = new ArrayList<Link>();
		links = (List<Link>)linkRepository.findAll();
		List<AuthenticationProvider> listAuthenticationProvider = (List<AuthenticationProvider>)authenticationProviderRepository.findAll();
		
		
		Link link = new Link();
		
		List<RoleCategory> listCategories = (List<RoleCategory>)roleCategories.findAll();
		
		model.addAttribute("listCategories",listCategories);		
		model.addAttribute("listProviders",listAuthenticationProvider);		
		
		model.addAttribute("link",link);
		model.addAttribute("links",links);
		return "links-management";
	}

	
	@RequestMapping(value="/link-form-submit",method=RequestMethod.POST)
	public String linkSubmit(@Valid @ModelAttribute("link")  Link link, BindingResult bindingResult, Model model,@RequestParam(name = "roleText") String roleText,@RequestParam(name = "sensitiveHeaders") String sensitiveHeaders){
		
		
		
		if(link !=  null) {
			if(!roleText.trim().equals("")) {
				List<String> roles = new ArrayList<String>(Arrays.asList(roleText.split(";")));
				link.setRoles(roles);
			};
			if(!sensitiveHeaders.trim().equals("")) {
				List<String> senheaders = new ArrayList<String>(Arrays.asList(sensitiveHeaders.split(";")));
				link.setSensitiveHeaders(senheaders);
			}
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
			List<AuthenticationProvider> listAuthenticationProvider = (List<AuthenticationProvider>)authenticationProviderRepository.findAll();
			List<RoleCategory> listCategories = (List<RoleCategory>)roleCategories.findAll();
			model.addAttribute("listCategories",listCategories);		
			model.addAttribute("listProviders",listAuthenticationProvider);		
			model.addAttribute("link",link);
			return "fragments/addlinks";
			
		}else {
			
			linkRepository.save(link);
			reload();
		}
		
			return "fragments/addlinks";
		
	}
	

	
	
	
	
	@RequestMapping(value="/update-form-submit",method=RequestMethod.POST)
	public String updateSubmit(@Valid @ModelAttribute("linkupdate")  Link link, BindingResult bindingResult, Model model,@RequestParam(name = "roleText") String roleText,@RequestParam(name = "sensitiveHeaders") String sensitiveHeaders){
		if(link !=  null) {
			if(!roleText.trim().equals("")) {
				List<String> roles = new ArrayList<String>(Arrays.asList(roleText.split(";")));
				link.setRoles(roles);
			};
			if(!sensitiveHeaders.trim().equals("")) {
				List<String> senheaders = new ArrayList<String>(Arrays.asList(sensitiveHeaders.split(";")));
				link.setSensitiveHeaders(senheaders);
			}
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
			List<AuthenticationProvider> listAuthenticationProvider = (List<AuthenticationProvider>)authenticationProviderRepository.findAll();
			List<RoleCategory> listCategories = (List<RoleCategory>)roleCategories.findAll();
			model.addAttribute("listCategories",listCategories);		
			model.addAttribute("listProviders",listAuthenticationProvider);		
			model.addAttribute("link",link);
			return "fragments/updateLinks";
			
		}else {
			
			linkRepository.save(link);
			reload();
			System.out.println(link.getLinkId());
		}
	
		return "fragments/updateLinks";

	}
		

	
	
	@RequestMapping(value="/viewupdate",method=RequestMethod.GET)
	public String viewUpdate(Model model,@RequestParam(name = "linkId") Long linkId){

		List<AuthenticationProvider> listAuthenticationProvider = (List<AuthenticationProvider>)authenticationProviderRepository.findAll();
			
		System.out.println("linkId="+linkId);
		Link link = linkRepository.findByLinkId(linkId);
		if(link != null)
		{	
			String sensitiveHeaders = "";
			List<String> l = link.getSensitiveHeaders();
			if(l.size()>0) {
				String[] lt = new String[l.size()];
				lt = l.toArray(lt);
				sensitiveHeaders = StringUtils.join(lt, ";");
			}
			
			
		List<RoleCategory> listCategories = (List<RoleCategory>)roleCategories.findAll();
		model.addAttribute("listCategories",listCategories);		
		model.addAttribute("listProviders",listAuthenticationProvider);		
		model.addAttribute("sensitiveHeaders",sensitiveHeaders);		
		model.addAttribute("linkupdate",link);
		return "fragments/updateLinks";
		}else {
			return "fragments/error";
		}
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
				reload();

			};
		
			return "fragments/ok";
		
	}

	
	
	@RequestMapping("/reload-apigateway")
	public String reloadApiGateway(Model model){
		String apiGateway = env.getProperty("url.api.gateway");
		System.out.println(env.getProperty("url.api.gateway"));
		RestTemplate restTemplate = new RestTemplate();
		String error = "";
		try {
		ResponseEntity<String> response
		  = restTemplate.getForEntity(apiGateway + "/gwadmin/reload", String.class);
		System.out.println(response.getStatusCode());
		}catch(Exception e) {
			error = e.getMessage();
			return "fragments/error";
		}
		return "fragments/ok";
	}

	
	private String reload() {
		String apiGateway = env.getProperty("url.api.gateway");
		RestTemplate restTemplate = new RestTemplate();
		String error = ""; 
		try {
		ResponseEntity<String> response
		  = restTemplate.getForEntity(apiGateway + "/gwadmin/reload", String.class);
		}catch(Exception e) {
			error = e.getMessage();
		}
		return error;
	}
}
