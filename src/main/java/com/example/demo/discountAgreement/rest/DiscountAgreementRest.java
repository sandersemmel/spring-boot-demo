package com.example.demo.discountAgreement.rest;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.discountAgreement.entity.DiscountAgreement;
import com.example.demo.discountAgreement.service.DiscountAgreementService;
import com.example.demo.dto.DTO_DiscountAgreement;
import com.example.demo.dto.incoming.DTO_AttachCustomerToDiscount;
import com.example.demo.dto.outgoing.BaseDTO;
import com.example.demo.util.Util;




@CrossOrigin
@RestController()
@RequestMapping("/discountagreement")
public class DiscountAgreementRest {

	@Autowired
	private DiscountAgreementService discountService;
	
	@PostMapping("/creatediscount")
	public String createDiscount(@RequestBody DTO_DiscountAgreement dto_discountAgreement) {
		System.out.println("hit");
		try {
			if(!Util.isSafeFromSqlInject(dto_discountAgreement)){
				return "Not created";
			}
		} catch (Exception e) {
			return "Not created - unable to check for sql injection";
		}


		this.discountService.createDiscountAgreement(dto_discountAgreement);
		
		return "Created";
	}

	@GetMapping("/getallagreements")
	public List<DiscountAgreement> getMethodName() {
		return discountService.getAllAgreements();
	}

	@PostMapping("attachdiscounttocustomer")
	public BaseDTO<String> attachDiscountToCustomer(@RequestBody DTO_AttachCustomerToDiscount entity) {
		BaseDTO<String> response = new BaseDTO<String>();
		try {
			if(!Util.isSafeFromSqlInject(entity)){
				response.setExplanation("SQL injection problem");
				return response;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return response;
		}


		discountService.attachDiscountToCustomer(entity);
		return response;
	}
	
	
}
