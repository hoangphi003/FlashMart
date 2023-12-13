package com.flashmartj6.responsitory;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.flashmartj6.entity.Color;

@Repository
public interface ColorDAO extends JpaRepository<Color, Integer> {

	@Query("SELECT c FROM Color c WHERE c.ColorName LIKE %:keyword%")
	Page<Color> findByNameContaining(@Param("keyword") String ColorName, Pageable pageable);

	@Transactional
	@Modifying
	@Query("UPDATE ProductDetail pd SET pd.color = NULL WHERE pd.color.id = :colorIdToDelete")
	void deleteReferencingRecords(@Param("colorIdToDelete") Integer colorIdToDelete);

	// Trong ColorDAO
	@Query("SELECT COUNT(c) FROM Color c WHERE LOWER(c.ColorName) = LOWER(:ColorName)")
	int existsByNameIgnoreCase(@Param("ColorName") String ColorName);
}