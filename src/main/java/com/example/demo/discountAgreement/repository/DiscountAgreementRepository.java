package com.example.demo.discountAgreement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.discountAgreement.entity.DiscountAgreement;

public interface DiscountAgreementRepository extends JpaRepository<DiscountAgreement, Long> {
    
}
