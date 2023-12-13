package com.flashmartj6.responsitory;

import com.flashmartj6.entity.Account;
import com.flashmartj6.entity.Category;

import javax.transaction.Transactional;
import javax.websocket.server.PathParam;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountDAO extends JpaRepository<Account, String> {

	@Modifying
	@Transactional
	@Query("update Account a set a.Password = '123456' where a.Email like %:gmail%")
	void UpdatePassword(@PathParam("gmail") String gmail);

	@Query("select a from Account a where a.Email like :gmail")
	Account getByGmail(@Param("gmail") String gmail);

	@Transactional
	@Query("SELECT u FROM Account u WHERE u.Username = :e AND u.Password = :password")
	Account findByEmailAndPassword(@Param("e") String username, @Param("password") String password);

	@Query("SELECT c FROM Account c WHERE c.Fullname LIKE %:keyword%")
	Page<Account> findByName(@Param("keyword") String Fullname, Pageable pageable);

	@Transactional
	@Modifying
	@Query("UPDATE Authority pd SET pd.account = NULL WHERE pd.account.Username = :id")
	void deleteReferencingAuthority(@Param("id") String id);

	@Transactional
	@Modifying
	@Query("UPDATE Order pd SET pd.account = NULL WHERE pd.account.Username = :id")
	void deleteReferenceOrders(@Param("id") String id);
	
	@Query("SELECT a FROM Account a WHERE a.Email = :email")
    Account findByEmail(@Param("email") String email);
	
	@Query("SELECT a FROM Account a WHERE a.Phone = :Phone")
    Account findByPhone(@Param("Phone") Integer Phone);
	

}
