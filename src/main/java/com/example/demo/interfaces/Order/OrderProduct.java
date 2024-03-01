package com.example.demo.interfaces.Order;

public class OrderProduct {

    private int quantity;
    private long productID;
    private double price;
    
     public OrderProduct(int quantity, long productID, double price) {
        this.quantity = quantity;
        this.productID = productID;
        this.price = price;
    }
    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    public int getQuantity() {
         return quantity;
     }
     public void setQuantity(int quantity) {
         this.quantity = quantity;
     }
     public long getProductID() {
         return productID;
     }
     public void setProductID(long productID) {
         this.productID = productID;
     } 

}
