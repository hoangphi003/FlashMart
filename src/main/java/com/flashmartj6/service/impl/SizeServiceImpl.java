package com.flashmartj6.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.flashmartj6.entity.Size;
import com.flashmartj6.responsitory.SizeDAO;
import com.flashmartj6.services.SizeService;

@Service
public class SizeServiceImpl implements SizeService {
       
	@Autowired
	SizeDAO sizeDAO;
	
	
	@Override
	public List<Size> findAll() {
		// TODO Auto-generated method stub
		return sizeDAO.findAll();
	}

	@Override
	public Page<Size> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return sizeDAO.findAll(pageable);
	}

	@Override
	public Optional<Size> findById(Integer id) {
		// TODO Auto-generated method stub
		return sizeDAO.findById(id);
	}

	@Override
	public Size create(Size size) {
		// TODO Auto-generated method stub
		return sizeDAO.save(size);
	}

	@Override
	public Size update(Size size) {
		// TODO Auto-generated method stub
		return sizeDAO.save(size);
	}

	@Override
	public void delete(Size size) {
		sizeDAO.delete(size);
		
	}

	@Override
	public Page<Size> search(String SizeName, Pageable pageable) {
		// TODO Auto-generated method stub
		return sizeDAO.findByName(SizeName, pageable);
	}
	
	// Trong SizeServiceImpl
			@Override
			public boolean existsByNameIgnoreCase(String SizeName) {
			    return sizeDAO.existsByNameIgnoreCase(SizeName) > 0;
			}

	@Override
	public void deleteReferencingRecords(Integer id) {
		sizeDAO.deleteReferencingRecords(id);
	}

}
