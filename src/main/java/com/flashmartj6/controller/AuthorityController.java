package com.flashmartj6.controller;

import java.util.Optional;

import javax.validation.Valid;

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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.flashmartj6.entity.Account;
import com.flashmartj6.entity.Authority;
import com.flashmartj6.entity.Color;
import com.flashmartj6.entity.Role;
import com.flashmartj6.entity.Size;
import com.flashmartj6.responsitory.AccountDAO;
import com.flashmartj6.responsitory.AuthorityDAO;
import com.flashmartj6.responsitory.RoleDAO;

@Controller
@RequestMapping("/admin")
public class AuthorityController {
	
	@Autowired
	AuthorityDAO authorityDAO;
	
	@Autowired
	RoleDAO dao;
	
	@Autowired
	AccountDAO accountDAO;
	
	@GetMapping("/authority")
	public String showAuthorityList(Authority authority,Model model, @RequestParam("p") Optional<Integer> p) {
		Pageable pageable = PageRequest.of(p.orElse(0), 5);
		Page<Authority> pages = authorityDAO.findAll(pageable);
		model.addAttribute("pages", pages);
		model.addAttribute("currentPage", p.orElse(0));
		model.addAttribute("authority", new Authority()); // Thêm một category mới cho form
		model.addAttribute("role", dao.findAll());
		model.addAttribute("account", accountDAO.findAll());
		return "/views/admin/authority";
	}
	
	@PostMapping("/saveauthority")
	public String creatauthority(@ModelAttribute @Valid Authority authority, BindingResult bindingResult,
	                             Model model) {
	    if (bindingResult.hasErrors()) {
	        model.addAttribute("authority", authority);
	        // Load lại trang với dữ liệu phân trang và danh sách role, account
	        loadRoleAccountData(model, 0);
	        return "/views/admin/role";
	    }

	    // Xử lý logic khi không có lỗi
	    authorityDAO.save(authority);

	    // Chuyển hướng sau khi thêm mới thành công
	    return "redirect:/admin/authority";
	}

	// Phương thức để load dữ liệu phân trang, role, account
	private void loadRoleAccountData(Model model, int currentPage) {
	    Page<Authority> pages = authorityDAO.findAll(PageRequest.of(currentPage, 5));		
	    model.addAttribute("pages", pages);
	    model.addAttribute("currentPage", currentPage);
	    model.addAttribute("role", dao.findAll());
	    model.addAttribute("account", accountDAO.findAll());
	}
	
	
	@GetMapping("/editauthority/{id}")
	public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
		// Lấy category cần sửa
		Authority authority = authorityDAO.findById(id)
		.orElseThrow(() -> new IllegalArgumentException("Invalid color Id:" + id));
		// Lấy danh sách categories để giữ nguyên dữ liệu dưới bảng
		Page<Authority> pages = authorityDAO.findAll(PageRequest.of(0, 5));
		// Thêm category và danh sách categories vào model
		model.addAttribute("authority", authority);
		model.addAttribute("pages", pages);
		model.addAttribute("currentPage", 0);
		 model.addAttribute("role", dao.findAll());
		    model.addAttribute("account", accountDAO.findAll());

		return "/views/admin/authority";
	}
	
	
//	@GetMapping("/deleteauthority/{id}")
//	 public String deleteAccount(@PathVariable("id") Integer id,Model model, RedirectAttributes redirectAttributes) {
//	     try {
//	         Authority authority = authorityDAO.findById(id)
//	                 .orElseThrow(() -> new IllegalArgumentException("Invalid account Id:" + id));
//	         model.addAttribute("role", dao.findAll());
//			    model.addAttribute("account", accountDAO.findAll());
//	         authorityDAO.delete(authority);
//
//	         // Đặt thông báo thành công vào redirectAttributes
//	         redirectAttributes.addFlashAttribute("successMessage", "Xóa thành công!");
//	     } catch (Exception e) {
//	    	 e.printStackTrace();
//	         // Đặt thông báo lỗi vào redirectAttributes nếu có lỗi
//	         redirectAttributes.addFlashAttribute("errorMessage", "Xóa thất bại: " + e.getMessage());
//	     }
//
//	    // Chuyển hướng người dùng đến trang hiển thị danh sách màu hoặc trang chính của trang quản trị
//	    return "redirect:/admin/authority";
//	}
	
	// chuc nang delete
			@GetMapping("/deleteauthority/{id}")
			public String deleteaccount( @PathVariable("id") Integer id, Model model) {
				Authority authority = authorityDAO.findById(id)
						.orElseThrow(() -> new IllegalArgumentException("Invalid category Id:" + id));
				 model.addAttribute("role", dao.findAll());
				    model.addAttribute("account", accountDAO.findAll());
				authorityDAO.deleteById(id);
				return "redirect:/admin/authority";
			}

	
	

}
