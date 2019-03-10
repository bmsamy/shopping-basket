package com.ilamcvel.data;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Class representing basket of items selected by customer.
 */
public class Basket {

    List<Product> selectedProducts;

    public Basket(List<Product> selectedProducts) {
        this.selectedProducts = selectedProducts;
    }

    public List<Product> getSelectedProducts() {
        return selectedProducts;
    }

    /**
     * creates a map of product and the number of items purchased.
     * @return
     */
    public Map<Product, Long> getSelectedProductMap() {
        return selectedProducts.stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
    }

}
