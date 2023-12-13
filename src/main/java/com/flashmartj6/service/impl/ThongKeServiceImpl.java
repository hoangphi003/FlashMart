package com.flashmartj6.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.flashmartj6.entity.Order;
import com.flashmartj6.responsitory.*;
import com.flashmartj6.services.*;

@Service
public class ThongKeServiceImpl implements ThongkeService {

	@Autowired
	ThongKeDAO thongkeResponsitory;

	@Override
	public List<Order> findAll() {
		// TODO Auto-generated method stub
		return thongkeResponsitory.findAll();
	}

	@Override
	public Page<Order> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return thongkeResponsitory.findAll(pageable);
	}

	@Override
	public Optional<Order> findById(Integer id) {
		// TODO Auto-generated method stub
		return thongkeResponsitory.findById(id);
	}

	@Override
	public Order create(Order order) {
		// TODO Auto-generated method stub
		return thongkeResponsitory.save(order);
	}

	@Override
	public Order update(Order order) {
		// TODO Auto-generated method stub
		return thongkeResponsitory.save(order);
	}

	@Override
	public void delete(Order order) {
		// TODO Auto-generated method stub
		thongkeResponsitory.delete(order);
	}

	@Override
	public Page<Order> search(String Fullname, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Order> findByYear(int year) {
		return thongkeResponsitory.findByYear(year);
	}

	@Override
	public Order findMaxQuantityAndTotalByYear(int year) {
		// TODO Auto-generated method stub
		return null;
	}

}
