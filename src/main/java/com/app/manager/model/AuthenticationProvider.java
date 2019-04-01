package com.app.manager.model;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import com.app.manager.converter.BooleanStringConverter;

@Entity
@Table(name="authentication_provider")
public class AuthenticationProvider {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)	
	private Long providerId;
	

	@Size(min=1, max=32, message="Provider Name must be between 1 and 32 characters")
	@Column(name="provider_name", nullable=true, length=255)
	private String providerName;
	
	@Column(name="url", nullable=true, length=255)
	private String url;
	
	
	@Column(name="type_provider", nullable=true, length=255)
	private String typeProvider;


	
	@Column(name="active")
	@Convert(converter=BooleanStringConverter.class)
	private boolean active;
	
	
	public Long getProviderId() {
		return providerId;
	}


	public void setProviderId(Long providerId) {
		this.providerId = providerId;
	}


	public String getProviderName() {
		return providerName;
	}


	public void setProviderName(String providerName) {
		this.providerName = providerName;
	}


	public String getUrl() {
		return url;
	}


	public void setUrl(String url) {
		this.url = url;
	}


	public String getTypeProvider() {
		return typeProvider;
	}


	public void setTypeProvider(String typeProvider) {
		this.typeProvider = typeProvider;
	}


	public boolean isActive() {
		return active;
	}


	public void setActive(boolean active) {
		this.active = active;
	}
	

	
	
	
	
}
