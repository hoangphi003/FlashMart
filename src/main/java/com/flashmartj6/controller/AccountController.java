package com.flashmartj6.controller;

import java.util.HashMap;
import java.util.Map;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.flashmartj6.entity.Account;
import com.flashmartj6.entity.Category;
import com.flashmartj6.services.AccountService;

import javax.validation.Valid;

@Controller
@RequestMapping("/admin")
public class AccountController {
	@Autowired
	AccountService accountService;

	@GetMapping("/account")
	public String showAccount(Account account, Model model, @RequestParam("p") Optional<Integer> p) {
		Pageable pageable = PageRequest.of(p.orElse(0), 5);
		Page<Account> pages = accountService.findAll(pageable);
		model.addAttribute("pages", pages);
		model.addAttribute("currentPage", p.orElse(0));
		model.addAttribute("account", account);
		return "/views/admin/account";
	}

	@GetMapping("/editaccount/{id}")
	public String showUpdateForm(@PathVariable("id") String id, Model model) {
		// Lấy category cần sửa
		Account account = accountService.findById(id);
		// Lấy danh sách categories để giữ nguyên dữ liệu dưới bảng
		Page<Account> pages = accountService.findAll(PageRequest.of(0, 5));
		// Thêm category và danh sách categories vào model
		model.addAttribute("account", account);
		model.addAttribute("pages", pages);
		model.addAttribute("currentPage", 0);

		return "/views/admin/account";
	}

	@ModelAttribute("genders")
	public Map<Boolean, String> getGenders() {
		Map<Boolean, String> map = new HashMap<>();
		map.put(true, "Male");
		map.put(false, "Female");
		return map;
	}

	@ModelAttribute("roles")
	public Map<Boolean, String> getRole() {
		Map<Boolean, String> map = new HashMap<>();
		map.put(true, "Hoạt động");
		map.put(false, "Không hoạt động");
		return map;
	}

	// phương thức thêm account có bắt validate lỗi trùng email và sđt
	@PostMapping("/saveaccount")
	public String createAccount(@ModelAttribute @Valid Account account, BindingResult bindingResult, Model model,
			RedirectAttributes redirectAttributes) {
		// Kiểm tra trường bắt buộc
		if (account.getFullname() == null || account.getFullname().isBlank()) {
			bindingResult.rejectValue("fullname", "error.account", "Vui lòng nhập họ tên.");
		}

		if (account.getEmail() == null || account.getEmail().isBlank()) {
			bindingResult.rejectValue("email", "error.account", "Vui lòng nhập địa chỉ email.");
		}

		if (account.getPassword() == null || account.getPassword().isBlank()) {
			bindingResult.rejectValue("password", "error.account", "Vui lòng nhập mật khẩu.");
		}

		if (account.getPhone() == null || account.getPhone().toString().isBlank()) {
			bindingResult.rejectValue("phone", "error.account", "Vui lòng nhập số điện thoại.");
		}

		// Kiểm tra lỗi cho trường radio "gender"
		if (account.getGender() == null) {
			// Add an error message to the model with the key "genderError"
			model.addAttribute("genderError", "Vui lòng chọn giới tính.");
		}

		// Check for null value in the "active" field
		if (account.getActive() == null) {
			// Add an error message to the model with the key "activeError"
			model.addAttribute("activeError", "Vui lòng chọn trạng thái hoạt động.");
		}

		// Kiểm tra trùng Email và Phone...
		if (checkDuplicate(account, bindingResult, model)) {
			// Xử lý logic khi không có lỗi
			accountService.create(account);

			// Thêm thông báo thành công vào redirectAttributes
			redirectAttributes.addFlashAttribute("successMessage", "Account đã được thêm thành công.");

			return "redirect:/admin/account";
		} else {
			// Thêm account và lỗi vào model để hiển thị lại dữ liệu đã nhập và thông báo
			// lỗi
			model.addAttribute("account", account);

			// Thêm danh sách accounts (pages) vào model để giữ nguyên dữ liệu dưới bảng
			Page<Account> pages = accountService.findAll(PageRequest.of(0, 5));
			model.addAttribute("pages", pages);
			model.addAttribute("currentPage", 0);

			return "/views/admin/account";
		}
	}

	// update
	@PostMapping("/updateaccount")
	public String updateAccount(@ModelAttribute @Valid Account account, BindingResult bindingResult, Model model,
			RedirectAttributes redirectAttributes) {
		// Kiểm tra trường bắt buộc
		if (account.getFullname() == null || account.getFullname().isBlank()) {
			bindingResult.rejectValue("fullname", "error.account", "Vui lòng nhập họ tên.");
		}

		if (account.getEmail() == null || account.getEmail().isBlank()) {
			bindingResult.rejectValue("email", "error.account", "Vui lòng nhập địa chỉ email.");
		}

		if (account.getPassword() == null || account.getPassword().isBlank()) {
			bindingResult.rejectValue("password", "error.account", "Vui lòng nhập mật khẩu.");
		}

		if (account.getPhone() == null || account.getPhone().toString().isBlank()) {
			bindingResult.rejectValue("phone", "error.account", "Vui lòng nhập số điện thoại.");
		}

		// Kiểm tra lỗi cho trường radio "gender"
		if (account.getGender() == null) {
			// Add an error message to the model with the key "genderError"
			model.addAttribute("genderError", "Vui lòng chọn giới tính.");
		}

		// Check for null value in the "active" field
		if (account.getActive() == null) {
			// Add an error message to the model with the key "activeError"
			model.addAttribute("activeError", "Vui lòng chọn trạng thái hoạt động.");
		}

		// Kiểm tra trùng Email và Phone...
		if (checkDuplicate(account, bindingResult, model)) {
			// Xử lý logic khi không có lỗi
			accountService.create(account);

			// Thêm thông báo thành công vào redirectAttributes
			redirectAttributes.addFlashAttribute("successMessage", "cập nhật thành công.");

			return "redirect:/admin/account";
		} else {
			// Thêm account và lỗi vào model để hiển thị lại dữ liệu đã nhập và thông báo
			// lỗi
			model.addAttribute("account", account);

			// Thêm danh sách accounts (pages) vào model để giữ nguyên dữ liệu dưới bảng
			Page<Account> pages = accountService.findAll(PageRequest.of(0, 5));
			model.addAttribute("pages", pages);
			model.addAttribute("currentPage", 0);

			return "/views/admin/account";
		}
	}

	// Phương thức kiểm tra trùng lặp Email và Phone
	private boolean checkDuplicate(Account account, BindingResult bindingResult, Model model) {
		// Kiểm tra trùng lặp email
		Account existingAccountByEmail = accountService.findByEmail(account.getEmail());
		if (existingAccountByEmail != null && !existingAccountByEmail.getUsername().equals(account.getUsername())) {
			bindingResult.rejectValue("email", "error.account", "Email đã tồn tại!");
		}

		// Kiểm tra trùng lặp số điện thoại
		Account existingAccountByPhone = accountService.findByPhone(account.getPhone());
		if (existingAccountByPhone != null && !existingAccountByPhone.getUsername().equals(account.getUsername())) {
			bindingResult.rejectValue("phone", "error.account", "Số điện thoại đã tồn tại!");
		}

		return !bindingResult.hasErrors();
	}

	@GetMapping("/deleteaccount/{id}")
	public String deleteAccount(@PathVariable("id") String id, RedirectAttributes redirectAttributes) {
		try {
			Account account = accountService.findById(id);
			accountService.deleteReferenceOrders(id);
			accountService.deleteReferencingRecords(id);
			accountService.delete(account);

			// Đặt thông báo thành công vào redirectAttributes
			redirectAttributes.addFlashAttribute("successMessage", "Xóa thành công!");
		} catch (Exception e) {
			// Đặt thông báo lỗi vào redirectAttributes nếu có lỗi
			redirectAttributes.addFlashAttribute("errorMessage", "Xóa thất bại: " + e.getMessage());
		}

		// Chuyển hướng người dùng đến trang hiển thị danh sách tài khoản hoặc trang
		// chính của trang quản trị
		return "redirect:/admin/account";
	}

	// search
	@GetMapping("/searchAccount")
	public String searchAccount(@RequestParam(value = "keyword", required = false) String keyword,
			@RequestParam("p") Optional<Integer> p, Model model) {
		Pageable pageable = PageRequest.of(p.orElse(0), 5);
		Page<Account> pages;

		if (keyword != null && !keyword.isEmpty()) {
			pages = accountService.search(keyword, pageable);
		} else {
			pages = accountService.findAll(pageable);
		}

		model.addAttribute("pages", pages);
		model.addAttribute("currentPage", p.orElse(0));
		model.addAttribute("account", new Account());

		return "/views/admin/account";
	}

}
