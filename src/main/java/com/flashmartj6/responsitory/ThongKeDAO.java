package com.flashmartj6.responsitory;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.flashmartj6.entity.Order;

@Repository
public interface ThongKeDAO extends JpaRepository<Order, Integer> {
  
    @Query("SELECT o FROM Order o WHERE YEAR(o.orderDate) = :year")
    List<Order> findByYear(@Param("year") int year);

    @Query("SELECT SUM(od.Total) FROM Order o JOIN o.orderDetails od WHERE YEAR(o.orderDate) = :year")
    Float getTotalRevenueByYear(@Param("year") int year);
    
    
}
