package com.flashmartj6.responsitory;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import com.flashmartj6.entity.Role;


@Repository
public interface RoleDAO extends JpaRepository<Role, String> {

	
	@Query("SELECT c FROM Role c WHERE c.RoleName LIKE %:keyword%")
	 Page<Role> findByName(@Param("keyword") String RoleName, Pageable pageable);

	@Transactional
	@Modifying
	@Query("UPDATE Authority pd SET pd.role = NULL WHERE pd.role.id = :id")
	void deleteAuthority(String id);
	
//	boolean existsById(String id);
//
//	boolean existsByRoleName(String RoleName);

}
