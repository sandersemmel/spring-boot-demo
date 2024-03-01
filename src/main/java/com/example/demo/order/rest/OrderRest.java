package com.example.demo.order.rest;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.customer.entity.Customer;
import com.example.demo.customer.service.CustomerService;
import com.example.demo.dto.incoming.DTO_CreateOrder;
import com.example.demo.dto.outgoing.BaseDTO;
import com.example.demo.order.entity.OOrder;
import com.example.demo.order.service.OrderDiscountService;
import com.example.demo.order.service.OrderPrepareService;
import com.example.demo.order.service.OrderService;
import com.example.demo.product.service.ProductService;
import com.example.demo.sku.service.SKUservice;
import com.example.demo.util.Util;

import org.springframework.web.bind.annotation.RequestParam;


@CrossOrigin
@RestController()
@RequestMapping("/order")
public class OrderRest {

    @Autowired
    private OrderService orderService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private OrderPrepareService orderPrepareService;

    @Autowired
    private ProductService productService;

    @Autowired
    private OrderDiscountService orderDiscountService;

    @Autowired
    private SKUservice skUservice;

    @GetMapping("/createtestorder")
    public String createTestOrder(){
        orderService.createTestOrder();
        return "createtestorder called";
    }

    @GetMapping("getallorders")
    public List<OOrder> getAllOrders(){
        return orderService.getAllOrders();
    }
    
    @PostMapping("/createorder")
    public BaseDTO<OOrder> createOrder(@RequestBody DTO_CreateOrder dto) {
        
        BaseDTO<OOrder> orderResponse = new BaseDTO<>();
        
        try {
            var safe = Util.isSafeFromSqlInject(dto);
            if(!safe){
                throw new Exception("Not safe data");
            }
            var customer = customerService.fillCustomer(dto.getCustomerID());
            dto.setCustomer(customer);
            var preparedOrder = orderPrepareService.getBestDiscount(dto);
            preparedOrder.setOrderSKUs(skUservice.createSkus(preparedOrder.getOrderProducts()));
            preparedOrder.setCustomer(customer);
            var createdOrder = orderService.createOrder(preparedOrder);
            
            orderResponse.setData(Arrays.asList(createdOrder));

        } catch (Exception e) {
            System.out.println(e.getMessage());
            orderResponse.setExplanation(e.getMessage());
            e.printStackTrace();
        }
        return orderResponse;
        
    }
    
}
