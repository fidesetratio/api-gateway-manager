package com.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.app.controller.datatables.SimplePaginator;
import com.app.controller.datatables.TablePaginator;
import com.app.controller.datatables.models.PaginationCriteria;
import com.app.controller.datatables.models.TablePage;
import com.app.controller.template.DataTablesWidget;
import com.app.controller.template.SelectInput;
import com.app.controller.template.SimpleCrud;
import com.app.manager.model.RoleCategory;
import com.app.manager.repo.RoleRepository;
import com.app.manager.repo.RolesCategoriesRepository;
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
		widget.addHeader("Role Id");
		widget.addHeader("Role Id");
		widget.addHeader("Role Name");
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
	
	

}
