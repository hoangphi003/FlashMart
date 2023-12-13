package com.flashmartj6.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.flashmartj6.entity.Authority;


public interface AuthorityService {
  
	List<Authority> findAll();

	public Page<Authority> findAll(Pageable pageable);

	public Optional<Authority> findById(Integer id);

	public Authority create(Authority authority);

	public Authority update(Authority authority);

	public void delete(Authority authority);
	
	
//	Page<Authority> search(String AccountId, Pageable pageable);
	
//	public boolean existsByNameIgnoreCase(String CategoryName);
}
