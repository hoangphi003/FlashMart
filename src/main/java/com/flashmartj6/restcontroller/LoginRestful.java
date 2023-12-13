package com.flashmartj6.restcontroller;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.flashmartj6.entity.Account;
import com.flashmartj6.entity.Authority;
import com.flashmartj6.services.AccountService;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@CrossOrigin("*")
@RestController
public class LoginRestful {

	@Autowired
	AccountService accountService;

	@GetMapping("user/accountlogin/{gmail}")
	public Account getGmail(@PathVariable("gmail") String gmail) {
		return accountService.findByGmail(gmail);
	}

	@PostMapping("/user/login")
	public ResponseEntity<Object> getAccount(@RequestBody Account account) {
		Account user = accountService.getUsernameAndPassword(account.getUsername(), account.getPassword());
		if (user.getActive() == false) {
			return ResponseEntity.badRequest().build();
		}
//		if (user != null) {
//			SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS512);
//
//			String token = Jwts.builder().setSubject(user.getUsername()).setIssuedAt(new Date())
//					.setExpiration(new Date(System.currentTimeMillis() + 864000000)).signWith(key).compact();
//
//			return ResponseEntity.ok().body(Collections.singletonMap("token", token));
//		} else {
//			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy tài khoản này");
//		}
		
		return ResponseEntity.ok(user);
	}

}
