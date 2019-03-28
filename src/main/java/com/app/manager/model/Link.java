package com.app.manager.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.app.manager.converter.BooleanStringConverter;
import com.app.manager.converter.ListDelimiterConverter;

@Entity
@Table(name="links")
public class Link {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)	
	private Long linkId;
	
	@Column(name="context", nullable=true, length=255)
	private String context;
	
	@Column(name="service_id", nullable=true, length=255)
	private String serviceId;
	
	
	
	@Column(name="url", nullable=true, length=255)
	private String url;
	
	@Column(name="path", nullable=true, length=255)
	private String path;
	
	
	@Column(name="active")
	@Convert(converter=BooleanStringConverter.class)
	private boolean active;
	
	
	@Column(name="permitall")
	@Convert(converter=BooleanStringConverter.class)
	private boolean permitAll;

	
	@Column(name="isNew")
	@Convert(converter=BooleanStringConverter.class)
	private boolean isNew;

	@Column(name="isNewUrl")
	@Convert(converter=BooleanStringConverter.class)
	private boolean isNewUrl;
	
	
	@Column(name="roles")
	@Convert(converter=ListDelimiterConverter.class)
	private List<String> roles = new ArrayList<String>();
	
	@Column(name="categoryId")
	private Long categoryId;
    
    
	
	public Link(){
		this.roles = new ArrayList<String>();
		this.categoryId = new Long(0);
		this.isNew = true;
		this.isNewUrl = true;
		this.permitAll = false;
		this.creationDateTime = new Date();
		
		
	}
	
	
	public Long getCategoryId() {
		return categoryId;
	}


	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}


	@Temporal(TemporalType.TIMESTAMP)
    private Date creationDateTime;


	public Long getLinkId() {
		return linkId;
	}


	public void setLinkId(Long linkId) {
		this.linkId = linkId;
	}


	public String getContext() {
		return context;
	}


	public void setContext(String context) {
		this.context = context;
	}


	public String getUrl() {
		return url;
	}


	public void setUrl(String url) {
		this.url = url;
	}


	public boolean isActive() {
		return active;
	}


	public void setActive(boolean active) {
		this.active = active;
	}


	public Date getCreationDateTime() {
		return creationDateTime;
	}


	public void setCreationDateTime(Date creationDateTime) {
		this.creationDateTime = creationDateTime;
	}


	public String getServiceId() {
		return serviceId;
	}


	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}


	public String getPath() {
		return path;
	}


	public void setPath(String path) {
		this.path = path;
	}


	public boolean isNew() {
		return isNew;
	}


	public void setNew(boolean isNew) {
		this.isNew = isNew;
	}


	public List<String> getRoles() {
		return roles;
	}


	public void setRoles(List<String> roles) {
		this.roles = roles;
	}


	public boolean isNewUrl() {
		return isNewUrl;
	}


	public void setNewUrl(boolean isNewUrl) {
		this.isNewUrl = isNewUrl;
	}


	public boolean isPermitAll() {
		return permitAll;
	}


	public void setPermitAll(boolean permitAll) {
		this.permitAll = permitAll;
	}
	
	
	
		
	

}