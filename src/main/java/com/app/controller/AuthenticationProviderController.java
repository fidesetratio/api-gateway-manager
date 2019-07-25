package com.app.controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.app.controller.datatables.SimplePaginator;
import com.app.controller.datatables.TablePaginator;
import com.app.controller.datatables.models.PaginationCriteria;
import com.app.controller.datatables.models.TablePage;
import com.app.controller.template.DataTablesWidget;
import com.app.controller.template.SimpleCrud;
import com.app.manager.model.AuthenticationProvider;
import com.app.manager.model.RoleCategory;
import com.app.manager.repo.AuthenticationProviderRepository;
import com.app.rest.model.EntityResponse;
import com.app.services.ProviderAuthenticationTable;

@Controller
@RequestMapping("/authentication")
public class AuthenticationProviderController extends SimpleCrud{

	@Autowired
	private AuthenticationProviderRepository repo;
	
	private Logger logger = LoggerFactory.getLogger(AuthenticationProviderController.class);

	@Override
	public DataTablesWidget init() {
		// TODO Auto-generated method stub
		DataTablesWidget widget = new DataTablesWidget();
		widget.setTitle("Authentication Provider Lists");
		widget.setDestination("/authentication");
		widget.addHeader("ProviderId");
		widget.addHeader("Provider Id");
		widget.addHeader("Provider Name");
		widget.addHeader("Type Provider");
		widget.addHeader("Client Id");
		widget.addHeader("Client Secret");
		widget.addHeader("Active");
		return widget;
	}

	@Override
	public TablePage listsPage(PaginationCriteria treq) {
		// TODO Auto-generated method stub
		TablePaginator paginator = new SimplePaginator(new ProviderAuthenticationTable(repo));
		TablePage tablePage =  paginator.getPage(treq);
		return tablePage;
	
	}
	@RequestMapping(value="/add",method=RequestMethod.GET)
	public String add(Model model){
			model.addAttribute("authenticationProvider", new AuthenticationProvider());
			return "fragments/addprovider";
	}
	
	@RequestMapping(value="/add/submit",method=RequestMethod.POST)
	public String submit(@Valid @ModelAttribute("authenticationProvider")  AuthenticationProvider authenticationProvider, BindingResult bindingResult, Model model) {
		
		if(authenticationProvider != null) {
			if(authenticationProvider.getProviderName() != null) {
	
				String providerName = authenticationProvider.getProviderName();
				AuthenticationProvider prov = repo.findByProviderName(providerName);
				  if(prov != null) {
					  bindingResult.rejectValue("providerName","error.providerName",
					  "Provider already exist"); };
					 
				}
		};
		
		
		
		if (bindingResult.hasErrors()) {
			for(FieldError error:bindingResult.getFieldErrors()) {
				System.out.println(error.getField()+":"+error.getDefaultMessage());
			}
			
			model.addAttribute("authenticationProvider", authenticationProvider);
			return "fragments/addprovider";
		}
		repo.save(authenticationProvider);
		return "fragments/ok";
	}
	
	
	
	@RequestMapping(value="/remove",method=RequestMethod.POST,produces="application/json")
	public @ResponseBody EntityResponse remove(@RequestBody  List<AuthenticationProvider> list) {
		logger.info("size:"+list.size());
		for(AuthenticationProvider details:list){
			AuthenticationProvider t = details;
			repo.deleteById(t.getProviderId());
		}
		EntityResponse response = new EntityResponse();
		return response;
		
		
		
	}

	
	

	@RequestMapping(value="/modify",method=RequestMethod.POST,consumes="application/json",produces = { MediaType.TEXT_HTML_VALUE,
            MediaType.APPLICATION_XHTML_XML_VALUE })
	public String modify(@RequestBody AuthenticationProvider detail,Model model){
			///logger.info("modify"+detail.getClientId());

		   AuthenticationProvider authenticationProvider = repo.findByProviderId(detail.getProviderId());
		   model.addAttribute("authenticationProvider", authenticationProvider);
		  return "fragments/modifyprovider";
	}
	
	@RequestMapping(value="/modify/submit",method=RequestMethod.POST)
	public String modifysubmit(@Valid @ModelAttribute("authenticationProvider")  AuthenticationProvider authenticationProvider, BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			logger.info("there is an error here");
			for(FieldError error:bindingResult.getFieldErrors()) {
				System.out.println(error.getField()+":"+error.getDefaultMessage());
			};
			model.addAttribute("authenticationProvider", authenticationProvider);
			return "fragments/modifyprovider";
		}
		
		repo.save(authenticationProvider);
		return "fragments/ok";
	}
	
}