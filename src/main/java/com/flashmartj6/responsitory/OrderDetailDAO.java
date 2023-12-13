package com.flashmartj6.responsitory;

import com.flashmartj6.entity.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderDetailDAO extends JpaRepository<OrderDetail,Integer> {
    @Query("select d from OrderDetail d where d.order.id = :OrderId")
    List<OrderDetail> findByOrderById(@Param("OrderId") Integer id);
    
}
