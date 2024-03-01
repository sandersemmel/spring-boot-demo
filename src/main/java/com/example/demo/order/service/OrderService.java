package com.example.demo.order.service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.customer.repository.CustomerRepository;
import com.example.demo.customer.service.CustomerService;
import com.example.demo.discountAgreement.entity.DiscountAgreement;
import com.example.demo.discountAgreement.repository.DiscountAgreementRepository;
import com.example.demo.interfaces.Discount.IPreparedOrder;
import com.example.demo.order.entity.OOrder;
import com.example.demo.order.repository.OrderRepository;


@Service
public class OrderService {

    @Autowired()
    private OrderRepository orderRepository;  

    public void createTestOrder(){
        //orderRepository.save(util.getTestOrder());
    }

    public List<OOrder> getAllOrders() {
        return orderRepository.findAll();
    }
    public OOrder createOrder(IPreparedOrder preparedOrder){
        OOrder order = new OOrder();
        preparedOrder.getOrderProducts();
        order.setSKU(preparedOrder.getOrderSKUs());
        order.setDiscountAgreement(preparedOrder.getDiscountAgreement());
        order.setTotalAfterDiscount(preparedOrder.getTotalAfterSavings());
        order.setTotalBeforeDiscount(preparedOrder.getOriginalOrderTotal());
        order.setCustomer(preparedOrder.getCustomer());


        var savedOrder = orderRepository.save(order);
        orderRepository.flush();

        return savedOrder;

    }

}
