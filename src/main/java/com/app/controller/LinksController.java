package com.app.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.app.controller.datatables.SimplePaginator;
import com.app.controller.datatables.TablePaginator;
import com.app.controller.datatables.models.PaginationCriteria;
import com.app.controller.datatables.models.TablePage;
import com.app.controller.template.DataTablesWidget;
import com.app.controller.template.SimpleCrud;
import com.app.manager.model.AuthenticationProvider;
import com.app.manager.model.Link;
import com.app.manager.model.RoleCategory;
import com.app.manager.repo.AuthenticationProviderRepository;
import com.app.manager.repo.LinkRepository;
import com.app.manager.repo.RolesCategoriesRepository;
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
		return widget;
	}

	@Override
	public TablePage listsPage(PaginationCriteria treq) {
		TablePaginator paginator = new SimplePaginator(new LinkServices(repo));
		TablePage tablePage =  paginator.getPage(treq);
		return tablePage;
	}

	@RequestMapping(value="/add",method=RequestMethod.GET)
	public String add(Model model){
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
}