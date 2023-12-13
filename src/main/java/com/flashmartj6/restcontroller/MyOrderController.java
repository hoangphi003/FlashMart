package com.flashmartj6.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.flashmartj6.entity.Order;
import com.flashmartj6.services.MyOrderService;

@CrossOrigin("*")
@RestController
public class MyOrderController {

	@Autowired
	MyOrderService myOrderService;
	
	@GetMapping("/rest/myorder/{accountid}")
	public List<Order> findAllOrdered(@PathVariable("accountid") String accountid){
		return myOrderService.findAll(accountid);
	}

}
