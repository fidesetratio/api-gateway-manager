package com.app.controller;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.app.manager.model.Application;
import com.app.manager.model.AuthenticationProvider;
import com.app.manager.model.RoleCategory;
import com.app.manager.repo.ApplicationRepository;
import com.app.manager.repo.AuthenticationProviderRepository;
import com.app.manager.repo.RolesCategoriesRepository;


@Controller
@RequestMapping("/application")
public class ApplicationController extends SingleTemplateController{

	@Autowired
	private AuthenticationProviderRepository authenticationProviderRepository;

	
	
	@Autowired
	private RolesCategoriesRepository roleCategories;
	

	@Autowired
	private ApplicationRepository applicationRepo;
	
	public String index(HttpServletRequest request, Model model) {
		String data= super.index(request, model);
		String target="application";
		model.addAttribute("titleprovider", "Application");
		model.addAttribute("providercontent","fragments/"+target);
		return data;
	}

	
	@RequestMapping(value="/addForm",method=RequestMethod.GET)
	public String addForm(Model model){
			System.out.println("patar");
			model.addAttribute("application", new Application());
			List<AuthenticationProvider> listAuthenticationProvider = (List<AuthenticationProvider>)authenticationProviderRepository.findAll();
			model.addAttribute("listProviders",listAuthenticationProvider);		
			List<RoleCategory> listCategories = (List<RoleCategory>)roleCategories.findAll();
			model.addAttribute("listCategories",listCategories);
			return "fragments/addapplication";
	};
	
	
	@RequestMapping(value="/addForm/submit",method=RequestMethod.POST)
	public String addFormSubmit(@Valid @ModelAttribute("application")  Application application, BindingResult bindingResult, Model model){
			System.out.println("patar submit");			
			if (bindingResult.hasErrors()) {
				for(ObjectError er:bindingResult.getAllErrors()) {
					System.out.println("error:"+er.getDefaultMessage());
				}
				model.addAttribute("application", application);
				List<AuthenticationProvider> listAuthenticationProvider = (List<AuthenticationProvider>)authenticationProviderRepository.findAll();
				model.addAttribute("listProviders",listAuthenticationProvider);		
				List<RoleCategory> listCategories = (List<RoleCategory>)roleCategories.findAll();
				model.addAttribute("listCategories",listCategories);
				return "fragments/addapplication";
			}
			
			applicationRepo.save(application);

			return "fragments/ok";
	};
	
	
	@RequestMapping(value="/lists",method=RequestMethod.GET,produces="application/json")
	public @ResponseBody List<Application> lists(@RequestParam(name="search",required = false) String search) {
		List<Application> page = (List<Application>) applicationRepo.findAll();
		return page;
	}
	
	@RequestMapping(value="/remove",method=RequestMethod.GET)
	public String  remove(@RequestParam(name="removeId",required = false) Long removeId) {
		Optional<Application> app = applicationRepo.findById(removeId);
		applicationRepo.delete(app.get());
		return "fragments/ok";
	}
}
