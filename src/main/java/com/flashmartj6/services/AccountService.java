package com.flashmartj6.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.flashmartj6.entity.Account;

public interface AccountService {

	void UpdatePasswordByMail(String gmail);

	public List<Account> findAll(); // in ra lưu vào danh sách

	public Page<Account> findAll(Pageable pageable); // phân trang

	public Account findById(String id); // tìm kiếm theo id

	public Account create(Account account); // thêm danh sách

	public Account update(Account account); // sửa danh sách

	public void delete(Account account);

	Account findByGmail(String gmail);
	
	Page<Account> search(String Fullname, Pageable pageable);
	
	Account findByPhone(Integer Phone);
	Account findByEmail(String email);

	Account getUsernameAndPassword(String username, String password);

	void deleteReferencingRecords(String id);

	void deleteReferenceOrders(String id);

}
