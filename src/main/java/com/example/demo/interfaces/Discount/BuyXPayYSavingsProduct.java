package com.example.demo.interfaces.Discount;

public class BuyXPayYSavingsProduct {

     double totalCostBeforeDiscount;
     int totalProductsForCustomer;
     double totalcostAfterDiscount;
     
    public BuyXPayYSavingsProduct(double totalCostBeforeDiscount, int totalProductsForCustomer,
            double totalcostAfterDiscount) {
        this.totalCostBeforeDiscount = totalCostBeforeDiscount;
        this.totalProductsForCustomer = totalProductsForCustomer;
        this.totalcostAfterDiscount = totalcostAfterDiscount;
    }
    public double getTotalCostBeforeDiscount() {
        return totalCostBeforeDiscount;
    }
    public void setTotalCostBeforeDiscount(double totalCostBeforeDiscount) {
        this.totalCostBeforeDiscount = totalCostBeforeDiscount;
    }
    public int getTotalProductsForCustomer() {
        return totalProductsForCustomer;
    }
    public void setTotalProductsForCustomer(int productsTotalAmount) {
        this.totalProductsForCustomer = productsTotalAmount;
    }
    public double getTotalcostAfterDiscount() {
        return totalcostAfterDiscount;
    }
    public void setTotalcostAfterDiscount(double totalcostAfterDiscount) {
        this.totalcostAfterDiscount = totalcostAfterDiscount;
    }
}
