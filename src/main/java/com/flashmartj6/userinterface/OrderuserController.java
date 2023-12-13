package com.flashmartj6.userinterface;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.flashmartj6.responsitory.OrderDAO;
import com.flashmartj6.services.OrderService;

import antlr.Token;
import io.jsonwebtoken.io.IOException;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

@Controller
public class OrderuserController {

	@Autowired
	OrderDAO orderDAO;
	@Autowired
	OrderService orderService;

	@Autowired
	HttpServletRequest request;

	@RequestMapping("/cart/view")
	public String cart() {
		return "cart/view";
	}

	@RequestMapping("/order/checkout")
	    public String checkout(Model model){
	        return "order/checkout";
	    }

	@RequestMapping("/order/list")
	public String list(Model model, Long id, HttpServletRequest request) {
		String username = request.getRemoteUser();
		model.addAttribute("orders", orderService.findByUsername(username));
		return "order/list";
	}

	@RequestMapping("/order/detail/{id}")
	public String detail(@PathVariable("id") Integer id, Model model) {
		model.addAttribute("order", orderService.findById(id));
		return "order/detail";
	}
	
	@RequestMapping("/order/purchase")
	public String purchase(Model model) {
		return "order/purchase";
	}
}
