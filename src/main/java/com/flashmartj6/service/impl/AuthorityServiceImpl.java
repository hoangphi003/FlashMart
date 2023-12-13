package com.flashmartj6.service.impl;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.flashmartj6.entity.Authority;
import com.flashmartj6.responsitory.AuthorityDAO;
import com.flashmartj6.services.AuthorityService;

@Service
 public class AuthorityServiceImpl implements AuthorityService  {
   
	@Autowired
	AuthorityDAO authorityDAO  ;

	@Override
	public List<Authority> findAll() {
		// TODO Auto-generated method stub
		return authorityDAO.findAll();
	}

	@Override
	public Page<Authority> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return authorityDAO.findAll(pageable);
	}

	@Override
	public Optional<Authority> findById(Integer id) {
		// TODO Auto-generated method stub
		return authorityDAO.findById(id);
	}

	@Override
	public Authority create(Authority authority) {
		// TODO Auto-generated method stub
		return authorityDAO.save(authority);
	}

	@Override
	public Authority update(Authority authority) {
		// TODO Auto-generated method stub
		return authorityDAO.save(authority);
	}
    
	@Override
	public void delete(Authority authority) {
		// TODO Auto-generated method stub
		authorityDAO.delete(authority);
	}

//	@Override
//	public Page<Authority> search(String AccountId, Pageable pageable) {
//		// TODO Auto-generated method stub
//		return authorityDAO.findByNameContaining(AccountId, pageable);
//	}

} 
