package com.ilamcvel.main;

import com.ilamcvel.data.Basket;
import com.ilamcvel.data.Product;
import com.ilamcvel.service.DisplayService;
import com.ilamcvel.service.ProductService;
import com.ilamcvel.service.ShoppingService;
import com.ilamcvel.util.Constants;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Arrays;
import java.util.List;

/**
 * Main class that handles the code flow.
 * Loads Spring context with config info from AppConfig class.
 * If the items are invalid they are ignored and valid items are added to basket
 * If the basket is empty or is the input args is empty Exception is raised.
 * Shopping service is called to calculate total and display result.
 */
public class Application {

    public static void main(String[] args) {
        ApplicationContext context
                = new AnnotationConfigApplicationContext(AppConfig.class);

        ShoppingService shoppingService = (ShoppingService) context.getBean(ShoppingService.class);
        if (null == args || args.length == 0) {
            throwError(context);
        }

        List<Product> basketItems = shoppingService.createBasketItems(Arrays.asList(args));
        if(null == basketItems|| basketItems.isEmpty()){
            throwError(context);
        }
        Basket basket = new Basket(basketItems);
        shoppingService.displayResult(shoppingService.calculateBasketPrice(basket));
    }

    /**
     * method to throw error and stop execution when there are no valid items to proceed
     * @param context
     */
    private static void throwError(ApplicationContext context) {
        DisplayService displayService = (DisplayService) context.getBean(DisplayService.class);
        throw new IllegalArgumentException(displayService.getMessage(Constants.USAGE_MESSAGE, ProductService.getProductsNames()));
    }
}
