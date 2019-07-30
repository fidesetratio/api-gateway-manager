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
import com.app.controller.template.FormInput;
import com.app.controller.template.SimpleCrud;
import com.app.manager.model.RoleCategory;
import com.app.manager.repo.RolesCategoriesRepository;
import com.app.rest.model.EntityResponse;
import com.app.services.CategoryServiceTable;


public class CopyOfCategoryController {

/*	@Autowired
	private RolesCategoriesRepository repo;
	
	private Logger logger = LoggerFactory.getLogger(CopyOfCategoryController.class);

	@Override
	public DataTablesWidget init() {
		DataTablesWidget widget = new DataTablesWidget();
		widget.setTitle("Category");
		widget.setDestination("/category");
		widget.addHeader("Role CategoryId");
		widget.addHeader("Role CategoryId");
		widget.addHeader("Category Name");
		widget.addHeader("Category Description");
		FormInput formInput = new FormInput("Category","categoryId",FormInput.SELECT);
		formInput.addSelect("Amerika", "0");
		formInput.addSelect("Indonesia", "1");
		widget.addFormInput(formInput);
		widget.addFormInput(new FormInput("Context","context",FormInput.INPUT));
		widget.addFormInput(formInput);
		
		FormInput inputText = new FormInput("Search By Name", "name");
		widget.addFormInput(inputText);
	
		return widget;
	}

	@Override
	public TablePage listsPage(PaginationCriteria treq) {
		TablePaginator paginator = new SimplePaginator(new CategoryServiceTable(repo));
		TablePage tablePage =  paginator.getPage(treq);
		return tablePage;
	}

	@RequestMapping(value="/add",method=RequestMethod.GET)
	public String add(Model model){
			model.addAttribute("roleCategory", new RoleCategory());
			return "fragments/addcategory";
	}

	@RequestMapping(value="/add/submit",method=RequestMethod.POST)
	public String submit(@Valid @ModelAttribute("roleCategory")  RoleCategory roleCategory, BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			for(FieldError error:bindingResult.getFieldErrors()) {
				System.out.println(error.getField()+":"+error.getDefaultMessage());
			}
			
			model.addAttribute("roleCategory", roleCategory);
			return "fragments/addcategory";
		}
		
		repo.save(roleCategory);
		return "fragments/ok";
	}
	
	
	
	
	@RequestMapping(value="/remove",method=RequestMethod.POST,produces="application/json")
	public @ResponseBody EntityResponse remove(@RequestBody  List<RoleCategory> list) {
		logger.info("size:"+list.size());
		for(RoleCategory details:list){
			RoleCategory clientDetails = details;
			repo.deleteById(clientDetails.getRoleCategoryId());
		}
		EntityResponse response = new EntityResponse();
		return response;
		
		
		
	}

	
	
	
	
	
	@RequestMapping(value="/listttest",method=RequestMethod.GET,produces="application/json")
	public @ResponseBody EntityResponse listttest() {
		
		EntityResponse response = new EntityResponse();
		List<RoleCategory> l = (List<RoleCategory>) repo.findAll();
		response.setMessage("total:"+l.size());
		return response;
		
		
		
	}
	
	@RequestMapping(value="/modify",method=RequestMethod.POST,consumes="application/json",produces = { MediaType.TEXT_HTML_VALUE,
            MediaType.APPLICATION_XHTML_XML_VALUE })
	public String modify(@RequestBody RoleCategory detail,Model model){
			///logger.info("modify"+detail.getClientId());

			RoleCategory d = repo.findByRoleCategoryId(detail.getRoleCategoryId());
			
			model.addAttribute("roleCategory", d);
			return "fragments/modifycategory";
	}
	
	

	@RequestMapping(value="/modify/submit",method=RequestMethod.POST)
	public String modifysubmit(@Valid @ModelAttribute("roleCategory")  RoleCategory roleCategory, BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			logger.info("there is an error here");
			for(FieldError error:bindingResult.getFieldErrors()) {
				System.out.println(error.getField()+":"+error.getDefaultMessage());
			};
			
			
			model.addAttribute("roleCategory", roleCategory);
			return "fragments/modifycategory";
		}
		
		repo.save(roleCategory);
		return "fragments/ok";
	}
	*/

	

}
