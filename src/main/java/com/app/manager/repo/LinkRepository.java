package com.app.manager.repo;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.app.manager.model.Link;

public interface LinkRepository  extends PagingAndSortingRepository<Link,Long>,JpaSpecificationExecutor{
	public List<Link> findByActive(boolean active);
	public Link findByPath(String path);
	public Link findByLinkId(Long linkId);
	public Page<Link> findByAppId(Long appId,Pageable pageable);
	public List<Link> findByAppId(Long appId);
}


