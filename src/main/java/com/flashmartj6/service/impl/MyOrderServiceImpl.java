package com.flashmartj6.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.flashmartj6.entity.Order;
import com.flashmartj6.responsitory.OrderDAO;
import com.flashmartj6.services.MyOrderService;

@Service
public class MyOrderServiceImpl implements MyOrderService{

	@Autowired
	OrderDAO orderDAO;
	
	@Override
	public List<Order> findAll(String accountid) {
		return orderDAO.findByAll(accountid);
	}

}
