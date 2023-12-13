package com.flashmartj6.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.flashmartj6.entity.Order;
import com.flashmartj6.responsitory.*;


public interface ThongkeService {
public List<Order> findAll() ; //in ra lưu vào danh sách
	
	public Page<Order> findAll(Pageable pageable) ;  //phân trang
	
	public Optional<Order> findById(Integer id) ;    //tìm kiếm theo id

	public Order create(Order order) ;     //thêm danh sách

	public Order update(Order order) ;   //sửa danh sách

	public void delete(Order order) ;
	
	Page<Order> search(String Fullname, Pageable pageable);
	 
	
	   List<Order> findByYear(int year);
	   Order findMaxQuantityAndTotalByYear(int year);  
}
