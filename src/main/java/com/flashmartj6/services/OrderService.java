package com.flashmartj6.services;

import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.flashmartj6.entity.Order;

public interface OrderService {
	Order create(JsonNode orderData);

	Order findById(Integer id);

	 List<Order> findByUsername(String username);

	List<Order> findAll();

	Order updateByStatus(Order order);

}  
