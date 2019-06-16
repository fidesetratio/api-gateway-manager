package com.app.controller.datatables.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.app.controller.datatables.models.PaginationCriteria;
import com.fasterxml.jackson.databind.ObjectMapper;

public abstract class DataServiceBase<T> implements TableDataService {


	private ObjectMapper objectMapper = new ObjectMapper();

		@Override
	    public List<Map<String, Object>> getPageEntries(PaginationCriteria paginationCriteria) throws TableDataException {
	        List<T> data = getData(paginationCriteria);
	        List<Map<String, Object>> records = new ArrayList<>(data.size());
	        try {
	            data.forEach(i -> {
	                Map<String, Object> m = objectMapper.convertValue(i, Map.class);
	                records.add(m);
				    	
	               });
	        } catch (Exception e) {
	        	e.printStackTrace();
	            throw new TableDataException("", e);
	        }
	        return records;
	    }

	
		protected abstract List<T> getData(PaginationCriteria paginationCriteria) throws TableDataException;
		
		

}
