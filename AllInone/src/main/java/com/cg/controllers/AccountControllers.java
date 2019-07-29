package com.cg.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cg.daos.AccountDao;
import com.cg.exceptions.ApplicationException;
import com.cg.model.Account;



@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api")
public class AccountControllers {

	@Autowired
	AccountDao repository;

	@GetMapping("/accounts")
	public List<Account> getAllAccount() {
		System.out.println("Get all Accounts...");

		List<Account> account = new ArrayList<>();
		repository.findAll().forEach(account::add);
		
		return account;
	}

	@PostMapping(value = "/accounts/create",consumes= {"application/json"})
	public Account postCustomer(@RequestBody Account account) {

		Account _account = repository.save(account);
		return _account;
	}

	@DeleteMapping("/accounts/{mobileno}")
	public ResponseEntity<String> deleteCustomer(@PathVariable("mobileno") long mobileno) {
		System.out.println("Delete account with ID = " + mobileno + "...");

		repository.deleteById(mobileno);

		return new ResponseEntity<>("Account has been deleted!", HttpStatus.OK);
	}

	@DeleteMapping("/accounts/delete")
	public ResponseEntity<String> deleteAllCustomers() {
		System.out.println("Delete All Account...");

		repository.deleteAll();

		return new ResponseEntity<>("All Account have been deleted!", HttpStatus.OK);
	}

	@GetMapping(value = "accounts/mobileno/{mobileno}")
	public List<Account> findBymobileno(@PathVariable long mobileno) {

		List<Account> account = repository.findBymobileno(mobileno);
		return account;
	}

	@PutMapping("/accounts/{mobileno}")
	public ResponseEntity<Account> updateCustomer(@PathVariable("mobileno") long mobileno, @RequestBody Account customer) {
		System.out.println("Update Customer with ID = " + mobileno + "...");

		Optional<Account> customerData = repository.findById(mobileno);

		if (customerData.isPresent()) {
			Account _customer = customerData.get();
			_customer.setMobileno(customer.getMobileno());
			_customer.setHoldername(customer.getHoldername());
			_customer.setBalance(customer.getBalance());
			return new ResponseEntity<>(repository.save(_customer), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
}
