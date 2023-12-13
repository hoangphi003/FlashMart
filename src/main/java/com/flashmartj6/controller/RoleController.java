package com.flashmartj6.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.flashmartj6.entity.Role;

import com.flashmartj6.service.*;
import com.flashmartj6.services.RoleService;

import javax.validation.Valid;

@Controller
@RequestMapping("/admin")
public class RoleController {

	@Autowired
	RoleService roleService;

	@GetMapping("/role")
	public String showAccount(Role role, Model model, @RequestParam("p") Optional<Integer> p) {
		Pageable pageable = PageRequest.of(p.orElse(0), 5);
		Page<Role> pages = roleService.findAll(pageable);
		model.addAttribute("pages", pages);
		model.addAttribute("currentPage", p.orElse(0));
		model.addAttribute("role", role);
		return "/views/admin/role";
	}

	@GetMapping("/editRole/{id}")
	public String showUpdateForm(@PathVariable("id") String id, Model model) {

		Role role = roleService.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid account Id:" + id));

		Page<Role> pages = roleService.findAll(PageRequest.of(0, 5));

		model.addAttribute("role", role);
		model.addAttribute("pages", pages);
		model.addAttribute("currentPage", 0);

		return "/views/admin/role";
	}

	// chuc nang insert
	@PostMapping("/saverole")
	public String createrole(@ModelAttribute @Valid Role role, BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			model.addAttribute("role", role);
			Page<Role> pages = roleService.findAll(PageRequest.of(0, 5));
			model.addAttribute("pages", pages);
			model.addAttribute("currentPage", 0);
			return "/views/admin/role";
		}
		// Xử lý logic khi không có lỗi
		roleService.create(role);

		return "redirect:/admin/role";
	}

	@GetMapping("/deleteRole/{id}")
	public String deleterole(@PathVariable("id") String id, Model model) {
		Role role = roleService.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid category Id:" + id));
		roleService.deleteAuthority(id);
		roleService.delete(role);
		return "redirect:/admin/role";
	}

	// search
	@GetMapping("/searchRL")
	public String searchrole(@RequestParam(value = "keyword", required = false) String keyword,
			@RequestParam("p") Optional<Integer> p, Model model) {
		Pageable pageable = PageRequest.of(p.orElse(0), 5);
		Page<Role> pages;

		if (keyword != null && !keyword.isEmpty()) {
			pages = roleService.search(keyword, pageable);
		} else {
			pages = roleService.findAll(pageable);
		}

		model.addAttribute("pages", pages);
		model.addAttribute("currentPage", p.orElse(0));
		model.addAttribute("role", new Role());

		return "/views/admin/role";
	}
}
