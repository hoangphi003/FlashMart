package com.flashmartj6.responsitory;

import org.springframework.data.jpa.repository.JpaRepository;

import com.flashmartj6.entity.Order;

public interface DetailDAO extends JpaRepository<Order, Integer>{


}
