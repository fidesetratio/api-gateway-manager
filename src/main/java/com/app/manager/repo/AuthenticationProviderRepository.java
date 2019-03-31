package com.app.manager.repo;

import org.springframework.data.repository.CrudRepository;

import com.app.manager.model.AuthenticationProvider;
import com.app.manager.model.Link;

public interface AuthenticationProviderRepository extends CrudRepository<AuthenticationProvider,Long>{

}
