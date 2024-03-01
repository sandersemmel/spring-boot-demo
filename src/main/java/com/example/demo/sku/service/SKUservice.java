package com.example.demo.sku.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.order.types.OrderProducts;
import com.example.demo.product.entity.Product;
import com.example.demo.sku.entity.SKU;
import com.example.demo.sku.repository.SKURepository;



@Service
public class SKUservice {
    @Autowired()
    private SKURepository skuRepository;
    

    public SKU createSKU(Product product) {
        try {

            SKU sku = new SKU();
            sku.setProduct(product);
            skuRepository.save(sku);
            skuRepository.flush();
            return sku;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw e;
        }
    }

    public List<SKU> createSkus(List<OrderProducts> orderProducts){
        List<SKU> skus = new ArrayList<>();
        orderProducts.stream().forEach(e-> {
            for (int index = 0; index < e.getQuantity(); index++) {
                skus.add(createSKU(e.getProduct()));
            }
        });
        return skus;
    }
}
