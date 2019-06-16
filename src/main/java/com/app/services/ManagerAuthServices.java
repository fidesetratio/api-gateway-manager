package com.app.services;

import java.util.ArrayList;
import java.util.List;

import org.assertj.core.util.Arrays;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.app.controller.datatables.data.DataServiceBase;
import com.app.controller.datatables.data.TableDataException;
import com.app.controller.datatables.models.PaginationCriteria;
import com.app.rest.model.ClientDetails;


public class ManagerAuthServices  extends DataServiceBase<ClientDetails>{
	
	private List<ClientDetails> list;
	
	public ManagerAuthServices() {
	}
	public ManagerAuthServices(String url) {
		 RestTemplate restTemplate = new RestTemplate();
		 ResponseEntity<ClientDetails[]> response   = restTemplate.getForEntity(url, ClientDetails[].class);
		 ClientDetails[] a = response.getBody();
		 list = new ArrayList<ClientDetails>();
		 for(ClientDetails d:a) {
			 list.add(d);
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
		return 0;
	}

	@Override
	protected List<ClientDetails> getData(PaginationCriteria paginationCriteria) throws TableDataException {
		// TODO Auto-generated method stub
		return list;
	}

}
