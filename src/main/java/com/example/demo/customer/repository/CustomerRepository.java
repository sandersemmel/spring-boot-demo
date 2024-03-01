package com.example.demo.customer.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.customer.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer,Long> {
    
}
