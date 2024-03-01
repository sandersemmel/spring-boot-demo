package com.example.demo.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import org.springframework.stereotype.Service;

import com.example.demo.customer.entity.Customer;
import com.example.demo.discountAgreement.entity.AgreementType;
import com.example.demo.discountAgreement.entity.DiscountAgreement;
import com.example.demo.dto.incoming.DTO_CartProduct;
import com.example.demo.interfaces.Discount.BuyXPayYsavings;
import com.example.demo.interfaces.Discount.IPreparedOrder;
import com.example.demo.interfaces.Discount.OrderSavings;
import com.example.demo.interfaces.Discount.ProductSavings;
import com.example.demo.interfaces.Order.OrderProduct;
import com.example.demo.order.entity.OOrder;
import com.example.demo.order.types.OrderProducts;
import com.example.demo.product.entity.Product;
import com.github.rkpunjal.sqlsafe.SqlSafeUtil;

@Service
public class Util {
 
    public void getTestOrder(){
        //Product hammasHarja = new Product("HammasHarja",5, "150");
        //Product koiranRuoka = new Product("KoiranRuoka",10, "150");

        //var orderProducts = new ArrayList<Product>();
        //orderProducts.add(hammasHarja);
        //orderProducts.add(koiranRuoka);

        //var calculatedOrderTotal = calculateProductTotal(orderProducts);

        //OOrder order = new OOrder();
        //order.setProducts(orderProducts);
        //order.setTotal(calculatedOrderTotal);

        //return order;
    }
    public Customer createTestCustomer_withDiscountAgreement(){
        Customer customer = new Customer();

        Product product = new Product("Testituote", 5);

        DiscountAgreement discountAgreement = new DiscountAgreement();
        discountAgreement.setAgreementType(AgreementType.PERCENTAGE_OFF_PRODUCT);
        discountAgreement.setProduct(product);
        discountAgreement.setPercentageOff(5);        
        
        discountAgreement.setProduct(product);

        return customer;

    }

    public void getTestDiscountAgreement(){
        //DiscountAgreement discountAgreement = new DiscountAgreement();
    }

    private double calculateProductTotal(List<Product> products){
        return products.stream().mapToDouble(Product::getPrice).sum();
    }

    public static <T> boolean isSafeFromSqlInject(T checkableObject) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException{     
        // Not tested fully but perhaps works :)

        Class<?> clazz = checkableObject.getClass();

        // Get all methods of the class
        Method[] methods = clazz.getMethods();
    
        // Iterate through methods to find getters
        for (Method method : methods) {
            // Check if the method is a getter by its name and return type
            if (Util.isGetter(method)) {
                // Invoke the getter method dynamically

                Object result = method.invoke(checkableObject);

                String stringvalue =String.valueOf(result);
                
                SqlSafeUtil.isSqlInjectionSafe(stringvalue);
            }
        }
        return true;
    }





    private static boolean isGetter(Method method) {
        return method.getName().startsWith("get") && method.getParameterCount() == 0
                && !method.getReturnType().equals(void.class);
    }

    public static ProductSavings calculateProductDiscount(DiscountAgreement discountAgreement, List<OrderProducts> orderProducts){
            var originalTotal = calculateOriginalOrderTotal(orderProducts);
        
            // calculate a discount off of single product and return the value
            var price = discountAgreement.getProduct().getPrice();
            var discountPerc = discountAgreement.getPercentageOff();

            // ensure discount is reasonable
            discountPerc = Math.max(0, Math.min(100, discountPerc));

            // calc the discount
            var discountedPrice = calculateDiscountForAmount(price,discountPerc);
            var totalSavings = price - discountedPrice;

            ProductSavings productSavings = new ProductSavings();
            productSavings.setTotalAfterSavings(originalTotal - totalSavings);
            productSavings.setTotalSavings(totalSavings);
            productSavings.setOriginalOrderTotal(calculateOriginalOrderTotal(orderProducts));
            productSavings.setDiscountAgreement(discountAgreement);
            return productSavings;
    }

    private static double calculateDiscountForAmount(double originalPrice, int discountPercentage){
        return originalPrice * (1 - (discountPercentage / 100.0));
    }

    private static double calculateOriginalOrderTotal(List<OrderProducts> orderProducts){
        return orderProducts.stream().mapToDouble((product)-> {
            return (product.getProduct().getPrice() * product.getQuantity());
        }).sum();

    }

    public static OrderSavings calculateOrderSavings(List<OrderProducts> orderProducts, DiscountAgreement discountAgreement){
                
        double orderTotal = calculateOriginalOrderTotal(orderProducts);

        OrderSavings orderSavings = new OrderSavings();

        var totalAfterDiscount = calculateDiscountForAmount(orderTotal, discountAgreement.getPercentageOff());

        orderSavings.setOriginalOrderTotal(orderTotal);
        orderSavings.setTotalAfterSavings(totalAfterDiscount);
        orderSavings.setTotalSavings((orderTotal-totalAfterDiscount));
        orderSavings.setDiscountAgreement(discountAgreement);
                            
        return orderSavings;
    }

    public static List<ProductSavings> calculateProductSavings(List<DiscountAgreement> customerDiscountAgreements, List<OrderProducts> orderProducts){
        // get order productsids to list
        List<Long> productsIds = orderProducts.stream().map(e->{
            return e.getProduct().getId();
        }).toList();
     
        List<ProductSavings> productSavingsList = new ArrayList();

        for (DiscountAgreement discountAgreement : customerDiscountAgreements) {
            if(AgreementType.PERCENTAGE_OFF_PRODUCT.equals(discountAgreement.getAgreementType())){
                if(productsIds.contains(discountAgreement.getProduct().getId())){
                    productSavingsList.add(Util.calculateProductDiscount(discountAgreement, orderProducts));
                }
            }
        }
        return productSavingsList;

    }

    public static List<OrderSavings> calculateOrderSavings(List<DiscountAgreement> customerDiscountAgreements, List<OrderProducts> orderProducts){
        List<OrderSavings> orderSavings = new ArrayList<OrderSavings>();
        for(DiscountAgreement discountAgreement : customerDiscountAgreements){
            if(AgreementType.PERCENTAGE_OFF_WHOLE_ORDER.equals(discountAgreement.getAgreementType())){
                orderSavings.add(Util.calculateOrderSavings(orderProducts,discountAgreement));
            }
        }
        return orderSavings;
    }


   public static List<BuyXPayYsavings> calculateBuyXPayYSavings(List<DiscountAgreement> customerDiscountAgreements, List<OrderProducts> orderProducts){
                    // Calculate the savings for this specific case
                    List<BuyXPayYsavings> savings = new ArrayList();

                    // calculate order total before any discounts
                    var originalOrderTotal = calculateOriginalOrderTotal(orderProducts);


                    for (DiscountAgreement discountAgreement : customerDiscountAgreements) {
                        
                        if(AgreementType.BUY_X_ONLY_PAY_Y.equals(discountAgreement.getAgreementType())){

                            var productsThatAreEligible = orderProducts.stream().filter(orderProduct-> {
                                if(orderProduct.getProduct().getId() == discountAgreement.getProduct().getId() && orderProduct.getQuantity() >= discountAgreement.getMustBuyAmount()){
                                    return true;
                                }
                                return false;
                            }).toList();
                

                            savings = productsThatAreEligible.stream().map(orderProduct->{
                            
                                BuyXPayYsavings buyXPayYsavings = new BuyXPayYsavings();
                                var howManyDiscounted = discountAgreement.getMustBuyAmount() - discountAgreement.getOnlyPayForAmount();
                                var totalSavings = orderProduct.getProduct().getPrice() * howManyDiscounted;
                                buyXPayYsavings.setTotalSavings(totalSavings);
                                buyXPayYsavings.setTotalAfterSavings(originalOrderTotal-totalSavings);
                                buyXPayYsavings.setDiscountAgreement(discountAgreement);
                                
                                return buyXPayYsavings;
                            }).toList();
                }
            }
            savings.forEach(e-> e.setOriginalOrderTotal(originalOrderTotal));

            return savings;

}
public static IPreparedOrder getTheBestDiscount(List<? extends IPreparedOrder> allSavings) {
    if (allSavings == null || allSavings.isEmpty()) {
        return null;
    }

    IPreparedOrder currentBestDiscount = allSavings.get(0);

    for (IPreparedOrder discount : allSavings) {
        if (discount.getTotalSavings() > currentBestDiscount.getTotalSavings()) {
            currentBestDiscount = discount;
        }
    }

    return currentBestDiscount;
} 
}
