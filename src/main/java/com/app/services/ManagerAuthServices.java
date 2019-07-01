package com.app.services;

import java.util.ArrayList;
import java.util.List;

import org.assertj.core.util.Arrays;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.app.controller.datatables.data.DataServiceBase;
import com.app.controller.datatables.data.TableDataException;
import com.app.controller.datatables.models.OrderingCriteria;
import com.app.controller.datatables.models.PaginationCriteria;
import com.app.controller.datatables.models.SearchCriteria;
import com.app.rest.model.ClientDetails;


public class ManagerAuthServices  extends DataServiceBase<ClientDetails>{
	
	private List<ClientDetails> list;

	private List<ClientDetails> listt;
	
	public ManagerAuthServices() {
	}
	public ManagerAuthServices(String url) {
		 RestTemplate restTemplate = new RestTemplate();
		 ResponseEntity<ClientDetails[]> response   = restTemplate.getForEntity(url, ClientDetails[].class);
		 ClientDetails[] a = response.getBody();
		 listt = new  ArrayList<ClientDetails>();
		 list = new ArrayList<ClientDetails>();
		 int i =0;
		 for(ClientDetails d:a) {
			 list.add(d);
			 if(i<=1) {
				 listt.add(d);
				 
			 }
			 i++;
		 }
		 
		 
		 i =0;
		 for(ClientDetails d:a) {
			 list.add(d);
			 if(i<=1) {
				 listt.add(d);
				 
			 }
			 i++;
		 }
		 
		 i =0;
		 for(ClientDetails d:a) {
			 list.add(d);
			 if(i<=1) {
				 listt.add(d);
				 
			 }
			 i++;
		 }
		 
		 i =0;
		 for(ClientDetails d:a) {
			 list.add(d);
			 if(i<=1) {
				 listt.add(d);
				 
			 }
			 i++;
		 }
		 
		 i =0;
		 for(ClientDetails d:a) {
			 list.add(d);
			 if(i<=1) {
				 listt.add(d);
				 
			 }
			 i++;
		 }
		 
		 i =0;
		 for(ClientDetails d:a) {
			 list.add(d);
			 if(i<=1) {
				 listt.add(d);
				 
			 }
			 i++;
		 }
		 
		 
	}

	@Override
	public long countTotalEntries() throws TableDataException {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public long countFilteredEntries(PaginationCriteria paginationCriteria) throws TableDataException {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	protected List<ClientDetails> getData(PaginationCriteria paginationCriteria) throws TableDataException {
		// TODO Auto-generated method stub
		
		List<OrderingCriteria> order = paginationCriteria.getOrder();
		SearchCriteria search = paginationCriteria.getSearch();
		int draw = paginationCriteria.getDraw();
		int length = paginationCriteria.getLength();
		int start = paginationCriteria.getStart();
		System.out.println("draw:"+draw);
		System.out.println("length:"+length);

		System.out.println("start:"+start);
		listt = new ArrayList<ClientDetails>();
		for(int i= 0;i<length;i++) {
			listt.add(list.get(i));			
		}
		
		if(order.size()>0) {
			for(OrderingCriteria criteria : order) {
				System.out.println("column:"+criteria.getColumn());
				System.out.println("direction:"+criteria.getDir());
			
			}
		}
		if(search != null) {
			System.out.println("search name:"+search.getValue());
			System.out.println("regex:"+search.isRegex());
		}
		
		
		System.out.println("==");
		
		
		return listt;
	}

}
