package com.shahidfoy.springsecurity.controller;

import com.shahidfoy.springsecurity.model.Accounts;
import com.shahidfoy.springsecurity.model.Customer;
import com.shahidfoy.springsecurity.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountController {

	@Autowired
	private AccountRepository accountRepository;
	
	@PostMapping("/myAccount")
	public Accounts getAccountDetails(@RequestBody Customer customer) {
		Accounts accounts = accountRepository.findByCustomerId(customer.getId());
		if (accounts != null) {
			return accounts;
		} else {
			return null;
		}
	}

}
