package com.app.manager.repo;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.app.manager.model.Application;


public interface ApplicationRepository  extends PagingAndSortingRepository<Application,Long>,JpaSpecificationExecutor {

}
