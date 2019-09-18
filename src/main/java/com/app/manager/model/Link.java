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
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.app.manager.converter.BooleanStringConverter;
import com.app.manager.converter.ListDelimiterConverter;

@Entity
@Table(name="links")
public class Link {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)	
	@Column(name="linkId")
	private Long linkId;
	
	
	
	@Size(min=1, max=60, message="Context must be between 1 and 60 characters")
	@Column(name="context", nullable=true, length=255)
	private String context;
	
	@Size(min=1, max=60, message="Service Id must be between 1 and 60 characters")
	@Column(name="service_id", nullable=true, length=255)
	private String serviceId;
	

	@NotEmpty(message="Url must not empty")
	@Column(name="url", nullable=true, length=255)
	private String url;
	
	@NotEmpty(message="Path must not empty")
	@Column(name="path", nullable=true, length=255)
	private String path;
	
	
	@Column(name="active")
	@Convert(converter=BooleanStringConverter.class)
	private boolean active;
	
	
	@Column(name="permitall")
	@Convert(converter=BooleanStringConverter.class)
	private boolean permitAll;
	
	@Column(name="roles")
	@Convert(converter=ListDelimiterConverter.class)
	private List<String> roles = new ArrayList<String>();
	
	
	@Column(name="scopes")
	@Convert(converter=ListDelimiterConverter.class)
	private List<String> scopes = new ArrayList<String>();
	



	@Column(name="categoryId")
	private Long categoryId;
	
	
	@Column(name="appId")
	private Long appId;
	

	@Column(name="providerId")
	private Long providerId;
	

	@Column(name="stripPrefix")
	@Convert(converter=BooleanStringConverter.class)
	private boolean stripPrefix;
    
	@Column(name="sensitiveHeaders")
	@Convert(converter=ListDelimiterConverter.class)
	private List<String> sensitiveHeaders = new ArrayList<String>();
	
	
	
	@Column(name="resourceid", nullable=true, length=255)
	private String resourceid;
	
	
	
	
	@Column(name="strict", nullable=true, length=255)
	@Convert(converter=BooleanStringConverter.class)
	private boolean strict;
	
	
	public Link(){
		this.roles = new ArrayList<String>();
		this.categoryId = new Long(1);
		this.permitAll = false;
		this.creationDateTime = new Date();
		this.active = true;
		this.serviceId="";
		this.url = null;
		this.stripPrefix = true;
		this.strict = false;
		this.resourceid = "";
		this.scopes = new ArrayList<String>();
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

	public List<String> getRoles() {
		return roles;
	}


	public void setRoles(List<String> roles) {
		this.roles = roles;
	}


	public boolean isPermitAll() {
		return permitAll;
	}


	public void setPermitAll(boolean permitAll) {
		this.permitAll = permitAll;
	}


	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append(this.serviceId+","+this.context+","+this.path+","+this.url+","+this.active+","+this.permitAll+","+this.categoryId+","+this.getRoles().size());
		return buffer.toString();
	}


	public Long getProviderId() {
		return providerId;
	}


	public void setProviderId(Long providerId) {
		this.providerId = providerId;
	}


	public boolean isStripPrefix() {
		return stripPrefix;
	}


	public void setStripPrefix(boolean stripPrefix) {
		this.stripPrefix = stripPrefix;
	}


	public List<String> getSensitiveHeaders() {
		return sensitiveHeaders;
	}


	public void setSensitiveHeaders(List<String> sensitiveHeaders) {
		this.sensitiveHeaders = sensitiveHeaders;
	}


	public Long getAppId() {
		return appId;
	}


	public void setAppId(Long appId) {
		this.appId = appId;
	}


	public String getResourceid() {
		return resourceid;
	}


	public void setResourceid(String resourceid) {
		this.resourceid = resourceid;
	}


	public boolean isStrict() {
		return strict;
	}


	public void setStrict(boolean strict) {
		this.strict = strict;
	}
	
	public List<String> getScopes() {
		return scopes;
	}


	public void setScopes(List<String> scopes) {
		this.scopes = scopes;
	}
}