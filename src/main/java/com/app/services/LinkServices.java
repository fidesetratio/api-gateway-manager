package com.app.services;

import java.util.List;

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
import com.app.manager.model.Link;
import com.app.manager.repo.LinkRepository;
import com.app.services.specification.RoleCategorySpecification;
import com.app.services.specification.SearchOperation;
import com.app.services.specification.SpecSearchCriteria;

public class LinkServices  extends DataServiceBase<Link>{
	
	private LinkRepository repo;
	private Page<Link> page;
	
	
	public LinkServices(LinkRepository repo){
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
	protected List<Link> getData(PaginationCriteria paginationCriteria)
			throws TableDataException {
		// TODO Auto-generated method stub
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
							pageable =  PageRequest.of(p, length, Sort.by("linkId").ascending());

						}else {
							pageable =  PageRequest.of(p, length, Sort.by("linkId").descending());
						}
					};
					
					
					if(column == 1) {
						if(dir.equals("asc")) {
							pageable =  PageRequest.of(p, length, Sort.by("linkId").ascending());

						}else {
							pageable =  PageRequest.of(p, length, Sort.by("linkId").descending());
						}
					};
					
					if(column == 2) {
						if(dir.equals("asc")) {
							pageable =  PageRequest.of(p, length, Sort.by("context").ascending());

						}else {
							pageable =  PageRequest.of(p, length, Sort.by("context").descending());
						}
					};
					
					if(column == 3) {
						if(dir.equals("asc")) {
							pageable =  PageRequest.of(p, length, Sort.by("serviceId").ascending());

						}else {
							pageable =  PageRequest.of(p, length, Sort.by("serviceId").descending());
						}
					};
					if(column == 4) {
						if(dir.equals("asc")) {
							pageable =  PageRequest.of(p, length, Sort.by("permitAll").ascending());

						}else {
							pageable =  PageRequest.of(p, length, Sort.by("permitAll").descending());
						}
					};
				
					if(column == 5) {
						if(dir.equals("asc")) {
							pageable =  PageRequest.of(p, length, Sort.by("active").ascending());

						}else {
							pageable =  PageRequest.of(p, length, Sort.by("active").descending());
						}
					};
				
				}
			}
			
			page = this.repo.findAll(pageable);
			 
			 
			if(search != null) {
				searchvalue = search.getValue();
				if(searchvalue !=  null && !searchvalue.trim().equals("")) {
					System.out.println("search value="+searchvalue);
					RoleCategorySpecification clientIdSpecification = new RoleCategorySpecification(new SpecSearchCriteria("context", SearchOperation.CONTAINS, searchvalue));
					RoleCategorySpecification resourceIdSpecification = new RoleCategorySpecification(new SpecSearchCriteria("serviceId", SearchOperation.CONTAINS, searchvalue));
					RoleCategorySpecification permitAll = new RoleCategorySpecification(new SpecSearchCriteria("permitAll", SearchOperation.CONTAINS, searchvalue));
					page = this.repo.findAll(Specification.where(clientIdSpecification).or(resourceIdSpecification).or(permitAll),pageable);
				}
				
			}
		
			
			return page.getContent();

	}

}
