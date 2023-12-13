package com.flashmartj6.services;

import java.util.List;

import com.flashmartj6.entity.Product;
import com.flashmartj6.entity.ProductDetail;

import javax.validation.Valid;

public interface ProductDetailService {

	ProductDetail save(ProductDetail detail);

	List<ProductDetail> findAll();
 
}
