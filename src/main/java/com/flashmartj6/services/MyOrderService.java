package com.flashmartj6.services;

import java.util.List;

import com.flashmartj6.entity.Order;

public interface MyOrderService {

	List<Order> findAll(String accountid);
}
