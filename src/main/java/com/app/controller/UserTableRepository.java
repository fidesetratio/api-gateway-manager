package com.app.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.app.controller.datatables.data.DataServiceBase;
import com.app.controller.datatables.data.TableDataException;
import com.app.controller.datatables.models.PaginationCriteria;
import com.app.rest.model.Product;

public class UserTableRepository extends DataServiceBase<Product> {

	static List<Product> products = new ArrayList<Product>();
	static {
		products = availableProduct();
	}
	

	@Override
	public long countTotalEntries() throws TableDataException {
		// TODO Auto-generated method stub
		return products.size();
	}

	private static List<Product> availableProduct() {
		// TODO Auto-generated method stub
		List<Product> products = new ArrayList<Product>();
		products.add(new Product("Patar Timotius", new BigDecimal(2000), "1", 1));
		products.add(new Product("Patar Timotius1", new BigDecimal(2001), "1", 1));
		products.add(new Product("Patar Timotius2", new BigDecimal(2003), "1", 1));
		products.add(new Product("Patar Timotius3", new BigDecimal(2004), "1", 1));
		
		
		
		return products;
	}

	@Override
	public long countFilteredEntries(PaginationCriteria paginationCriteria) throws TableDataException {
		// TODO Auto-generated method stub
		System.out.println("countFiltered Entries");
		return 0;
	}

	@Override
	protected List<Product> getData(PaginationCriteria paginationCriteria) throws TableDataException {
		// TODO Auto-generated method stub
		System.out.println("search  nya berapa :"+paginationCriteria);
		
		 products.stream().filter(u->u.getName().contains(paginationCriteria.getSearch().getValue())).collect(Collectors.toList());
		
		 return products;
		 //return availableProduct();
	}

}
	