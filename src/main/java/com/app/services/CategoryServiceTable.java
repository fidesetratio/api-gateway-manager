package com.app.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.app.controller.datatables.data.DataServiceBase;
import com.app.controller.datatables.data.TableDataException;
import com.app.controller.datatables.models.OrderingCriteria;
import com.app.controller.datatables.models.PaginationCriteria;
import com.app.controller.datatables.models.SearchCriteria;
import com.app.manager.model.RoleCategory;
import com.app.manager.repo.RolesCategoriesRepository;
import com.app.services.specification.RoleCategorySpecification;
import com.app.services.specification.SearchOperation;
import com.app.services.specification.SpecSearchCriteria;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

public class CategoryServiceTable extends DataServiceBase<RoleCategory> {
	 
	private RolesCategoriesRepository repo;
	private Page<RoleCategory> page;
	
	
	public CategoryServiceTable(RolesCategoriesRepository repo){
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
	protected List<RoleCategory> getData(PaginationCriteria paginationCriteria)
			throws TableDataException {
		// TODO Auto-generated method stub
		List<OrderingCriteria> order = paginationCriteria.getOrder();
		SearchCriteria search = paginationCriteria.getSearch();
		int draw = paginationCriteria.getDraw();
		int length = paginationCriteria.getLength();
		int start = paginationCriteria.getStart();
		
		System.out.println("draw:"+draw);
		System.out.println("length:"+length);

		System.out.println("start:"+start);
		String searchvalue = null;
		int p = Math.round(start/length);
		System.out.println("p:"+p);
	
		Pageable pageable = PageRequest.of(p, length);
		
		if(order.size()>0) {
			for(OrderingCriteria criteria : order) {
				int column = criteria.getColumn();
				String dir = criteria.getDir();
				System.out.println("column:"+column);
				System.out.println("direction:"+dir);
				
				
				if(column == 0) {
					if(dir.equals("asc")) {
						pageable =  PageRequest.of(p, length, Sort.by("roleCategoryId").ascending());

					}else {
						pageable =  PageRequest.of(p, length, Sort.by("roleCategoryId").descending());
					}
				};
				
				
				if(column == 1) {
					if(dir.equals("asc")) {
						pageable =  PageRequest.of(p, length, Sort.by("roleCategoryId").ascending());

					}else {
						pageable =  PageRequest.of(p, length, Sort.by("roleCategoryId").descending());
					}
				};
				
				if(column == 2) {
					if(dir.equals("asc")) {
						pageable =  PageRequest.of(p, length, Sort.by("categoryName").ascending());

					}else {
						pageable =  PageRequest.of(p, length, Sort.by("categoryName").descending());
					}
				};
				
				if(column == 3) {
					if(dir.equals("asc")) {
						pageable =  PageRequest.of(p, length, Sort.by("categoryDescription").ascending());

					}else {
						pageable =  PageRequest.of(p, length, Sort.by("categoryDescription").descending());
					}
				};
				if(column == 4) {
					if(dir.equals("asc")) {
						pageable =  PageRequest.of(p, length, Sort.by("categoryDescription").ascending());

					}else {
						pageable =  PageRequest.of(p, length, Sort.by("categoryDescription").descending());
					}
				};
			
			}
		}
		
		page = this.repo.findAll(pageable);
		 
		 
		if(search != null) {
			searchvalue = search.getValue();
			if(searchvalue !=  null && !searchvalue.trim().equals("")) {
				System.out.println("search value="+searchvalue);
				RoleCategorySpecification clientIdSpecification = new RoleCategorySpecification(new SpecSearchCriteria("categoryName", SearchOperation.CONTAINS, searchvalue));
				RoleCategorySpecification resourceIdSpecification = new RoleCategorySpecification(new SpecSearchCriteria("categoryDescription", SearchOperation.CONTAINS, searchvalue));
				page = this.repo.findAll(Specification.where(clientIdSpecification).or(resourceIdSpecification),pageable);
			}
			
		}
	
		
		return page.getContent();
	}

}
