package com.app.services;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import com.app.controller.datatables.data.DataServiceBase;
import com.app.controller.datatables.data.TableDataException;
import com.app.controller.datatables.models.OrderingCriteria;
import com.app.controller.datatables.models.PaginationCriteria;
import com.app.controller.datatables.models.SearchCriteria;
import com.app.controller.datatables.models.SearchEntity;
import com.app.controller.template.SelectValue;
import com.app.manager.model.RoleCategory;
import com.app.manager.model.Roles;
import com.app.manager.repo.RoleRepository;
import com.app.services.specification.RoleCategorySpecification;
import com.app.services.specification.RolesSpecification;
import com.app.services.specification.SearchOperation;
import com.app.services.specification.SpecSearchCriteria;

public class RoleServiceTable  extends DataServiceBase<Roles> {

	
	private RoleRepository repo;
	private Page<Roles> page;
	private static Logger logger = LoggerFactory.getLogger(RoleServiceTable.class);
	
	
	public RoleServiceTable(RoleRepository repo){
		this.repo = repo;
		Pageable pageable = PageRequest.of(0, 10);
		page = this.repo.findAll(pageable);
	}
	@Override
	public long countTotalEntries() throws TableDataException {
		// TODO Auto-generated method stub
		return page.getTotalElements();
	}

	@Override
	public long countFilteredEntries(PaginationCriteria paginationCriteria)
			throws TableDataException {
		// TODO Auto-generated method stub
		return page.getTotalElements();
	}

	@Override
	protected List<Roles> getData(PaginationCriteria paginationCriteria)
			throws TableDataException {
		// TODO Auto-generated method stub
		List<OrderingCriteria> order = paginationCriteria.getOrder();
		SearchCriteria search = paginationCriteria.getSearch();
		int draw = paginationCriteria.getDraw();
		int length = paginationCriteria.getLength();
		int start = paginationCriteria.getStart();
		String searchvalue = null;
		int p = Math.round(start/length);
		Pageable pageable = PageRequest.of(p, length);
		List<SearchEntity> complex = paginationCriteria.getSearchcomplex();
		for(SearchEntity s:complex){
			logger.info("complex search:"+s);
		}
		
		if(paginationCriteria.getSelectcategory() != null)
		{
			System.out.println(paginationCriteria.getSelectcategory());
		}
		if(order.size()>0) {
			for(OrderingCriteria criteria : order) {
				int column = criteria.getColumn();
				String dir = criteria.getDir();
				
				if(column == 0) {
					if(dir.equals("asc")) {
						pageable =  PageRequest.of(p, length, Sort.by("roleId").ascending());

					}else {
						pageable =  PageRequest.of(p, length, Sort.by("roleId").descending());
					}
				};
				
				
				if(column == 1) {
					if(dir.equals("asc")) {
						pageable =  PageRequest.of(p, length, Sort.by("roleId").ascending());

					}else {
						pageable =  PageRequest.of(p, length, Sort.by("roleId").descending());
					}
				};
				
				if(column == 2) {
					if(dir.equals("asc")) {
						pageable =  PageRequest.of(p, length, Sort.by("roleName").ascending());

					}else {
						pageable =  PageRequest.of(p, length, Sort.by("roleName").descending());
					}
				};
				
				
			
			}
		}
		
		SelectValue select = paginationCriteria.getSelectcategory();
		if(Integer.parseInt(select.getValue())>0){
			RolesSpecification categoryId = new RolesSpecification(new SpecSearchCriteria("roleCategoryId", SearchOperation.EQUALITY, Long.parseLong(select.getValue())),new RoleCategory());
			page = this.repo.findAll(Specification.where(categoryId),pageable);
		}else{
			page = this.repo.findAll(pageable);
		};
		if(search != null) {
			searchvalue = search.getValue();
			if(searchvalue !=  null && !searchvalue.trim().equals("")) {
				System.out.println("search value="+searchvalue);
				RolesSpecification roleName = new RolesSpecification(new SpecSearchCriteria("roleName", SearchOperation.CONTAINS, searchvalue));
		
				if(Integer.parseInt(select.getValue())>0){
					RolesSpecification categoryId = new RolesSpecification(new SpecSearchCriteria("roleCategoryId", SearchOperation.EQUALITY, select.getValue()),new RoleCategory());
					page = this.repo.findAll(Specification.where(roleName).and(categoryId),pageable);
				}else{
					page = this.repo.findAll(Specification.where(roleName),pageable);
				}
				
				
				
			}
			
		}
	
		
		return page.getContent();
	}


}
