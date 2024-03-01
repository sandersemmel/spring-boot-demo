package com.example.demo.sku.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.sku.entity.SKU;

public interface SKURepository extends JpaRepository<SKU, Long> {
    
}

