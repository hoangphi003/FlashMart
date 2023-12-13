package com.flashmartj6.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.flashmartj6.entity.Color;

public interface ColorService {

    public List<Color> findAll() ;
	
    public Page<Color> findAll(Pageable pageable) ;
	
	public Optional<Color> findById(Integer id) ;

	public Color create(Color color) ;

	public Color update(Color color) ;

	public void delete(Color color) ;
	
	Page<Color> search(String ColorName, Pageable pageable);

	public void deleteColorAndRelatedDetails(Color color);

	public void deleteReferencingRecords(Integer color);
	
	public boolean existsByNameIgnoreCase(String ColorName);
}
