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
@RequestMapping("/role")
public class RoleController {
	
	
	@Autowired
	private RoleRepository roleRepository;
	
	

	@Autowired
	private RolesCategoriesRepository roleCategoryRepo;
	
		
		@RequestMapping("/role-category")
		public String roleCategory(Model model){
	
			List<Roles> roles = new ArrayList<Roles>();
		
			Roles r = new Roles();
			r.setRoleCategory(new RoleCategory());
			r.getRoleCategory().setRoleCategoryId(new Long(0));
			
			List<RoleCategory> roleCategory = (List<RoleCategory>)roleCategoryRepo.findAll();
			model.addAttribute("categories", roleCategory);
			model.addAttribute("role", roles);
			model.addAttribute("r",r);
			return "role-category";

		}


		@RequestMapping(value="/list-roles",method=RequestMethod.GET)
		public String listRoles(@RequestParam(value = "cat", required = false) Long cat,Model model){
			List<Roles> roles = new ArrayList<Roles>();
			if(cat != null)
				roles = roleRepository.findByCategoryId(cat);
		
			model.addAttribute("role", roles);
			return "fragments/tableroles";
		}
	
		
		@RequestMapping(value="/role-form-submit",method=RequestMethod.POST)
		public String roleSubmit(@Valid @ModelAttribute("r")  Roles r, BindingResult bindingResult, Model model){
			System.out.println(r.getRoleCategory().getRoleCategoryId());
			if (bindingResult.hasErrors()) {
				model.addAttribute("r",r);
				return "fragments/addroles";
			}else {
				roleRepository.save(r);
			}
			
				return "fragments/addroles";
			
		}
		
		@RequestMapping(value="/delete-role",method=RequestMethod.GET)
		public String deleteRole(@RequestParam(value = "roleid", required = false) Long roleId, Model model){
				if(roleId != null) {
				Roles r =	roleRepository.findByRoleId(roleId);
				roleRepository.delete(r);
				};
			
				return "fragments/ok";
			
		}

}
