package com.flashmartj6.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.flashmartj6.entity.Category;
import com.flashmartj6.responsitory.CategoryDAO;
import com.flashmartj6.services.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService{
	@Autowired
    CategoryDAO categoryDAO;
	
	@Override
	public List<Category> findAll() {
 		return categoryDAO.findAll();
	}

	@Override
	public Page<Category> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return categoryDAO.findAll(pageable);
	}

	@Override
	public Optional<Category> findById(Integer id) {
		// TODO Auto-generated method stub
		return categoryDAO.findById(id);
	}

	@Override
	public Category create(Category category) {
		// TODO Auto-generated method stub
		return categoryDAO.save(category);
	}

	@Override
	public Category update(Category category) {
		// TODO Auto-generated method stub
		return categoryDAO.save(category) ;
	}

	@Override
	public void delete(Category category) {
		// TODO Auto-generated method stub
		 categoryDAO.delete(category);
		
	}

	@Override
	public Page<Category> search(String CategoryName, Pageable pageable) {
		// TODO Auto-generated method stub
		return categoryDAO.findByNameContaining(CategoryName, pageable);
	}
	// Trong CategoryServiceImpl
	@Override
	public boolean existsByNameIgnoreCase(String categoryName) {
	    return categoryDAO.existsByNameIgnoreCase(categoryName) > 0;
	}

}
