package com.app.controller.template;

import java.util.ArrayList;
import java.util.List;

public class DataTablesWidget {
	
	private String title;
	
	private String destination;
	
	private List<String> headers;
	private String hiddenCategory = "0";
	
	private boolean buttons;
	
	
	
	public static Integer DEFAULT_FORM = 0;
	public static Integer CUSTOM_SEARCH = 1;
	public static Integer SIMPLE_BY_CATEGORY = 2;
	public static Integer SIMPLE_BY_CATEGORY_HIDDEN = 3;
	
	




	
	public int getTypeForm() {
		return typeForm;
	}


	public void setTypeForm(int typeForm) {
		this.typeForm = typeForm;
	}


	private int typeForm;
	
	private List<FormInput> searchForm;
	
	private SelectInput selectInput;
	
	public boolean useButton;
	


	
	
	public SelectInput getSelectInput() {
		return selectInput;
	}


	public void setSelectInput(SelectInput selectInput) {
		this.selectInput = selectInput;
		this.typeForm = SIMPLE_BY_CATEGORY;
	}

	public void setHiddenCategory(String categoryId){
		this.hiddenCategory = categoryId;
		this.typeForm = SIMPLE_BY_CATEGORY_HIDDEN;
	}

	public DataTablesWidget() {
		this.headers = new ArrayList<String>();
		this.typeForm = DEFAULT_FORM;
		this.searchForm = new ArrayList<FormInput>();
		this.useButton = true;
	}
	
	
	public void addFormInput(FormInput input){
		if(this.searchForm != null){
			this.searchForm.add(input);
			this.typeForm = CUSTOM_SEARCH;
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
		this.typeForm = DEFAULT_FORM;
		this.useButton = true;
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


	public boolean isUseButton() {
		return useButton;
	}


	public void setUseButton(boolean useButton) {
		this.useButton = useButton;
	}


	public String getHiddenCategory() {
		return hiddenCategory;
	}
	
	

}
