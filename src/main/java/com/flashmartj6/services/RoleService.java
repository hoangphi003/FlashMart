package com.flashmartj6.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import com.flashmartj6.entity.Role;


public interface RoleService {
public List<Role> findAll() ; //in ra lưu vào danh sách
	
	public Page<Role> findAll(Pageable pageable) ;  //phân trang
	
	public Optional<Role> findById(String id) ;    //tìm kiếm theo id

	public Role create(Role role) ;     //thêm danh sách

	public Role update(Role role) ;   //sửa danh sách

	public void delete(Role role) ;
	
	Page<Role> search(String RoleName, Pageable pageable);

	public void deleteAuthority(String id);
	
	
//	boolean existsById(String id);
//
//    boolean existsByRoleName(String RoleName);
}
