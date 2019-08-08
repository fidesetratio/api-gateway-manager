package com.app.manager.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Entity
@Table(name="application")
public class Application {
	
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)	
	@Column(name="appId")
	private Long appId;
	
	

	@Size(min=1, max=32, message="Application must be between 1 and 32 characters")
	@Column(name="applicationName", nullable=true, length=255)
	private String applicationName;
	
	
	@Column(name="description", nullable=true, length=255)
	private String description;
	
	
	@Size(min=1, max=32, message="Context must be between 1 and 32 characters")
	@Column(name="context", nullable=true, length=255)
	private String context;

	@Size(min=1, max=32, message="Photo must be between 1 and 32 characters")
	@Column(name="photo", nullable=true, length=255)
	private String photo;
	
	@Column(name="permitAll")
	private int permitAll;
	@Column(name="roleCategoryId")
	private int roleCategoryId;
	@Column(name="providerId")
	private int providerId;
	
	public Application(){
		this.photo = "imagenotavailable.png";
		this.permitAll = 0;
		this.providerId = 0;
		this.roleCategoryId =0;
	}

	public Long getAppId() {
		return appId;
	}

	public void setAppId(Long appId) {
		this.appId = appId;
	}

	public String getApplicationName() {
		return applicationName;
	}

	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public int getPermitAll() {
		return permitAll;
	}

	public void setPermitAll(int permitAll) {
		this.permitAll = permitAll;
	}

	public int getRoleCategoryId() {
		return roleCategoryId;
	}

	public void setRoleCategoryId(int roleCategoryId) {
		this.roleCategoryId = roleCategoryId;
	}

	public int getProviderId() {
		return providerId;
	}

	public void setProviderId(int providerId) {
		this.providerId = providerId;
	}
	

}
