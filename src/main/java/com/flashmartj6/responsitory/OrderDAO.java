package com.flashmartj6.responsitory;

import com.flashmartj6.entity.Order;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

public interface OrderDAO extends JpaRepository<Order, Integer> {
	@Query("SELECT o FROM Order o WHERE o.account.Fullname=?1")
	List<Order> findByUsername(String username);

	@Query("SELECT o FROM Order o JOIN FETCH o.orderDetails od WHERE o.account.Username = ?1")
	List<Order> findByAll(String accountid);
	
	
}
