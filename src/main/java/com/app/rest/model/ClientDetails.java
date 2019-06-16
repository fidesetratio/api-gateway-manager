package com.app.rest.model;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class ClientDetails {
	
	@NotEmpty(message="Please Enter your clientId")
	@Size(min=4, max=32, message="Client Id must be between 4 and 32 characters")
	private String clientId;

	@NotEmpty(message="Please enter your resourceId")
	private String resourceIds;
	

	@NotEmpty(message="Please Enter your clientSecret")
	@Size(min=4, max=32, message="Client Secret must be between 4 and 32 characters")
	private String clientSecret;


	@NotEmpty(message = "Please enter your scope such as : (read,write)")
	private String scope;

	@NotEmpty(message = "Please enter your grant types such as : (password,refresh_token,authorization_code)")
	private String authorizationGrantTypes;
	

	private String webServerRedirectUri;

	private String authorities;
	
	private int accessTokenValidity;

	private int refreshTokenValidity;

	@NotEmpty(message = "Please enter your additional Information.")
	private String additionalInformation;

	private int autoApprove;
	
	public ClientDetails()
	{
		this.autoApprove = 0;
		this.accessTokenValidity = 0;
		this.refreshTokenValidity = 0;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getResourceIds() {
		return resourceIds;
	}

	public void setResourceIds(String resourceIds) {
		this.resourceIds = resourceIds;
	}

	public String getClientSecret() {
		return clientSecret;
	}

	public void setClientSecret(String clientSecret) {
		this.clientSecret = clientSecret;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	public String getAuthorizationGrantTypes() {
		return authorizationGrantTypes;
	}

	public void setAuthorizationGrantTypes(String authorizationGrantTypes) {
		this.authorizationGrantTypes = authorizationGrantTypes;
	}

	public String getWebServerRedirectUri() {
		return webServerRedirectUri;
	}

	public void setWebServerRedirectUri(String webServerRedirectUri) {
		this.webServerRedirectUri = webServerRedirectUri;
	}

	public String getAuthorities() {
		return authorities;
	}

	public void setAuthorities(String authorities) {
		this.authorities = authorities;
	}

	public int getAccessTokenValidity() {
		return accessTokenValidity;
	}

	public void setAccessTokenValidity(int accessTokenValidity) {
		this.accessTokenValidity = accessTokenValidity;
	}

	public int getRefreshTokenValidity() {
		return refreshTokenValidity;
	}

	public void setRefreshTokenValidity(int refreshTokenValidity) {
		this.refreshTokenValidity = refreshTokenValidity;
	}

	public String getAdditionalInformation() {
		return additionalInformation;
	}

	public void setAdditionalInformation(String additionalInformation) {
		this.additionalInformation = additionalInformation;
	}

	public int getAutoApprove() {
		return autoApprove;
	}

	public void setAutoApprove(int autoApprove) {
		this.autoApprove = autoApprove;
	}
	
	


}
