package com.flashmartj6.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.flashmartj6.entity.Account;
import com.flashmartj6.services.AccountService;

@CrossOrigin("*")
@RestController
public class RegisterResful {
	
	@Autowired
	AccountService accountService;
	
	@PostMapping("/user/register")
	public ResponseEntity<Account> Register(@RequestBody Account account) {
		
		 if (account.getActive() == null) {
		        account.setActive(true);
		    }
		accountService.create(account);
 		return ResponseEntity.ok().build();
	}
}
