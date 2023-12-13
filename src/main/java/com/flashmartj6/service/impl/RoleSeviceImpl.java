package com.flashmartj6.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.flashmartj6.entity.Role;
import com.flashmartj6.responsitory.RoleDAO;
import com.flashmartj6.services.RoleService;

@Service
public class RoleSeviceImpl implements RoleService {

	@Autowired
	RoleDAO aResponsitory2;

	@Override
	public List<Role> findAll() {
		// TODO Auto-generated method stub
		return aResponsitory2.findAll();
	}

	@Override
	public Page<Role> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return aResponsitory2.findAll(pageable);
	}

	@Override
	public Optional<Role> findById(String id) {
		// TODO Auto-generated method stub
		return aResponsitory2.findById(id);
	}

	@Override
	public Role create(Role role) {
		// TODO Auto-generated method stub
		return aResponsitory2.save(role);
	}

	@Override
	public Role update(Role role) {
		// TODO Auto-generated method stub
		return aResponsitory2.save(role);
	}

	@Override
	public void delete(Role role) {
		// TODO Auto-generated method stub
		aResponsitory2.delete(role);
	}

	@Override
	public Page<Role> search(String RoleName, Pageable pageable) {
		// TODO Auto-generated method stub
		return aResponsitory2.findByName(RoleName, pageable);
	}

	@Override
	public void deleteAuthority(String id) {
		aResponsitory2.deleteAuthority(id);
	}
	
//	@Override
//    public boolean existsById(String id) {
//        return aResponsitory2.existsById(id);
//    }
//
//    @Override
//    public boolean existsByRoleName(String roleName) {
//        return aResponsitory2.existsByRoleName(roleName);
//    }

}
