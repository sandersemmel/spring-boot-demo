package com.example.demo.dto.incoming;

public class DTO_AttachCustomerToDiscount {
    private long customerID;
    private long discountID;
    
    public long getCustomerID() {
        return customerID;
    }
    public void setCustomerID(long customerID) {
        this.customerID = customerID;
    }
    public long getDiscountID() {
        return discountID;
    }
    public void setDiscountID(long discountID) {
        this.discountID = discountID;
    }
}
