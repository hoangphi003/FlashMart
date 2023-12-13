package com.flashmartj6.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.flashmartj6.entity.Category;
import com.flashmartj6.entity.Product;
import com.flashmartj6.responsitory.ProductDAO;
import com.flashmartj6.services.CategoryService;
import com.flashmartj6.services.ProductService;

@CrossOrigin("*")
@RestController
public class CategoryRestful {

    @Autowired
    CategoryService categoryService;

    @GetMapping("user/category")
    public List<Category> getAll() {
     	return categoryService.findAll();
    }
    
}
