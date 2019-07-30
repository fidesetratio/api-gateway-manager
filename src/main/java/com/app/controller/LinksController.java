package com.app.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.app.controller.datatables.SimplePaginator;
import com.app.controller.datatables.TablePaginator;
import com.app.controller.datatables.models.PaginationCriteria;
import com.app.controller.datatables.models.ReceiveData;
import com.app.controller.datatables.models.TablePage;
import com.app.controller.template.DataTablesWidget;
import com.app.controller.template.SelectInput;
import com.app.controller.template.SimpleCrud;
import com.app.manager.model.AuthenticationProvider;
import com.app.manager.model.Link;
import com.app.manager.model.RoleCategory;
import com.app.manager.model.Roles;
import com.app.manager.repo.AuthenticationProviderRepository;
import com.app.manager.repo.LinkRepository;
import com.app.manager.repo.RolesCategoriesRepository;
import com.app.rest.model.EntityResponse;
import com.app.services.LinkServices;

@Controller
@RequestMapping("/links")
public class LinksController extends SimpleCrud {
	@Autowired
	private LinkRepository repo;
	
	@Autowired
	private RolesCategoriesRepository roleCategories;
	
	@Autowired
	private LinkRepository linkRepository;
	
	@Autowired
	private AuthenticationProviderRepository authenticationProviderRepository;
	
	
	
	private Logger logger = LoggerFactory.getLogger(LinksController.class);

	@Override
	public DataTablesWidget init() {
		// TODO Auto-generated method stub
		DataTablesWidget widget = new DataTablesWidget();
		widget.setTitle("Links Lists");
		widget.setDestination("/links");
		widget.addHeader("LinkId");
		widget.addHeader("Link Id");
		widget.addHeader("Context");
		widget.addHeader("ServiceId");
		widget.addHeader("PermitAll");
		widget.addHeader("Active");
		/*SelectInput selectInput = new SelectInput("Category","categoryid");
		selectInput.addSelect("Please Select Category","0");
		selectInput.addSelect("Database","1");
		selectInput.addSelect("Rumah","2");
		
		
		widget.setSelectInput(selectInput);
*/		return widget;
	}

	@Override
	public TablePage listsPage(PaginationCriteria treq) {
		TablePaginator paginator = new SimplePaginator(new LinkServices(repo));
		TablePage tablePage =  paginator.getPage(treq);
		return tablePage;
	}

	@RequestMapping(value="/add",method=RequestMethod.GET)
	public String add(Model model,@RequestParam(name="categoryid",required = false) Integer categoryid){
		
		System.out.println("categoryIDD="+categoryid);
		
		List<Link> links = new ArrayList<Link>();
		links = (List<Link>)linkRepository.findAll();
		List<AuthenticationProvider> listAuthenticationProvider = (List<AuthenticationProvider>)authenticationProviderRepository.findAll();
		Link link = new Link();
		List<RoleCategory> listCategories = (List<RoleCategory>)roleCategories.findAll();
		model.addAttribute("listCategories",listCategories);		
		model.addAttribute("listProviders",listAuthenticationProvider);		
		model.addAttribute("link",link);
		model.addAttribute("links",links);
		return "fragments/addlinks";
	}

	@RequestMapping(value="/remove",method=RequestMethod.POST,produces="application/json")
	public @ResponseBody EntityResponse remove(@RequestBody  List<Link> list) {
		logger.info("size:"+list.size());
		for(Link details:list){
			Link clientDetails = details;
			repo.deleteById(clientDetails.getLinkId());
		}
		EntityResponse response = new EntityResponse();
		return response;
		
		
		
	}


	
	@RequestMapping(value="/add/submit",method=RequestMethod.POST)
	public String submit(@Valid @ModelAttribute("link")  Link link, BindingResult bindingResult, Model model,@RequestParam(name = "roleText") String roleText,@RequestParam(name = "sensitiveHeaders") String sensitiveHeaders,@RequestParam(name = "rolePickup") Integer rolePickup) {
		
	
		if(link !=  null) {
			if(!roleText.trim().equals("")) {
				List<String> roles = new ArrayList<String>(Arrays.asList(roleText.split(";")));
				link.setRoles(roles);
			};
			if(!sensitiveHeaders.trim().equals("")) {
				List<String> senheaders = new ArrayList<String>(Arrays.asList(sensitiveHeaders.split(";")));
				link.setSensitiveHeaders(senheaders);
			}
		};
		
		if(rolePickup<=1){		
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
		}else{
			link.setCategoryId(new Long(0));
			
		}
		
		if (bindingResult.hasErrors()) {
			List<AuthenticationProvider> listAuthenticationProvider = (List<AuthenticationProvider>)authenticationProviderRepository.findAll();
			List<RoleCategory> listCategories = (List<RoleCategory>)roleCategories.findAll();
			model.addAttribute("listCategories",listCategories);		
			model.addAttribute("listProviders",listAuthenticationProvider);		
			model.addAttribute("link",link);
			return "fragments/addlinks";
		}
		linkRepository.save(link);
		return "fragments/ok";
	}
	
	@RequestMapping(value="/modify",method=RequestMethod.POST,consumes="application/json",produces = { MediaType.TEXT_HTML_VALUE,
            MediaType.APPLICATION_XHTML_XML_VALUE })
	public String modify(@RequestBody Link l,@RequestParam(name="categoryid",required=false) String categoryId,Model model){
	
		List<AuthenticationProvider> listAuthenticationProvider = (List<AuthenticationProvider>)authenticationProviderRepository.findAll();
		List<Link> links = new ArrayList<Link>();
		links = (List<Link>)linkRepository.findAll();
		   Link link = repo.findByLinkId(l.getLinkId());
		   Integer rolePickup = 2;
		   if(link.getCategoryId()>0){
			   rolePickup = 1;
		   }
		   String roles = "";
		   if(link.getRoles().size()>0)
		   roles =  String.join(";", link.getRoles());
		   String sensitiveheaders = String.join(";", link.getSensitiveHeaders());
		   
		   
		   
			List<RoleCategory> listCategories = (List<RoleCategory>)roleCategories.findAll();
			model.addAttribute("listCategories",listCategories);		
			model.addAttribute("listProviders",listAuthenticationProvider);		
			model.addAttribute("link",link);
			model.addAttribute("links",links);
			model.addAttribute("roleText",roles);
			model.addAttribute("rolePickup",rolePickup);
			model.addAttribute("sensitiveHeaders",sensitiveheaders);
			
		   return "fragments/modifylinks";
	}
	
	
	@RequestMapping(value="/modify/submit",method=RequestMethod.POST)
	public String modifysubmit(@Valid @ModelAttribute("link")  Link link, BindingResult bindingResult, Model model,@RequestParam(name = "roleText") String roleText,@RequestParam(name = "sensitiveHeaders") String sensitiveHeaders,@RequestParam(name = "rolePickup") Integer rolePickup) {
			
		if(link !=  null) {
			if(!roleText.trim().equals("")) {
				List<String> roles = new ArrayList<String>(Arrays.asList(roleText.split(";")));
				link.setRoles(roles);
			};
			if(!sensitiveHeaders.trim().equals("")) {
				List<String> senheaders = new ArrayList<String>(Arrays.asList(sensitiveHeaders.split(";")));
				link.setSensitiveHeaders(senheaders);
			}
		};
		
		

		if(rolePickup<=1){		
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
			}else{
				link.setCategoryId(new Long(0));
				
				
			}
		
		if (bindingResult.hasErrors()) {
			List<AuthenticationProvider> listAuthenticationProvider = (List<AuthenticationProvider>)authenticationProviderRepository.findAll();
			List<RoleCategory> listCategories = (List<RoleCategory>)roleCategories.findAll();
			model.addAttribute("listCategories",listCategories);		
			model.addAttribute("listProviders",listAuthenticationProvider);		
			model.addAttribute("link",link);
			return "fragments/modifylinks";
		}
		linkRepository.save(link);
		return "fragments/ok";
	}
	
	

}

