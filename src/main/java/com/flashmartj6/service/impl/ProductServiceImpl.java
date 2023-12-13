package com.flashmartj6.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.flashmartj6.entity.Product;
import com.flashmartj6.responsitory.ProductDAO;
import com.flashmartj6.services.ProductService;

@Service
public class ProductServiceImpl implements ProductService {
	@Autowired
	ProductDAO productDAO;

	@Override
	public List<Product> findAllDesc() {
		return productDAO.findAllDesc();
	}

	@Override
	public Product save(Product product) {
		return productDAO.save(product);
	}

	@Override
	public void deleteById(Integer id) {
		productDAO.deleteByRelatedId(id);
		productDAO.deleteById(id);
	}

	@Override
	public Product getOne(Integer id) {
		return productDAO.getOne(id);
	}

	@Override
	public Page<Product> findProductNameLike(String keywords, Pageable pageable) {
		return productDAO.findByProductNameLike(keywords, pageable);
	}

	@Override
	public Page<Product> SortByProductName(Pageable pageable) {
		return productDAO.SortByProductName(pageable);
	}

	@Override
	public Page<Product> findAllByPage(Pageable pageable) {
		return productDAO.findAll(pageable);
	}

	@Override
	public Product findById(Integer id) {
		return productDAO.findById(id).get();
	}

	@Override
	public boolean existsById(Integer id) {
		return productDAO.existsById(id);
	}

	@Override
	public List<Product> getProductByCategoryId(Integer id) {
		return productDAO.getProductByCategoryId(id);
	}

	@Override
	public List<Product> findByCategory(Optional<Integer> cid) {
		return productDAO.finByCategory(cid);
	}

	@Override
	public List<Product> findAll() {
		return productDAO.findAll();
	}
	
	@Override
	public List<Product> findProductByKeywords(String keyword) {
		return productDAO.findProductByKeywords(keyword);
	}
}
