package com.app.controller;

import java.util.ArrayList;
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

import com.app.manager.model.RoleCategory;
import com.app.manager.model.Roles;
import com.app.manager.repo.RoleRepository;
import com.app.manager.repo.RolesCategoriesRepository;

@Controller
@RequestMapping("/category")
public class CategoryController {
	
	@Autowired
	private RolesCategoriesRepository roleCategoryRepo;
	

	@Autowired
	private RoleRepository roleRepository;
	
	@RequestMapping("/category")
	public String category(Model model){
		RoleCategory roleCategory = new RoleCategory();
		model.addAttribute("roleCategory",roleCategory);
		List<RoleCategory> roleCategories = (List<RoleCategory>)roleCategoryRepo.findAll();
		model.addAttribute("roleCategories",roleCategories);
		return "category-management";
	}
	
	
	@RequestMapping(value="/category-form-submit",method=RequestMethod.POST)
	public String categorySubmit(@Valid @ModelAttribute("roleCategory")  RoleCategory roleCategory, BindingResult bindingResult, Model model){
	
		
		if(roleCategory.getCategoryName() != null) {
			RoleCategory r = roleCategoryRepo.findByCategoryName(roleCategory.getCategoryName().trim());
			if(r != null) {
				  bindingResult.rejectValue("categoryName", "error.categoryName",
						  "Category Name already exist"); 
						  
			}
		}
		 
		if (bindingResult.hasErrors()) {
			model.addAttribute("roleCategory",roleCategory);
			return "fragments/addcategory";
		}
		
		roleCategoryRepo.save(roleCategory);
		return "fragments/addcategory";
		
	}
	

	@RequestMapping(value="/list-categories",method=RequestMethod.GET)
	public String listCategories(Model model){
		List<RoleCategory> links = new ArrayList<RoleCategory>();
		links = (List<RoleCategory>)roleCategoryRepo.findAll();
		model.addAttribute("roleCategories",links);
		return "fragments/tablecategory";
	}
	
	@RequestMapping(value="/delete-category",method=RequestMethod.GET)
	public String deleteCategory(@RequestParam(value = "categoryId", required = false) Long categoryId, Model model){
			if(categoryId != null) {
				RoleCategory cat = roleCategoryRepo.findByRoleCategoryId(categoryId);
				if(cat != null) {
					for(Roles r : cat.getRoles()) {
						roleRepository.delete(r);
					}
					
					roleCategoryRepo.delete(cat);
				}
			};
		
			return "fragments/ok";
		
	}

}
