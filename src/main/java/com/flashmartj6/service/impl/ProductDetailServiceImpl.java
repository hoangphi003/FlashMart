package com.flashmartj6.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.flashmartj6.entity.Product;
import com.flashmartj6.entity.ProductDetail;
import com.flashmartj6.responsitory.ProductDAO;
import com.flashmartj6.responsitory.ProductDetailDAO;
import com.flashmartj6.services.ProductDetailService;
import com.flashmartj6.services.ProductService;

import javax.validation.Valid;

@Service
public class ProductDetailServiceImpl implements ProductDetailService {
	@Autowired
	ProductDetailDAO productDetailDAO;

	@Override
	public ProductDetail save(ProductDetail detail) {
		return productDetailDAO.save(detail);
	}
	
	@Override
	public List<ProductDetail> findAll() {
 		return productDetailDAO.findAll();
	}
}
