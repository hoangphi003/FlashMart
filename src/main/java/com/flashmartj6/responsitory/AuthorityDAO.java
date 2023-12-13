package com.flashmartj6.responsitory;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.flashmartj6.entity.Authority;


@Repository
public interface AuthorityDAO extends JpaRepository<Authority, Integer> {
//	 @Query("SELECT c FROM Authority c WHERE c.AccountId LIKE %:keyword%")
//	 Page<Authority> findByNameContaining(@Param("keyword") String AccountId, Pageable pageable);
	

}
