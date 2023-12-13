package com.flashmartj6.service.impl;

import java.util.List;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.flashmartj6.entity.Account;
import com.flashmartj6.entity.Authority;
import com.flashmartj6.entity.Color;
import com.flashmartj6.entity.ProductDetail;
import com.flashmartj6.responsitory.AccountDAO;
import com.flashmartj6.services.AccountService;

@Service
public class AccountServiceImpl implements AccountService {
	@Autowired
	AccountDAO accountDAO;

	@Override
	public void UpdatePasswordByMail(String gmail) {
		accountDAO.UpdatePassword(gmail);
	}

	@Override
	public List<Account> findAll() {
		return accountDAO.findAll();
	}

	@Override
	public Page<Account> findAll(Pageable pageable) {
		return accountDAO.findAll(pageable);
	}

	@Override
	@Transactional
	public Account findById(String username) {
		return accountDAO.findById(username).get();
	}

	@Override
	public Account create(Account account) {
		// TODO Auto-generated method stub
		return accountDAO.save(account);
	}

	@Override
	public Account update(Account account) {
		// TODO Auto-generated method stub
		return accountDAO.save(account);
	}

	@Override
	public void delete(Account account) {
		// TODO Auto-generated method stub
		accountDAO.delete(account);
	}

	@Override
	public Account findByGmail(String gmail) {
		return accountDAO.getByGmail(gmail);
	}

	@Override
	public Account getUsernameAndPassword(String username, String password) {
		return accountDAO.findByEmailAndPassword(username, password);
	}

	@Override
	public Page<Account> search(String Fullname, Pageable pageable) {
		return accountDAO.findByName(Fullname, pageable);
	}

	@Override
	public void deleteReferencingRecords(String id) {
		accountDAO.deleteReferencingAuthority(id);;
	}

	@Override
	public void deleteReferenceOrders(String id) {
		accountDAO.deleteReferenceOrders(id);
	}

	@Override
	public Account findByPhone(Integer Phone) {
		return accountDAO.findByPhone(Phone);
	}

	@Override
	public Account findByEmail(String Email) {
		return accountDAO.findByEmail(Email);
	}
}
