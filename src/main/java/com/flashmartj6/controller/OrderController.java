package com.flashmartj6.controller;

import com.flashmartj6.entity.Order;
import com.flashmartj6.responsitory.OrderDAO;
import com.flashmartj6.responsitory.OrderDetailDAO;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/admin")
public class OrderController {
	@Autowired
	OrderDAO orderDAO;

	@Autowired
	OrderDetailDAO orderDetailDAO;

	@GetMapping("/order")
	public String index(Model model) {
		model.addAttribute("list", orderDAO.findAll());
		return "/views/admin/order";
	}

	@GetMapping("/orderSee/{id}")
	public String OrderSee(@PathVariable("id") Integer id, Model model) {
		// OrderDetail orderDetail = new OrderDetail();
		model.addAttribute("list", orderDAO.findAll());

//		if (order.getId().equals(orderDetail.getId())){
//			System.out.println("order.getId()");
//		}
		model.addAttribute("listdetail", orderDAO.getOne(id));
		model.addAttribute("listorderdetail", orderDetailDAO.findByOrderById(id));

		return "/views/admin/order";
	}

	@RequestMapping("/statussuccess/{id}")
	public String UpdateStatus(@PathVariable("id") Integer id, Model model) {
	    Optional<Order> optionalOrder = orderDAO.findById(id);
	    if (optionalOrder.isPresent()) {
	        Order order = optionalOrder.get();
	        order.setStatus("Đã xác nhận");
	        orderDAO.save(order); // Lưu thay đổi vào cơ sở dữ liệu
	    }
	    return "redirect:/admin/order";
	}

	@RequestMapping("/statuscancel/{id}")
	public String CancelStatus(@PathVariable("id") Integer id, Model model) {
	    Optional<Order> optionalOrder = orderDAO.findById(id);
	    if (optionalOrder.isPresent()) {
	        Order order = optionalOrder.get();
	        order.setStatus("Đã hủy");
	        orderDAO.save(order); // Lưu thay đổi vào cơ sở dữ liệu
	    }
	    return "redirect:/admin/order";
	}
	
	@RequestMapping("/statusing/{id}")
	public String StatuSing(@PathVariable("id") Integer id, Model model) {
	    Optional<Order> optionalOrder = orderDAO.findById(id);
	    if (optionalOrder.isPresent()) {
	        Order order = optionalOrder.get();
	        order.setStatus("Đang giao");
	        orderDAO.save(order); // Lưu thay đổi vào cơ sở dữ liệu
	    }
	    return "redirect:/admin/order";
	}
	
	@RequestMapping("/statused/{id}")
	public String Statused(@PathVariable("id") Integer id, Model model) {
	    Optional<Order> optionalOrder = orderDAO.findById(id);
	    if (optionalOrder.isPresent()) {
	        Order order = optionalOrder.get();
	        order.setStatus("Đã giao");
	        orderDAO.save(order); // Lưu thay đổi vào cơ sở dữ liệu
	    }
	    return "redirect:/admin/order";
	}

}