package com.app.controller.template;

import java.util.ArrayList;
import java.util.List;

public class DataTablesWidget {
	
	private String title;
	
	private String destination;
	
	private List<String> headers;
	
	private boolean buttons;
	
	
	




	public void setUseFormSearch(boolean useFormSearch) {
		this.useFormSearch = useFormSearch;
	}

	
	public boolean isUseFormSearch() {
		return useFormSearch;
	}
	
	
	
	private boolean useFormSearch;
	
	private List<FormInput> searchForm;

	
	
	public DataTablesWidget() {
		this.headers = new ArrayList<String>();
		this.useFormSearch = false;
		this.searchForm = new ArrayList<FormInput>();
	}
	
	
	public void addFormInput(FormInput input){
		if(this.searchForm != null){
			this.searchForm.add(input);
			this.useFormSearch = true;
		}
	}
	
	
	
	
	
	
	public void addHeader(String header) {
		if(this.headers == null) {
			this.headers = new ArrayList<String>();
		}
		this.headers.add(header);
	}
	
	public DataTablesWidget(String title, String destination, List<String> headers, boolean buttons) {
		super();
		this.title = title;
		this.destination = destination;
		this.headers = headers;
		this.buttons = buttons;
	}

	
	public DataTablesWidget(String title, String destination) {
		super();
		this.title = title;
		this.destination = destination;
		this.headers = new ArrayList<String>();
		this.buttons = false;
	}
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public List<String> getHeaders() {
		return headers;
	}

	public void setHeaders(List<String> headers) {
		this.headers = headers;
	}

	public boolean isButtons() {
		return buttons;
	}

	public void setButtons(boolean buttons) {
		this.buttons = buttons;
	}


	public List<FormInput> getSearchForm() {
		return searchForm;
	}


	public void setSearchForm(List<FormInput> searchForm) {
		this.searchForm = searchForm;
	}
	
	

}
