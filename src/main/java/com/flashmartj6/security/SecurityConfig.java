package com.flashmartj6.security;

import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.flashmartj6.entity.Account;
import com.flashmartj6.services.AccountService;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	private AccountService accountService;

	@Autowired
	BCryptPasswordEncoder pe;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(username -> {
			try {
				Account user = accountService.findById(username);
				String password = pe.encode(user.getPassword());
				String[] roles = user.getAuthorities().stream().map(er -> er.getRole().getId())
						.collect(Collectors.toList()).toArray(new String[0]);
				return User.withUsername(username).password(password).roles(roles).build();
			} catch (NoSuchElementException e) {
				System.out.println("User not found");
				throw new UsernameNotFoundException(username + "Not found");
			}
		});
	};

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();
		http.cors().disable();
		http.authorizeRequests().antMatchers("/admin/role").hasRole("dire").antMatchers("/admin/account")
				.hasRole("dire").antMatchers("/admin/authority").hasRole("dire").antMatchers("/admin/**")
				.authenticated().antMatchers("/admin/**").hasAnyRole("staff", "dire").anyRequest().permitAll();
		http.formLogin().loginPage("/auth/login").loginProcessingUrl("/auth/login")
				.defaultSuccessUrl("/admin/index/", false).failureUrl("/auth/login/error");

		http.exceptionHandling().accessDeniedPage("/auth/login/accesspage");

		http.logout().logoutUrl("/admin/logout").logoutSuccessUrl("/auth/login");
	};

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers(HttpMethod.OPTIONS, "/**");
	};
}
