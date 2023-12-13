package com.flashmartj6.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.flashmartj6.entity.Order;
import com.flashmartj6.responsitory.OrderDAO;
import com.flashmartj6.responsitory.ThongKeDAO;
import com.flashmartj6.services.ThongkeService;

@Controller
@RequestMapping("/admin")
public class HomeController {
	@Autowired
	private OrderDAO orderDAO;

	@Autowired
	ThongkeService thongkeService;

	@Autowired
	private ThongKeDAO thongkeResponsitory;

	@GetMapping({ "", "/", "/index" ,"/home" })
	public String showAccount(Order order, Model model, @RequestParam("p") Optional<Integer> p) {
		Pageable pageable = PageRequest.of(p.orElse(0), 10);
		Page<Order> pages = thongkeService.findAll(pageable);
		model.addAttribute("pages", pages);
		model.addAttribute("currentPage", p.orElse(0));
		model.addAttribute("order", order);
		return "/views/admin/index";
	}

	@GetMapping("/filterByYear")
	public ResponseEntity<List<Order>> filterByYear(@RequestParam("year") int year) {
		List<Order> filteredOrders = thongkeService.findByYear(year);
		return new ResponseEntity<>(filteredOrders, HttpStatus.OK);
	}

	@GetMapping("/maxOrderInfoByYear")
	public ResponseEntity<Order> getMaxOrderInfoByYear(@RequestParam("year") int year) {
		Order maxOrder = thongkeService.findMaxQuantityAndTotalByYear(year);
		return new ResponseEntity<>(maxOrder, HttpStatus.OK);
	}

	@GetMapping("/getTotalRevenueByYear")
	public ResponseEntity<Float> getTotalRevenueByYear(@RequestParam("year") int year) {
		Float totalRevenue = thongkeResponsitory.getTotalRevenueByYear(year);
		return new ResponseEntity<>(totalRevenue, HttpStatus.OK);
	}


}
