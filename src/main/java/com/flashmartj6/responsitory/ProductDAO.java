package com.flashmartj6.responsitory;

import com.flashmartj6.entity.Product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface ProductDAO extends JpaRepository<Product,Integer> {
	
    @Query("SELECT p FROM Product p WHERE p.ProductName LIKE %:keywords%")
    Page<Product> findByProductNameLike(@Param("keywords") String keywords, Pageable pageable);

    @Query("select p from Product p order by p.ProductName asc")
    Page<Product> SortByProductName(Pageable pageable);
    
    @Query("select p from Product p where p.category.id = :id")
	List<Product> getProductByCategoryId(@Param("id") Integer id);

    @Query("select p from Product p where p.category.id = ?1")
	List<Product> finByCategory(Optional<Integer> cid);

    @Modifying
    @Transactional
    @Query("DELETE FROM ProductDetail WHERE product.id IN (SELECT p.id FROM Product p WHERE p.id = :productId)")
	void deleteByRelatedId(@Param("productId") Integer id);

    @Query("select a from Product a order by a.id desc")
	List<Product> findAllDesc();

    @Query("SELECT p FROM Product p WHERE p.ProductName LIKE %:keywords%")
	List<Product> findProductByKeywords(@Param("keywords")String keyword);
}
