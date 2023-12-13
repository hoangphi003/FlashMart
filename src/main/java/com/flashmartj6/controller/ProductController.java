package com.flashmartj6.controller;

import com.flashmartj6.entity.Color;
import com.flashmartj6.entity.Product;
import com.flashmartj6.entity.ProductDetail;
import com.flashmartj6.entity.Size;
import com.flashmartj6.responsitory.CategoryDAO;
import com.flashmartj6.services.ColorService;
import com.flashmartj6.services.ProductDetailService;
import com.flashmartj6.services.ProductService;
import com.flashmartj6.services.SizeService;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.websocket.server.PathParam;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@MultipartConfig
@RequestMapping("/admin")
public class ProductController {

	@Autowired
	ProductService productService;

	@Autowired
	SizeService sizeService;

	@Autowired
	ColorService colorService;

	@Autowired
	ProductDetailService productDetailService;

	@Autowired
	CategoryDAO categoryDAO;

	@Autowired
	HttpServletRequest request;

	private static String UPLOAD_DIRECTORY = System.getProperty("user.dir")
			+ "/src/main/resources/static/assets/images/products/";

	@GetMapping("/product")
	public String index(Product product, Model model, @PathParam("p") Optional<Integer> p) {
		Pageable pageable = PageRequest.of(p.orElse(0), 5);
		Page<Product> page = productService.findAllByPage(pageable);
		model.addAttribute("list", page);
		model.addAttribute("currentPage", p.orElse(0));
		model.addAttribute("categoryList", categoryDAO.findAll());
		model.addAttribute("colors", colorService.findAll());
		model.addAttribute("sizes", sizeService.findAll());
		model.addAttribute("product", product);
		return "/views/admin/product";
	}

	@PostMapping("/saveproduct")
	public String SaveProduct(@Valid @ModelAttribute Product product, BindingResult result,
			@RequestParam("selectedSize") Integer sizeId, @RequestParam("selectedColor") Integer colorId,
			@RequestParam("image") MultipartFile image, @PathParam("p") Optional<Integer> p, Model model) {

		Pageable pageable = PageRequest.of(p.orElse(0), 5);
		Page<Product> page = productService.findAllByPage(pageable);
		Optional<Size> sizeOptional = sizeService.findById(sizeId);
		Optional<Color> colorOptional = colorService.findById(colorId);

		if (image != null && !image.isEmpty()) {
			try {
				String fileName = image.getOriginalFilename();
				Path fileNameAndPath = Paths.get(UPLOAD_DIRECTORY, fileName);
				Files.write(fileNameAndPath, image.getBytes());
				product.setImage(fileName);
			} catch (IOException e) {
				e.printStackTrace();
			}
			productService.save(product);

		} else {
//			bắt lỗi cho hình
			if (result.hasErrors()) {
				model.addAttribute("product", product); // Create a new product for the form
				model.addAttribute("list", page);
				model.addAttribute("categoryList", categoryDAO.findAll());
				model.addAttribute("colors", colorService.findAll());
				model.addAttribute("sizes", sizeService.findAll());
			}
			model.addAttribute("photo_message", "Không bỏ trống hình");
			return "/views/admin/product";
		}

		if (sizeOptional.isPresent() && colorOptional.isPresent()) {
			Size size = sizeOptional.get();
			Color color = colorOptional.get();

			// Create ProductDetail and associate it with the saved Product, Size, and Color
			ProductDetail detail = new ProductDetail();
			detail.setProduct(product);
			detail.setSize(size);
			detail.setColor(color);

			// Save the ProductDetail
			productDetailService.save(detail);
		}

		productService.save(product);

		model.addAttribute("list", productService.findAll());
		model.addAttribute("categoryList", categoryDAO.findAll());
		model.addAttribute("product", new Product());
		model.addAttribute("colors", colorService.findAll());
		model.addAttribute("sizes", sizeService.findAll());
		return "redirect:/admin/product";
	}

	@GetMapping("/deleteproduct/{id}")
	public String DeleteProduct(@PathVariable("id") Integer id) {
		productService.deleteById(id);
		return "redirect:/admin/product";
	}

	@GetMapping("editproduct/{id}")
	public String EditProduct(@PathVariable("id") Integer id, Product product, @PathParam("p") Optional<Integer> p,
			Model model) {
		Pageable pageable = PageRequest.of(p.orElse(0), 5);
		Page<Product> page = productService.findAllByPage(pageable);

		model.addAttribute("product", productService.getOne(id));
		model.addAttribute("colors", colorService.findAll());
		model.addAttribute("sizes", sizeService.findAll());
		model.addAttribute("list", page);
		model.addAttribute("categoryList", categoryDAO.findAll());

		return "/views/admin/product";
	}

//	Tìm kiếm
	@PostMapping("/product")
	public String ProductSearch(Model model, HttpServletRequest request, @RequestParam("p") Optional<Integer> p) {
		String keywords = request.getParameter("keywords");
		Pageable pageable = PageRequest.of(p.orElse(0), 5);
		Page<Product> pages;

		if (keywords != null && !keywords.isEmpty()) {
			model.addAttribute("categoryList", categoryDAO.findAll());
			pages = productService.findProductNameLike(keywords, pageable);
		} else {
			model.addAttribute("categoryList", categoryDAO.findAll());
			pages = productService.findAllByPage(pageable);
		}

		model.addAttribute("list", pages);
		model.addAttribute("currentPage", p.orElse(0));
		model.addAttribute("product", new Product());
		model.addAttribute("colors", colorService.findAll());
		model.addAttribute("sizes", sizeService.findAll());
		return "views/admin/product";
	}

	@GetMapping("/product/sort")
	public String ProductSort(Model model, HttpServletRequest request, @PathParam("p") Optional<Integer> p) {
		Pageable pageable = PageRequest.of(p.orElse(0), 5);
		Page<Product> pages;

		pages = productService.SortByProductName(pageable);

		model.addAttribute("list", pages);
		model.addAttribute("currentPage", p.orElse(0));
		model.addAttribute("categoryList", categoryDAO.findAll());
		model.addAttribute("product", new Product());
		model.addAttribute("colors", colorService.findAll());
		model.addAttribute("sizes", sizeService.findAll());
		return "views/admin/product";
	}

	@GetMapping("/product/pagination")
	public String ProductPagination(Model model, @PathParam("p") Optional<Integer> p) {

		Pageable pageable = PageRequest.of(p.orElse(0), 5);
		Page<Product> page = productService.findAllByPage(pageable);

		model.addAttribute("list", page);
//		model.addAttribute("list", productService.findAll());
		model.addAttribute("categoryList", categoryDAO.findAll());
		model.addAttribute("product", new Product());

		return "views/admin/product";
	}

}
