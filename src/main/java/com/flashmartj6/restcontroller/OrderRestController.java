package com.flashmartj6.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.flashmartj6.entity.Order;
import com.flashmartj6.responsitory.OrderDAO;
import com.flashmartj6.services.OrderService;

@CrossOrigin("*")
@RestController
@RequestMapping("/rest/orders")
public class OrderRestController {

	@Autowired
	OrderService orderService;

	@PostMapping
	public Order create(@RequestBody JsonNode orderData) {
		return orderService.create(orderData);
	}

	@Autowired
	OrderDAO dao;

	@PutMapping("{id}")
	public ResponseEntity<Order> update(@PathVariable("id") Integer id, @RequestBody Order order) {
		// Assume you have a service class to handle business logic
	    Order existingOrder = orderService.findById(id);

	    if (existingOrder != null) {
	        // Update only the 'status' property
	        existingOrder.setStatus(order.getStatus());

	        // Save the updated order
	        Order savedOrder = dao.save(existingOrder);

	        // Return the updated order with a 200 OK status
	        return ResponseEntity.ok(savedOrder);
	    } else {
	        // If the order with the given ID is not found, return a 404 Not Found status
	        return ResponseEntity.notFound().build();
	    }
	}

	@GetMapping
	public List<Order> getAll() {
		return orderService.findAll();
	}
}