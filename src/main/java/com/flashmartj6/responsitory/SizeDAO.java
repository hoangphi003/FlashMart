package com.flashmartj6.responsitory;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.flashmartj6.entity.Size;

@Repository
public interface SizeDAO extends JpaRepository<Size, Integer> {

	@Query("SELECT c FROM Size c WHERE c.SizeName LIKE %:keyword%")
	Page<Size> findByName(@Param("keyword") String SizeName, Pageable pageable);

	// Trong SizeDAO
	@Query("SELECT COUNT(c) FROM Size c WHERE LOWER(c.SizeName) = LOWER(:SizeName)")
	int existsByNameIgnoreCase(@Param("SizeName") String SizeName);

	@Transactional
	@Modifying
	@Query("UPDATE ProductDetail pd SET pd.size = NULL WHERE pd.size.id = :sizeIdToDelete")
	void deleteReferencingRecords(@Param("sizeIdToDelete") Integer sizeIdToDelete);
}
