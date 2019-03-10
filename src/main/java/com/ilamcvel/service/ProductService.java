package com.ilamcvel.service;

import com.ilamcvel.data.Offer;
import com.ilamcvel.data.Product;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service to handle product related flow.
 */
public interface ProductService {
    /**
     * Static method that gives Product based on the name.
     */
    public static Optional<Product> getProduct(String productName) {
        return Arrays.stream(Product.values()).filter(p -> p.name().equalsIgnoreCase(productName)).findFirst();
    }

    /**
     * Static method that displays the available list of products
     *
     * @return
     */
    public static String getProductsNames() {
        return Arrays.stream(Product.values()).map(p -> p.name()).collect(Collectors.joining(","));
    }

    /**
     * Method that lists the offers available for Product.
     *
     * @param product
     * @return
     */
    List<Offer> getAvailableOffers(Product product);
}
