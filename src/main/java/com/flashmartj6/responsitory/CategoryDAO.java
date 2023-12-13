package com.flashmartj6.responsitory;

import com.flashmartj6.entity.Category;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

 @Repository
public interface CategoryDAO extends JpaRepository<Category,Integer> {
//	 Page<Category> findByNameContaining(String CategoryName, Pageable pageable);
	 
	 @Query("SELECT c FROM Category c WHERE c.CategoryName LIKE %:keyword%")
	 Page<Category> findByNameContaining(@Param("keyword") String CategoryName, Pageable pageable);

		// Trong CategoryDAO
	 @Query("SELECT COUNT(c) FROM Category c WHERE LOWER(c.CategoryName) = LOWER(:categoryName)")
	 int existsByNameIgnoreCase(@Param("categoryName") String categoryName);
}
