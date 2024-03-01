package com.example.demo.customer.entity;

import java.util.List;

import com.example.demo.common.BaseEntity;
import com.example.demo.discountAgreement.entity.DiscountAgreement;
import com.example.demo.order.entity.OOrder;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity()
@Table(name = "CUSTOMER")
public class Customer extends BaseEntity{
    
    @ManyToMany()
    private List<DiscountAgreement> discountAgreement;

    @OneToMany
    private List<OOrder> orders;

    String name;

    public List<DiscountAgreement> getDiscountAgreement() {
        return discountAgreement;
    }

    public void setDiscountAgreement(List<DiscountAgreement> discountAgreement) {
        this.discountAgreement = discountAgreement;
    }

    public String getName(){
        return this.name;
    }
    public void setName(String _name){
        this.name = _name;
    }

    public List<OOrder> getOrders() {
        return orders;
    }

    public void setOrders(List<OOrder> orders) {
        this.orders = orders;
    }
}
