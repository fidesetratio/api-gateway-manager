package com.app.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.validation.Valid;

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
import com.app.controller.datatables.models.TablePage;
import com.app.controller.template.DataTablesWidget;
import com.app.controller.template.SelectInput;
import com.app.controller.template.SimpleCrud;
import com.app.manager.model.AuthenticationProvider;
import com.app.manager.model.Link;
import com.app.manager.model.RoleCategory;
import com.app.manager.model.Roles;
import com.app.manager.repo.RoleRepository;
import com.app.manager.repo.RolesCategoriesRepository;
import com.app.rest.model.EntityResponse;
import com.app.services.RoleServiceTable;

@Controller
@RequestMapping("/role")
public class RoleController  extends SimpleCrud {
	
	
	@Autowired
	private RoleRepository repo;
	
	@Autowired
	private RolesCategoriesRepository roleCategoryRepo;

	@Override
	public DataTablesWidget init() {
		DataTablesWidget widget = new DataTablesWidget();
		widget.setTitle("Role");
		widget.setDestination("/role");
		widget.addHeader("roleId");
		widget.addHeader("roleId");
		widget.addHeader("roleName");
		SelectInput selectInput = new SelectInput("Role Category","categoryid");
		selectInput.addSelect("Please Select Category","0");
		
		List<RoleCategory> roleCategory = ((List<RoleCategory>)roleCategoryRepo.findAll());
		
		for(RoleCategory r:roleCategory){
			selectInput.addSelect(r.getCategoryName(),Long.toString(r.getRoleCategoryId()));
		};
		
		
		widget.setSelectInput(selectInput);
		return widget;
	}

	@Override
	public TablePage listsPage(PaginationCriteria treq) {
		// TODO Auto-generated method stub
		TablePaginator paginator = new SimplePaginator(new RoleServiceTable(repo));
		TablePage tablePage =  paginator.getPage(treq);
		return tablePage;
	}
	
	@RequestMapping(value="/add",method=RequestMethod.GET)
	public String add(Model model,@RequestParam(name="categoryid",required = false) Long categoryid){
		Roles r = new Roles();
		r.setRoleCategory(new RoleCategory());
		r.getRoleCategory().setRoleCategoryId(categoryid);
		model.addAttribute("r",r);
		model.addAttribute("allowed", new Integer(1));
		if(categoryid <=0){
			model.addAttribute("allowed", new Integer(2));	
		}
		return "fragments/addroles";
	}
	
	
	@RequestMapping(value="/add/submit",method=RequestMethod.POST)
	public String submit(@Valid @ModelAttribute("r")  Roles r,BindingResult bindingResult, Model model, @RequestParam(name="categoryid",required = false) Long categoryid) {
		if (bindingResult.hasErrors()) {
			model.addAttribute("r",r);
			model.addAttribute("allowed", new Integer(1));
			return "fragments/addroles";
		};
		repo.save(r);
		return "fragments/ok";
	};
	
	
	@RequestMapping(value="/remove",method=RequestMethod.POST,produces="application/json")
	public @ResponseBody EntityResponse remove(@RequestBody  List<Roles> list) {
	
		for(Roles details:list){
			Roles clientDetails = details;
			repo.deleteById(clientDetails.getRoleId());
		}
		EntityResponse response = new EntityResponse();
		return response;
	}
	@RequestMapping(value="/modify",method=RequestMethod.POST,consumes="application/json",produces = { MediaType.TEXT_HTML_VALUE,
            MediaType.APPLICATION_XHTML_XML_VALUE })
	public String modify(@RequestBody Roles r,@RequestParam(name="categoryid",required=false) String categoryId,Model model){
			Roles rm = repo.findByRoleId(r.getRoleId());
			model.addAttribute("allowed", new Integer(1));
			model.addAttribute("r",rm);
		   return "fragments/modifyroles";
	}
	@RequestMapping(value="/modify/submit",method=RequestMethod.POST)
	public String modifysubmit(@Valid @ModelAttribute("r")  Roles role, BindingResult bindingResult, Model model) {
			
		
		if (bindingResult.hasErrors()) {
			model.addAttribute("allowed", new Integer(1));
			model.addAttribute("r",role);
			 return "fragments/modifyroles";
		}
		repo.save(role);
		return "fragments/ok";
	}
}
