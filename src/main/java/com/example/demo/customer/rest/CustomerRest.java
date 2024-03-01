package com.example.demo.customer.rest;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.customer.entity.Customer;
import com.example.demo.customer.service.CustomerService;
import com.example.demo.dto.incoming.DTO_CreateCustomer;
import com.example.demo.dto.outgoing.BaseDTO;
import com.example.demo.util.Util;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;




@CrossOrigin
@RestController()
@RequestMapping("/customer")
public class CustomerRest {

	@Autowired
	private CustomerService customerService;

	@GetMapping("/customertest")
	public String greeting() {
		return "hello";
	}
	@GetMapping("/createtestcustomer")
	public String createTestCustomer(){
		customerService.createTestCustomer_withDiscountAgreement();
		return "/createtestcustomer called";
	}
	
	@GetMapping("/getallcustomers")
	public BaseDTO<Customer> getAllCustomers(){
		BaseDTO<Customer> response = new BaseDTO<Customer>();
		var customers =  customerService.getAllCustomers();
		response.setData(customers);
		return response;

	}

	@PostMapping("/createcustomer")
	public BaseDTO<Customer> createCustomer(@RequestBody DTO_CreateCustomer entity) {
		BaseDTO<Customer> response = new BaseDTO<Customer>();

		try {
			if(!Util.isSafeFromSqlInject(entity)){
				response.setExplanation("Not safe");
				return response;
			}
		} catch (Exception e) {
			response.setExplanation("Something went wrong..");
			return response;
		}
		var serviceData = customerService.createCustomer(entity);
		var list = new ArrayList();
		list.add(serviceData);
		response.setData(list);
		return response;
		
	}
	
}
