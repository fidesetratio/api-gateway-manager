package com.app.manager.repo;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.app.manager.model.AuthenticationProvider;
import com.app.manager.model.Link;

public interface AuthenticationProviderRepository extends PagingAndSortingRepository<AuthenticationProvider,Long>,JpaSpecificationExecutor{
	
	public AuthenticationProvider findByProviderName(String providerName);
	public AuthenticationProvider findByProviderId(Long providerId);

}
