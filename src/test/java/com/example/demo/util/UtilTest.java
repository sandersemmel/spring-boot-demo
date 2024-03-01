package com.example.demo.util;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.example.demo.discountAgreement.entity.AgreementType;
import com.example.demo.discountAgreement.entity.DiscountAgreement;
import com.example.demo.interfaces.Discount.BuyXPayYsavings;
import com.example.demo.interfaces.Discount.OrderSavings;
import com.example.demo.interfaces.Discount.ProductSavings;
import com.example.demo.order.types.OrderProducts;
import com.example.demo.product.entity.Product;

public class UtilTest {
    @Test
    void testCalculateProductSavings() {
        // Given order which total is 400
        // Discount agreement of 5 % off a single product
        // The result should be ProductSavings where
            // discountedprice == 95
            // totalsavings == 5
            // originaltotal 400

        List<DiscountAgreement> customerDiscountAgreements = new ArrayList<>();
        // discount agreement
        DiscountAgreement discountAgreement = new DiscountAgreement();
        discountAgreement.setAgreementType(AgreementType.PERCENTAGE_OFF_PRODUCT);
        discountAgreement.setPercentageOff(5);
    
        // product with discount
        Product product = new Product();
        product.setId(1L);
        product.setPrice(100);
        discountAgreement.setProduct(product);

        // product2 (no discount)
        Product product2 = new Product();
        product2.setId(2L);
        product2.setPrice(100);
        
        customerDiscountAgreements.add(discountAgreement);

        List<OrderProducts> orderProducts = new ArrayList<>();
        orderProducts.add(new OrderProducts(product, 1));
        orderProducts.add(new OrderProducts(product2, 3));
        
        ProductSavings productSavings = Util.calculateProductSavings(customerDiscountAgreements, orderProducts).get(0);

        assertEquals(400, productSavings.getOriginalOrderTotal());
        assertEquals(395, productSavings.getTotalAfterSavings());
        assertEquals(5, productSavings.getTotalSavings());
    }

    @Test
    void testCalculateOrderSavings() {
       // Given order which total is 1000
        // Discount agreement of 10 % off whole order
        // The result should be List<OrderSavings> where
            // originalOrderTotal is 1000
            // totalAfterSavings is 900
            // totalSavings is 100


            List<DiscountAgreement> customerDiscountAgreements = new ArrayList<>();
            // discount agreement
            DiscountAgreement discountAgreement = new DiscountAgreement();
            discountAgreement.setAgreementType(AgreementType.PERCENTAGE_OFF_WHOLE_ORDER);
            discountAgreement.setPercentageOff(10);
        
        
            Product product = new Product();
            product.setId(1L);
            product.setPrice(100);
        
            Product product2 = new Product();
            product2.setId(2L);
            product2.setPrice(900);
            
            customerDiscountAgreements.add(discountAgreement);
    
            List<OrderProducts> orderProducts = new ArrayList<>();
            orderProducts.add(new OrderProducts(product, 1));
            orderProducts.add(new OrderProducts(product2, 1));
            
            List<OrderSavings> orderSavingsList = Util.calculateOrderSavings(customerDiscountAgreements, orderProducts);
            OrderSavings orderSavings = orderSavingsList.get(0);
    
            assertEquals(orderSavings.getOriginalOrderTotal(), 1000);
            assertEquals(orderSavings.getTotalAfterSavings(), 900);
            assertEquals(orderSavings.getTotalSavings(), 100);
    }

	@Test
	void testCalculateBuyXPayYSavings() {
		       // Given order which total is 1000
        // Discount agreement of 10 % off whole order
        // The result should be List<OrderSavings> where
            // originalOrderTotal is 1500
            // totalAfterSavings is 900
            // totalSavings is 100


            List<DiscountAgreement> customerDiscountAgreements = new ArrayList<>();
            // discount agreement
            DiscountAgreement discountAgreement = new DiscountAgreement();
            discountAgreement.setAgreementType(AgreementType.BUY_X_ONLY_PAY_Y);
            discountAgreement.setMustBuyAmount(5);
            discountAgreement.setOnlyPayForAmount(4);
            
            Product product = new Product();
            product.setId(1L);
            product.setPrice(100);
            discountAgreement.setProduct(product);
        
            Product product2 = new Product();
            product2.setId(2L);
            product2.setPrice(1000);
            
            customerDiscountAgreements.add(discountAgreement);
    
            List<OrderProducts> orderProducts = new ArrayList<>();
            orderProducts.add(new OrderProducts(product, 5));
            orderProducts.add(new OrderProducts(product2, 1));
            
            List<BuyXPayYsavings> buyXPayYsavings = Util.calculateBuyXPayYSavings(customerDiscountAgreements, orderProducts);
            BuyXPayYsavings xsavings = buyXPayYsavings.get(0);
    
            assertEquals(xsavings.getOriginalOrderTotal(), 1500);
            assertEquals(xsavings.getTotalAfterSavings(), 1400);
            assertEquals(xsavings.getTotalSavings(), 100);
	}


    @Test
    void testCalculateOrderSavings_multiple_discount_for_customer() {
       // Given order which total is 1000
        // Discount agreement of 10 % off whole order
        // Discount agreement of 20 % off whole order
        // The result be the best discount


            List<DiscountAgreement> customerDiscountAgreements = new ArrayList<>();
            // discount agreement
            DiscountAgreement discountAgreement = new DiscountAgreement();
            discountAgreement.setAgreementType(AgreementType.PERCENTAGE_OFF_WHOLE_ORDER);
            discountAgreement.setPercentageOff(10);

            DiscountAgreement discountAgreement2 = new DiscountAgreement();
            discountAgreement2.setAgreementType(AgreementType.PERCENTAGE_OFF_WHOLE_ORDER);
            discountAgreement2.setPercentageOff(20);
        
        
            Product product = new Product();
            product.setId(1L);
            product.setPrice(100);
        
            Product product2 = new Product();
            product2.setId(2L);
            product2.setPrice(900);
            
            customerDiscountAgreements.add(discountAgreement);
            customerDiscountAgreements.add(discountAgreement2);
    
            List<OrderProducts> orderProducts = new ArrayList<>();
            orderProducts.add(new OrderProducts(product, 1));
            orderProducts.add(new OrderProducts(product2, 1));
            
            List<OrderSavings> orderSavingsList = Util.calculateOrderSavings(customerDiscountAgreements, orderProducts);
            
            assertEquals(orderSavingsList.size(),2);
            OrderSavings orderSavings = orderSavingsList.get(0);

            
            var bestDiscount = Util.getTheBestDiscount(orderSavingsList);
            assertEquals(bestDiscount.getTotalSavings(), 200);

    }

   
}
