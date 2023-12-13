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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.flashmartj6.entity.Category;
import com.flashmartj6.services.CategoryService;
import javax.validation.Valid;

@Controller
@RequestMapping("/admin")
public class CategoryController {

	@Autowired
	CategoryService categoryService;

	// field du lieu len table
	@GetMapping("/category")
	public String showCategoryList(Model model, @RequestParam("p") Optional<Integer> p) {
		Pageable pageable = PageRequest.of(p.orElse(0), 5);
		Page<Category> pages = categoryService.findAll(pageable);
		model.addAttribute("pages", pages);
		model.addAttribute("currentPage", p.orElse(0));
		model.addAttribute("category", new Category()); // Thêm một category mới cho form
		return "/views/admin/category";
	}

	// chuc nang insert
//	@PostMapping("/savecategory")
//	public String createCategory(@ModelAttribute @Valid Category category, BindingResult bindingResult, Model model) {
//		if (bindingResult.hasErrors()) {
//			// Thêm category vào model để hiển thị lại dữ liệu đã nhập
//			model.addAttribute("category", category);
//			// Thêm danh sách danh mục (pages) vào model để giữ nguyên dữ liệu dưới bảng
//			Page<Category> pages = categoryService.findAll(PageRequest.of(0, 5));
//			model.addAttribute("pages", pages);
//			model.addAttribute("currentPage", 0);
//			return "/views/admin/category";
//		}
//		   // Xử lý logic khi không có lỗi
//		   categoryService.create(category);
//
//		return "redirect:/admin/category";
//	}
	
	// insert CategoryController
	@PostMapping("/savecategory")
	public String createCategory(@ModelAttribute @Valid Category category, BindingResult bindingResult,
	        Model model, RedirectAttributes redirectAttributes) {
	    // Kiểm tra trùng tên (không phân biệt chữ hoa chữ thường)
	    if (categoryService.existsByNameIgnoreCase(category.getCategoryName())) {
	        bindingResult.rejectValue("CategoryName", "error.category", "Tên danh mục đã tồn tại.");
	    }

	    if (bindingResult.hasErrors()) {
	        // Thêm category và lỗi vào model để hiển thị lại dữ liệu đã nhập và thông báo lỗi
	        model.addAttribute("category", category);
	        model.addAttribute("bindingResult", bindingResult);

	        // Thêm danh sách danh mục (pages) vào model để giữ nguyên dữ liệu dưới bảng
	        Page<Category> pages = categoryService.findAll(PageRequest.of(0, 5));
	        model.addAttribute("pages", pages);
	        model.addAttribute("currentPage", 0);
	        return "/views/admin/category";
	    }

	    // Xử lý logic khi không có lỗi
	    categoryService.create(category);

	    // Thêm thông báo thành công vào redirectAttributes
	    redirectAttributes.addFlashAttribute("successMessage", "Danh mục đã được thêm thành công.");

	    return "redirect:/admin/category";
	}
	
	// update end batloi validate
	// insert CategoryController
		@PostMapping("/updatecategory")
		public String updateCategory(@ModelAttribute @Valid Category category, BindingResult bindingResult,
		        Model model, RedirectAttributes redirectAttributes) {
		    // Kiểm tra trùng tên (không phân biệt chữ hoa chữ thường)
		    if (categoryService.existsByNameIgnoreCase(category.getCategoryName())) {
		        bindingResult.rejectValue("CategoryName", "error.category", "Tên danh mục đã tồn tại.");
		    }

		    if (bindingResult.hasErrors()) {
		        // Thêm category và lỗi vào model để hiển thị lại dữ liệu đã nhập và thông báo lỗi
		        model.addAttribute("category", category);
		        model.addAttribute("bindingResult", bindingResult);

		        // Thêm danh sách danh mục (pages) vào model để giữ nguyên dữ liệu dưới bảng
		        Page<Category> pages = categoryService.findAll(PageRequest.of(0, 5));
		        model.addAttribute("pages", pages);
		        model.addAttribute("currentPage", 0);
		        return "/views/admin/category";
		    }

		    // Xử lý logic khi không có lỗi
		    categoryService.create(category);

		    // Thêm thông báo thành công vào redirectAttributes
		    redirectAttributes.addFlashAttribute("successMessage", "Cập nhật thành công.");

		    return "redirect:/admin/category";
		}


	@GetMapping("/edit/{id}")
	public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
		// Lấy category cần sửa
		Category category = categoryService.findById(id)
		.orElseThrow(() -> new IllegalArgumentException("Invalid category Id:" + id));
		// Lấy danh sách categories để giữ nguyên dữ liệu dưới bảng
		Page<Category> pages = categoryService.findAll(PageRequest.of(0, 5));
		// Thêm category và danh sách categories vào model
		model.addAttribute("category", category);
		model.addAttribute("pages", pages);
		model.addAttribute("currentPage", 0);

		return "/views/admin/category";
	}

	// chuc nang delete
//	@GetMapping("/delete/{id}")
//	public String deleteCategory(@PathVariable("id") Integer id, Model model) {
//		Category category = categoryService.findById(id)
//				.orElseThrow(() -> new IllegalArgumentException("Invalid category Id:" + id));
//		categoryService.delete(category);
//		
//		
//		
//		return "redirect:/admin/category";
//	}
	
	@GetMapping("/delete/{id}")
	public String deleteCategory(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
	    try {
	        Category category = categoryService.findById(id)
	                .orElseThrow(() -> new IllegalArgumentException("Invalid category Id:" + id));
	        categoryService.delete(category);

	        // Đặt thông báo thành công vào redirectAttributes
	        redirectAttributes.addFlashAttribute("successMessage", "Xóa thành công!");
	    } catch (Exception e) {
	        // Đặt thông báo lỗi vào redirectAttributes nếu có lỗi
	        redirectAttributes.addFlashAttribute("errorMessage", "Error deleting category: " + e.getMessage());
	    }

	    // Chuyển hướng người dùng đến trang hiển thị danh sách loại sản phẩm hoặc trang chính của trang quản trị
	    return "redirect:/admin/category";
	}


	// search
	@GetMapping("/search")
	public String searchCategories(@RequestParam(value = "keyword", required = false) String keyword,
			@RequestParam("p") Optional<Integer> p, Model model) {
		Pageable pageable = PageRequest.of(p.orElse(0), 5);
		Page<Category> pages;
		
		if (keyword != null && !keyword.isEmpty()) {
			pages = categoryService.search(keyword, pageable);
		} else {
			pages = categoryService.findAll(pageable);
		}
		
		model.addAttribute("pages", pages);
		model.addAttribute("currentPage", p.orElse(0));
		model.addAttribute("category", new Category());

		return "/views/admin/category";
	}

}