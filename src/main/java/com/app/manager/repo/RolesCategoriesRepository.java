package com.app.manager.repo;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.app.manager.model.RoleCategory;

public interface RolesCategoriesRepository extends PagingAndSortingRepository<RoleCategory,Long>,JpaSpecificationExecutor{

	public RoleCategory findByRoleCategoryId(Long id);
	public RoleCategory findByCategoryName(String name);
	
}
