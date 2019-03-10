package com.ilamcvel.service;

import com.ilamcvel.data.Basket;
import com.ilamcvel.data.BasketPriceTotal;
import com.ilamcvel.data.Product;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Service to handle shopping flow.
 */
public interface ShoppingService {
    /**
     * Creates a list of products from list of String.
     * Ignores invalid items
     *
     * @param shoppingItems
     * @return
     */
    List<Product> createBasketItems(List<String> shoppingItems);

    /**
     * Method to calculate the price of basket of items
     * return BasketPriceTotal object which has the split up of cost.
     *
     * @param basket
     * @return
     */
    BasketPriceTotal calculateBasketPrice(Basket basket);

    /**
     * Method that displays total result to console
     *
     * @param basketPriceTotal
     */
    void displayResult(BasketPriceTotal basketPriceTotal);

}
