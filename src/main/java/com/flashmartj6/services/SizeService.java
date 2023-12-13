package com.flashmartj6.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.flashmartj6.entity.Color;
import com.flashmartj6.entity.Size;

public interface SizeService {
   
public List<Size> findAll() ;
	
    public Page<Size> findAll(Pageable pageable) ;
	
	public Optional<Size> findById(Integer id) ;

	public Size create(Size size) ;

	public Size update(Size size) ;

	public void delete(Size size) ;
	
	Page<Size> search(String SizeName, Pageable pageable);
	
	public boolean existsByNameIgnoreCase(String SizeName);

	public void deleteReferencingRecords(Integer id);

 }
