package com.flashmartj6.services;

import java.util.List;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.flashmartj6.entity.Category;
import com.flashmartj6.entity.Category;

public interface CategoryService {

	List<Category> findAll();

	public Page<Category> findAll(Pageable pageable);

	public Optional<Category> findById(Integer id);

	public Category create(Category category);

	public Category update(Category category);

	public void delete(Category category);

	Page<Category> search(String CategoryName, Pageable pageable);
	
	public boolean existsByNameIgnoreCase(String CategoryName);
}
