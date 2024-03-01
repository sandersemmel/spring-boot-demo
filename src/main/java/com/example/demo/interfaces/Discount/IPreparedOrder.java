package com.example.demo.interfaces.Discount;

import java.util.List;

import com.example.demo.customer.entity.Customer;
import com.example.demo.discountAgreement.entity.DiscountAgreement;
import com.example.demo.order.types.OrderProducts;
import com.example.demo.sku.entity.SKU;

public interface IPreparedOrder {

     double getOriginalOrderTotal();

     double getTotalAfterSavings();

     double getTotalSavings();

     DiscountAgreement getDiscountAgreement();

     List<OrderProducts> getOrderProducts();

     void setOrderProducts(List<OrderProducts> orderProducts);

     Customer getCustomer();

     void setCustomer(Customer customer);

     List<SKU> getOrderSKUs();

     void setOrderSKUs(List<SKU> skus);
    }

