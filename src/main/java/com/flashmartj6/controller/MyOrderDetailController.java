package com.flashmartj6.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.flashmartj6.entity.Order;
import com.flashmartj6.responsitory.OrderDAO;
import com.flashmartj6.services.OrderService;

@Controller
public class MyOrderDetailController {
	
	@Autowired
	OrderService orderService;
	
	@RequestMapping("/order/detail2s/{id}")
    public String OrderDetail(@PathVariable("id") Integer id, Model model){
        model.addAttribute("order", orderService.findById(id));
        return "/order/detail";
    }
}
