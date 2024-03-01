package com.example.demo.order.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



import com.example.demo.dto.incoming.DTO_CreateOrder;
import com.example.demo.interfaces.Discount.IPreparedOrder;

import com.example.demo.order.errors.ProductNotFoundException;
import com.example.demo.order.types.OrderProducts;
import com.example.demo.product.service.ProductService;
import com.example.demo.util.Util;

@Service
public class OrderPrepareService {
    
    @Autowired()
    private ProductService productService;

    public List<OrderProducts> createOrderProducts(DTO_CreateOrder dto_createOrder){
        List<OrderProducts> ret = new ArrayList();
        try {
            ret = dto_createOrder.getProductQuantity().stream().map((cartProduct)-> {
                var product = productService.getProduct(cartProduct.getProduct().getId());
                if(product == null){
                    throw new ProductNotFoundException("Product that was in cart " + cartProduct.getProduct().getId() + " was not found from DB");
                }
                return new OrderProducts(product,cartProduct.getQuantity());
            }).toList();
        } catch (ProductNotFoundException e) {
            System.out.println(e.getMessage());
            return ret;
        }
        return ret;
    }

        public IPreparedOrder getBestDiscount(DTO_CreateOrder dto){
                // Try to fetch Products from database
        List<OrderProducts> orderProducts = createOrderProducts(dto);
        
        if(orderProducts.isEmpty()){
            throw new ProductNotFoundException("Product does not exist");
        }

        var productSavings = Util.calculateProductSavings(dto.getCustomer().getDiscountAgreement(), orderProducts);
        var orderSavings = Util.calculateOrderSavings(dto.getCustomer().getDiscountAgreement(), orderProducts);
        var buyXPayYsavings = Util.calculateBuyXPayYSavings(dto.getCustomer().getDiscountAgreement(), orderProducts);

        List<IPreparedOrder> allSavings = new ArrayList<>();

        productSavings.forEach(e-> allSavings.add(e));
        orderSavings.forEach(e-> allSavings.add(e));
        buyXPayYsavings.forEach(e-> allSavings.add(e));

        var preparedOrder = Util.getTheBestDiscount(allSavings);
        preparedOrder.setOrderProducts(orderProducts);
        return preparedOrder;
    }


}
