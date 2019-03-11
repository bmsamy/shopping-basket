package com.ilamcvel.data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Class representing basket of items selected by customer.
 */
public class Basket {

    List<Product> selectedProducts;
    Map<Product, Long> itemsCountMapForOffer;

    public Basket(List<Product> selectedProducts) {
        this.selectedProducts = selectedProducts;
        this.itemsCountMapForOffer = selectedProducts.stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
    }

    public List<Product> getSelectedProducts() {
        return selectedProducts;
    }

    /**
     * creates a map of product and the number of items purchased.
     *
     * @return
     */
    public Map<Product, Long> getItemsCountMapForOffer() {
        return itemsCountMapForOffer;
    }

}
