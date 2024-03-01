package com.example.demo.discountAgreement.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.customer.repository.CustomerRepository;
import com.example.demo.discountAgreement.entity.AgreementType;
import com.example.demo.discountAgreement.entity.DiscountAgreement;
import com.example.demo.discountAgreement.repository.DiscountAgreementRepository;
import com.example.demo.dto.DTO_DiscountAgreement;
import com.example.demo.dto.incoming.DTO_AttachCustomerToDiscount;
import com.example.demo.product.entity.Product;
import com.example.demo.product.repository.ProductRepository;

@Service
public class DiscountAgreementService {
    
    @Autowired
    DiscountAgreementRepository discountRepository;

    @Autowired
    ProductRepository productRepository;

	@Autowired
	CustomerRepository customerRepository;

    public void createDiscountAgreement(DTO_DiscountAgreement dto_discountAgreement){


	Product product = null;

		if(dto_discountAgreement.getAgreementType() == AgreementType.PERCENTAGE_OFF_PRODUCT || dto_discountAgreement.getAgreementType() == AgreementType.BUY_X_ONLY_PAY_Y ){
			product = productRepository.getReferenceById(dto_discountAgreement.getProductId());
			if(product == null){
                System.out.println("Can not continue, the product does not exist");
				return;
			}
		}

		// TODO MAPPER?
		DiscountAgreement discountAgreement = new DiscountAgreement();
		discountAgreement.setAgreementType(dto_discountAgreement.getAgreementType());
		discountAgreement.setMustBuyAmount(dto_discountAgreement.getMustBuyAmount());
		discountAgreement.setOnlyPayForAmount(dto_discountAgreement.getOnlyPayForAmount());
		discountAgreement.setPercentageOff(dto_discountAgreement.getPercentageOff());
		discountAgreement.setProduct(product);
		

        discountRepository.save(discountAgreement);
    }

    public List<DiscountAgreement> getAllAgreements() {
		return this.discountRepository.findAll();
    }

    public boolean attachDiscountToCustomer(DTO_AttachCustomerToDiscount entity) {
		var discount = discountRepository.findById(entity.getDiscountID()).orElse(null);
		var customer = customerRepository.findById(entity.getCustomerID()).orElse(null);
		if(discount == null || customer == null){
			return false;
		}	
		var oldDiscountAgreements = customer.getDiscountAgreement();
		oldDiscountAgreements.add(discount);

		customerRepository.flush();
		return true;
    }
}
