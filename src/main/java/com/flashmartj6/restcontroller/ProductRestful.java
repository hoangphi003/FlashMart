package com.flashmartj6.restcontroller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.flashmartj6.entity.Product;
import com.flashmartj6.responsitory.ProductDAO;
import com.flashmartj6.services.ProductService;

@CrossOrigin("*")
@RestController
public class ProductRestful {

	@Autowired
	ProductService productService;

	@GetMapping("/user/product")
	public List<Product> getAll() {
		return productService.findAll();
	}
	
	@GetMapping("/user/productnew")
	public List<Product> getAllNew() {
		return productService.findAllDesc();
	}

	@GetMapping("/user/product/{id}")
	public ResponseEntity<Product> getOneById(@PathVariable("id") Integer id) {
		if (!productService.existsById(id)) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(productService.getOne(id));
	}

	@GetMapping("/user/productcategory/{id}")
	public ResponseEntity<List<Product>> getProcuctByCategory(@PathVariable("id") Integer id) {
		return ResponseEntity.ok(productService.getProductByCategoryId(id));
	}
	
	@GetMapping("/user/productsearch/{keyword}")
	public List<Product> getProductByKeywords(@PathVariable("keyword") String keyword) {
		return productService.findProductByKeywords(keyword);
	}
}
