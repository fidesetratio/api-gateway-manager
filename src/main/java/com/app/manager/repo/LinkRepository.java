package com.app.manager.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.app.manager.model.Link;

public interface LinkRepository  extends CrudRepository<Link,Long>{
	public List<Link> findByActive(boolean active);
	public Link findByPath(String path);
	public Link findByLinkId(Long linkId);
}

