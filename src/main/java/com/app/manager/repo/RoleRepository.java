package com.app.manager.repo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.app.manager.model.Roles;

public interface RoleRepository extends CrudRepository<Roles,Long>{
	@Query("select r from Roles r where r.roleCategory.roleCategoryId = :catid ")
	List<Roles> findByCategoryId(@Param("catid") Long catid);
	Roles findByRoleId(Long roleId);
	
}
