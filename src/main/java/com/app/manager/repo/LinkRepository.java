package com.app.manager.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.app.manager.model.Link;

public interface LinkRepository  extends CrudRepository<Link,Long>{
	public List<Link> findByActive(boolean active);
	public List<Link> findByActiveAndIsNew(boolean active, boolean n);
	public List<Link> findByIsNew(boolean n);
	public List<Link> findByIsNewUrl(boolean n);
	public Link findByPath(String path);
}

