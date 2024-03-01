package com.example.demo.dto.incoming;

import com.example.demo.product.entity.Product;

public class DTO_CartProduct {
   private int quantity;
   private Product product;
   
public int getQuantity() {
    return quantity;
}
public void setQuantity(int quantity) {
    this.quantity = quantity;
}
public Product getProduct() {
    return product;
}
public void setProductID(Product product) {
    this.product = product;
} 
}
