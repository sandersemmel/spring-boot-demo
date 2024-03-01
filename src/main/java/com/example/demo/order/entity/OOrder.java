package com.example.demo.order.entity;



import java.util.List;

import com.example.demo.common.BaseEntity;
import com.example.demo.customer.entity.Customer;
import com.example.demo.discountAgreement.entity.DiscountAgreement;
import com.example.demo.product.entity.Product;
import com.example.demo.sku.entity.SKU;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity()
@Table(name = "OORDER")
public class OOrder extends BaseEntity{

    @OneToMany(cascade= CascadeType.ALL)
    @JoinColumn(name = "order_id")  // This creates a foreign key column in the Product table
    private List<Product> products;
    
    @OneToMany
    private List<SKU> SKU;

    @ManyToOne
    private DiscountAgreement discountAgreement;

    @ManyToOne
    private Customer customer;

    private double totalAfterDiscount;

    private double totalBeforeDiscount;



    public OOrder() {
        // Default constructor required by JPA
    }

    public OOrder(List<Product> products, double total) {
        this.products = products;
        this.totalAfterDiscount = total;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public double getTotalAfterDiscount() {
        return totalAfterDiscount;
    }

    public void setTotalAfterDiscount(double total) {
        this.totalAfterDiscount = total;
    }

    public List<SKU> getSKU() {
        return SKU;
    }

    public void setSKU(List<SKU> sKU) {
        SKU = sKU;
    }

    public DiscountAgreement getDiscountAgreement() {
        return discountAgreement;
    }

    public void setDiscountAgreement(DiscountAgreement discountAgreement) {
        this.discountAgreement = discountAgreement;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
    public double getTotalBeforeDiscount() {
        return totalBeforeDiscount;
    }

    public void setTotalBeforeDiscount(double totalBeforeDiscount) {
        this.totalBeforeDiscount = totalBeforeDiscount;
    }
    // Getters and setters
}