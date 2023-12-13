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

import com.flashmartj6.entity.ProductDetail;
import com.flashmartj6.entity.Size;
import com.flashmartj6.services.ProductDetailService;
import com.flashmartj6.services.SizeService;

import javax.validation.Valid;

@Controller
@RequestMapping("/admin")
public class SizeController {
	@Autowired
	SizeService sizeService;
			
	// field du lieu len table
			@GetMapping("/size")
			public String showSizeList(Model model, @RequestParam("p") Optional<Integer> p) {
				Pageable pageable = PageRequest.of(p.orElse(0), 5);
				Page<Size> pages = sizeService.findAll(pageable);
				model.addAttribute("pages", pages);
				model.addAttribute("currentPage", p.orElse(0));
				model.addAttribute("size", new Size()); // Thêm một category mới cho form
				return "/views/admin/size";
			}
//	
			// chuc nang insert
//			@PostMapping("/savesize")
//			public String createSize(@ModelAttribute @Valid Size size, BindingResult bindingResult, Model model) {
//				if (bindingResult.hasErrors()) {
//					// Thêm category vào model để hiển thị lại dữ liệu đã nhập
//					model.addAttribute("size", size);
//					// Thêm danh sách danh mục (pages) vào model để giữ nguyên dữ liệu dưới bảng
//					Page<Size> pages = sizeService.findAll(PageRequest.of(0, 5));
//					model.addAttribute("pages", pages);
//					model.addAttribute("currentPage", 0);
//					return "/views/admin/size";
//				}
//				   // Xử lý logic khi không có lỗi
//				sizeService.create(size);
//				return "redirect:/admin/size";
//			}
			
			// insert và validate form và hiện thông báo khi thêm thành công 
			 // Chức năng thêm size
		    @PostMapping("/savesize")
		    public String createSize(@ModelAttribute @Valid Size size, BindingResult bindingResult,
		                              Model model, RedirectAttributes redirectAttributes) {
		        // Kiểm tra trùng tên (không phân biệt chữ hoa chữ thường)
		        if (sizeService.existsByNameIgnoreCase(size.getSizeName())) {
		            bindingResult.rejectValue("sizeName", "error.size", "Tên size đã tồn tại.");
		        }

		        if (bindingResult.hasErrors()) {
		            // Thêm size và lỗi vào model để hiển thị lại dữ liệu đã nhập và thông báo lỗi
		            model.addAttribute("size", size);
		            model.addAttribute("bindingResult", bindingResult);

		            // Thêm danh sách size (pages) vào model để giữ nguyên dữ liệu dưới bảng
		            Page<Size> pages = sizeService.findAll(PageRequest.of(0, 5));
		            model.addAttribute("pages", pages);
		            model.addAttribute("currentPage", 0);

		            return "/views/admin/size";
		        }

		        // Xử lý logic khi không có lỗi
		        sizeService.create(size);

		        // Thêm thông báo thành công vào redirectAttributes
		        redirectAttributes.addFlashAttribute("successMessage", "Size đã được thêm thành công.");

		        return "redirect:/admin/size";
		    }
		    
		    // update và hiển thị thông báo lỗi 
		    @PostMapping("/updatesize")
		    public String updateSize(@ModelAttribute @Valid Size size, BindingResult bindingResult,
		                              Model model, RedirectAttributes redirectAttributes) {
		        // Kiểm tra trùng tên (không phân biệt chữ hoa chữ thường)
		        if (sizeService.existsByNameIgnoreCase(size.getSizeName())) {
		            bindingResult.rejectValue("sizeName", "error.size", "Tên size đã tồn tại.");
		        }

		        if (bindingResult.hasErrors()) {
		            // Thêm size và lỗi vào model để hiển thị lại dữ liệu đã nhập và thông báo lỗi
		            model.addAttribute("size", size);
		            model.addAttribute("bindingResult", bindingResult);

		            // Thêm danh sách size (pages) vào model để giữ nguyên dữ liệu dưới bảng
		            Page<Size> pages = sizeService.findAll(PageRequest.of(0, 5));
		            model.addAttribute("pages", pages);
		            model.addAttribute("currentPage", 0);

		            return "/views/admin/size";
		        }

		        // Xử lý logic khi không có lỗi
		        sizeService.create(size);

		        // Thêm thông báo thành công vào redirectAttributes
		        redirectAttributes.addFlashAttribute("successMessage", "cập nhật size thành công.");

		        return "redirect:/admin/size";
		    }
			
			
			@GetMapping("/editsize/{id}")
			public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
				// Lấy category cần sửa
				Size size = sizeService.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid color Id:" + id));
				// Lấy danh sách categories để giữ nguyên dữ liệu dưới bảng
				Page<Size> pages = sizeService.findAll(PageRequest.of(0, 5));
				// Thêm category và danh sách categories vào model
				model.addAttribute("size", size);
				model.addAttribute("pages", pages);
				model.addAttribute("currentPage", 0);

				return "/views/admin/size";
			}
			
			// chuc nang delete
//			@GetMapping("/deleteSize/{id}")
//			public String deleteColor(@PathVariable("id") Integer id, Model model) {
//				Size size = sizeService.findById(id)
//						.orElseThrow(() -> new IllegalArgumentException("Invalid size Id:" + id));
//				sizeService.delete(size);
//				return "redirect:/admin/size";
//			}
			
			@GetMapping("/deleteSize/{id}")
			public String deleteSize(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
			    try {
			        Size size = sizeService.findById(id)
			                .orElseThrow(() -> new IllegalArgumentException("Invalid size Id:" + id));
			        sizeService.delete(size);
			        sizeService.deleteReferencingRecords(id);

			        // Đặt thông báo thành công vào redirectAttributes
			        redirectAttributes.addFlashAttribute("successMessage", "xóa thành công!");
			    } catch (Exception e) {
			        // Đặt thông báo lỗi vào redirectAttributes nếu có lỗi
			        redirectAttributes.addFlashAttribute("errorMessage", "Error deleting size: " + e.getMessage());
			    }

			    // Chuyển hướng người dùng đến trang hiển thị danh sách kích thước hoặc trang chính của trang quản trị
			    return "redirect:/admin/size";
			}

			
			// search
			@GetMapping("/searchSize")
			public String searchCategories(@RequestParam(value = "keyword", required = false) String keyword,
					@RequestParam("p") Optional<Integer> p, Model model) {
				Pageable pageable = PageRequest.of(p.orElse(0), 5);
				Page<Size> pages;
				
				if (keyword != null && !keyword.isEmpty()) {
					pages = sizeService.search(keyword, pageable);
				} else {
					pages = sizeService.findAll(pageable);
				}
				
				model.addAttribute("pages", pages);
				model.addAttribute("currentPage", p.orElse(0));
				model.addAttribute("size", new Size());

				return "/views/admin/size";
			}
}
