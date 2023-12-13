package com.flashmartj6.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import com.flashmartj6.entity.Product;

public interface ProductService {

	List<Product> findAll();

	Product save(Product product);

	void deleteById(Integer id);

	Product getOne(Integer id);

	Page<Product>  findProductNameLike(String string,Pageable pageable);

	Page<Product> SortByProductName(Pageable pageable);

	Page<Product> findAllByPage(Pageable pageable);

	Product findById(Integer id);

	boolean existsById(Integer id);

	List<Product> getProductByCategoryId(Integer id);

	List<Product> findByCategory(Optional<Integer> cid);

	List<Product> findAllDesc();

	List<Product> findProductByKeywords(String keyword);
	

}
