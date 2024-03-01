package com.example.demo.interfaces.Discount;

import java.util.List;

import com.example.demo.customer.entity.Customer;
import com.example.demo.discountAgreement.entity.DiscountAgreement;
import com.example.demo.order.types.OrderProducts;
import com.example.demo.product.entity.Product;
import com.example.demo.sku.entity.SKU;

public class ProductSavings implements IPreparedOrder{
    private Product product;
    private double originalOrderTotal;
    private double totalAfterSavings;
    private double totalSavings;
    private DiscountAgreement discountAgreement;
    private List<OrderProducts> orderProducts;
    private List<SKU> orderSKUs;
    private Customer customer;
    

    public double getOriginalOrderTotal() {
        return originalOrderTotal;
    }
    public void setOriginalOrderTotal(double originalPrice) {
        this.originalOrderTotal = originalPrice;
    }
    public double getTotalAfterSavings() {
        return totalAfterSavings;
    }
    public void setTotalAfterSavings(double discountedPrice) {
        this.totalAfterSavings = discountedPrice;
    }
    public double getTotalSavings() {
        return totalSavings;
    }
    public void setTotalSavings(double totalSavings) {
        this.totalSavings = totalSavings;
    }
    public Product getProduct() {
        return product;
    }
    public void setProduct(Product product) {
        this.product = product;
    }
    public DiscountAgreement getDiscountAgreement() {
        return discountAgreement;
    }
    public void setDiscountAgreement(DiscountAgreement discountAgreement) {
        this.discountAgreement = discountAgreement;
    }
    public List<OrderProducts> getOrderProducts() {
        return orderProducts;
    }
    public void setOrderProducts(List<OrderProducts> orderProducts) {
        this.orderProducts = orderProducts;
    }
    public List<SKU> getOrderSKUs() {
        return orderSKUs;
    }
    public void setOrderSKUs(List<SKU> orderSKUs) {
        this.orderSKUs = orderSKUs;
    }
    public Customer getCustomer() {
        return customer;
    }
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }


}
