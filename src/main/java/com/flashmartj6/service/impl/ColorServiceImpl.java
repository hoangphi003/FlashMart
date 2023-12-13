package com.flashmartj6.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.flashmartj6.entity.Color;
import com.flashmartj6.entity.ProductDetail;
import com.flashmartj6.responsitory.ColorDAO;
import com.flashmartj6.services.ColorService;

@Service
public class ColorServiceImpl implements ColorService {

	@Autowired
	ColorDAO colorDAO;

	@Override
	public List<Color> findAll() {
		// TODO Auto-generated method stub
		return colorDAO.findAll();
	}

	@Override
	public Page<Color> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return colorDAO.findAll(pageable);
	}

	@Override
	public Optional<Color> findById(Integer id) {
		// TODO Auto-generated method stub
		return colorDAO.findById(id);
	}

	@Override
	public Color create(Color color) {
		// TODO Auto-generated method stub
		return colorDAO.save(color);
	}

	@Override
	public Color update(Color color) {
		// TODO Auto-generated method stub
		return colorDAO.save(color);
	}

	@Override
	public void delete(Color color) {
		colorDAO.delete(color);
	}
	// Trong service hoặc DAO của Color

	@Transactional
	public void deleteColorAndRelatedDetails(Color color) {
		// Remove color from associated productDetails to avoid constraint violations
		for (ProductDetail productDetail : color.getProductDetails()) {
			productDetail.setColor(null);
		}
		color.getProductDetails().clear();

		// Delete the color entity
		colorDAO.delete(color);
	}

	@Override
	public void deleteReferencingRecords(Integer color) {
		colorDAO.deleteReferencingRecords(color);
	}

	@Override
	public Page<Color> search(String ColorName, Pageable pageable) {
		// TODO Auto-generated method stub
		return colorDAO.findByNameContaining(ColorName, pageable);
	}
	
	@Override
	public boolean existsByNameIgnoreCase(String ColorName) {
	    return colorDAO.existsByNameIgnoreCase(ColorName) > 0;
	}

}
