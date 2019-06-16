package com.app.rest.model;

public class ConfigPaging {
	
	private Integer total;
	private Integer visiblePages;
	
	private String redirectUrl;
	
	
	
	public ConfigPaging(Integer total, Integer visiblePages) {
		super();
		this.total = total;
		this.visiblePages = visiblePages;
	}
	
	
	
	public ConfigPaging(Integer total, Integer visiblePages, String redirectUrl) {
		super();
		this.total = total;
		this.visiblePages = visiblePages;
		this.redirectUrl = redirectUrl;
	}



	public Integer getTotal() {
		return total;
	}
	public void setTotal(Integer total) {
		this.total = total;
	}
	public Integer getVisiblePages() {
		return visiblePages;
	}
	public void setVisiblePages(Integer visiblePages) {
		this.visiblePages = visiblePages;
	}
	public String getRedirectUrl() {
		return redirectUrl;
	}
	public void setRedirectUrl(String redirectUrl) {
		this.redirectUrl = redirectUrl;
	}
	
}
