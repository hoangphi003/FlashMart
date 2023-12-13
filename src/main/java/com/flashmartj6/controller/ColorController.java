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
import com.flashmartj6.entity.Color;
import com.flashmartj6.entity.ProductDetail;
import com.flashmartj6.services.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/admin")
public class ColorController {
	
	@Autowired
	ColorService colorService;
	
	// field du lieu len table
		@GetMapping("/color")
		public String showColorList(Model model, @RequestParam("p") Optional<Integer> p) {
			Pageable pageable = PageRequest.of(p.orElse(0), 5);
			Page<Color> pages = colorService.findAll(pageable);
			model.addAttribute("pages", pages);
			model.addAttribute("currentPage", p.orElse(0));
			model.addAttribute("color", new Color()); // Thêm một category mới cho form
			return "/views/admin/color";
		}
		
		// chuc nang insert
//		@PostMapping("/savecolor")
//		public String createColor(@ModelAttribute @Valid Color color, BindingResult bindingResult, Model model) {
//			if (bindingResult.hasErrors()) {
//				// Thêm category vào model để hiển thị lại dữ liệu đã nhập
//				model.addAttribute("color", color);
//				// Thêm danh sách danh mục (pages) vào model để giữ nguyên dữ liệu dưới bảng
//				Page<Color> pages = colorService.findAll(PageRequest.of(0, 5));
//				model.addAttribute("pages", pages);
//				model.addAttribute("currentPage", 0);
//				return "/views/admin/color";
//			}
//			   // Xử lý logic khi không có lỗi
//			colorService.create(color);
//
//			return "redirect:/admin/color";
//		}
		
		// Chức năng thêm màu
	    @PostMapping("/savecolor")
	    public String createColor(@ModelAttribute @Valid Color color, BindingResult bindingResult,
	                              Model model, RedirectAttributes redirectAttributes) {
	        // Kiểm tra trùng tên (không phân biệt chữ hoa chữ thường)
	        if (colorService.existsByNameIgnoreCase(color.getColorName())) {
	            bindingResult.rejectValue("colorName", "error.color", "Tên màu đã tồn tại");
	        }

	        if (bindingResult.hasErrors()) {
	            // Thêm màu và lỗi vào model để hiển thị lại dữ liệu đã nhập và thông báo lỗi
	            model.addAttribute("color", color);
	            model.addAttribute("bindingResult", bindingResult);

	            // Thêm danh sách màu (pages) vào model để giữ nguyên dữ liệu dưới bảng
	            Page<Color> pages = colorService.findAll(PageRequest.of(0, 5));
	            model.addAttribute("pages", pages);
	            model.addAttribute("currentPage", 0);

	            return "/views/admin/color";
	        }

	        // Xử lý logic khi không có lỗi
	        colorService.create(color);

	        // Thêm thông báo thành công vào redirectAttributes
	        redirectAttributes.addFlashAttribute("successMessage", "Màu đã được thêm thành công.");

	        return "redirect:/admin/color";
	    }
	    
	    // update color
	    
	 // Chức năng thêm màu
	    @PostMapping("/updatecolor")
	    public String updateeColor(@ModelAttribute @Valid Color color, BindingResult bindingResult,
	                              Model model, RedirectAttributes redirectAttributes) {
	        // Kiểm tra trùng tên (không phân biệt chữ hoa chữ thường)
	        if (colorService.existsByNameIgnoreCase(color.getColorName())) {
	            bindingResult.rejectValue("colorName", "error.color", "Tên màu đã tồn tại");
	        }

	        if (bindingResult.hasErrors()) {
	            // Thêm màu và lỗi vào model để hiển thị lại dữ liệu đã nhập và thông báo lỗi
	            model.addAttribute("color", color);
	            model.addAttribute("bindingResult", bindingResult);

	            // Thêm danh sách màu (pages) vào model để giữ nguyên dữ liệu dưới bảng
	            Page<Color> pages = colorService.findAll(PageRequest.of(0, 5));
	            model.addAttribute("pages", pages);
	            model.addAttribute("currentPage", 0);

	            return "/views/admin/color";
	        }

	        // Xử lý logic khi không có lỗi
	        colorService.create(color);

	        // Thêm thông báo thành công vào redirectAttributes
	        redirectAttributes.addFlashAttribute("successMessage", "Cập nhật thành công.");

	        return "redirect:/admin/color";
	    }

		@GetMapping("/editt/{id}")
		public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
			// Lấy category cần sửa
			Color color = colorService.findById(id)
			.orElseThrow(() -> new IllegalArgumentException("Invalid color Id:" + id));
			// Lấy danh sách categories để giữ nguyên dữ liệu dưới bảng
			Page<Color> pages = colorService.findAll(PageRequest.of(0, 5));
			// Thêm category và danh sách categories vào model
			model.addAttribute("color", color);
			model.addAttribute("pages", pages);
			model.addAttribute("currentPage", 0);

			return "/views/admin/color";
		}

		// chuc nang delete
//		@GetMapping("/deleteColor/{id}")
//		public String deleteColor(@PathVariable("id") Integer id, Model model) {
//			Color color = colorService.findById(id)
//					.orElseThrow(() -> new IllegalArgumentException("Invalid color Id:" + id));
//			colorService.delete(color);
//			return "redirect:/admin/color";
//		}
		
		@GetMapping("/deleteColor/{id}")
		public String deleteColor(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
		    try {
		        Color color = colorService.findById(id)
		                .orElseThrow(() -> new IllegalArgumentException("Invalid color Id:" + id));
		        colorService.delete(color);
		        colorService.deleteReferencingRecords(id);

		        // Đặt thông báo thành công vào redirectAttributes
		        redirectAttributes.addFlashAttribute("successMessage", "Xóa thành công!");
		    } catch (Exception e) {
		        // Đặt thông báo lỗi vào redirectAttributes nếu có lỗi
		        redirectAttributes.addFlashAttribute("errorMessage", "Error deleting color: " + e.getMessage());
		    }

		    // Chuyển hướng người dùng đến trang hiển thị danh sách màu hoặc trang chính của trang quản trị
		    return "redirect:/admin/color";
		}

		
		// search
		@GetMapping("/searchColor")
		public String searchCategories(@RequestParam(value = "keyword", required = false) String keyword,
				@RequestParam("p") Optional<Integer> p, Model model) {
			Pageable pageable = PageRequest.of(p.orElse(0), 5);
			Page<Color> pages;
			
			if (keyword != null && !keyword.isEmpty()) {
				pages = colorService.search(keyword, pageable);
			} else {
				pages = colorService.findAll(pageable);
			}
			
			model.addAttribute("pages", pages);
			model.addAttribute("currentPage", p.orElse(0));
			model.addAttribute("color", new Color());

			return "/views/admin/color";
		}
}
