package com.ilamcvel.service.impl;

import com.ilamcvel.data.*;
import com.ilamcvel.service.ProductService;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class ShoppingServiceImplTest {

    private Offer applesOffer;
    private Offer breadOffer;
    private ShoppingServiceImpl shoppingService;
    private ProductService productService;

    @Before
    public void init() {
        applesOffer = new PercentOffer.OfferBuilder().with(b -> {
            b.offerProduct = Product.Apples;
            b.discount = BigDecimal.valueOf(0.10);
            b.expiryDate = LocalDate.now().plusDays(7);
        }).build();

        breadOffer = new PercentOffer.OfferBuilder().with(b -> {
            b.offerProduct = Product.Bread;
            b.discount = BigDecimal.valueOf(0.50);
            b.comboOfferMap = new HashMap<Product, Integer>() {{
                put(Product.Soup, 2);
            }};
        }).build();
        productService = new ProductServiceImpl() {
            @Override
            public List<Offer> getAvailableOffers(Product product) {
                this.availableOfferMap = new HashMap<Product, List<Offer>>() {{
                    put(Product.Apples, new ArrayList<Offer>() {{
                        add(applesOffer);
                    }});
                    put(Product.Bread, new ArrayList<Offer>() {{
                        add(breadOffer);
                    }});
                }};
                return this.availableOfferMap.get(product);
            }
        };
        shoppingService = new ShoppingServiceImpl();
        shoppingService.productService = productService;
    }

    @Test
    public void calculateBasketPriceNoOffer() {
        List<Product> selectedProducts = new ArrayList<Product>() {{
            add(Product.Apples);
            add(Product.Milk);
            add(Product.Bread);
        }};
        Basket basket = new Basket(selectedProducts);
        BasketPriceTotal basketPriceTotal = shoppingService.calculateBasketPrice(basket);
        assertTrue(BigDecimal.valueOf(3.1).compareTo(basketPriceTotal.getSubTotal()) == 0);
        assertTrue(BigDecimal.valueOf(3).compareTo(basketPriceTotal.getTotal()) == 0);
    }

    @Test
    public void calculateBasketPriceMultiBuy() {
        List<Product> selectedProducts = new ArrayList<Product>() {{
            add(Product.Soup);
            add(Product.Soup);
            add(Product.Bread);
            add(Product.Soup);
            add(Product.Soup);
        }};
        Basket basket = new Basket(selectedProducts);
        BasketPriceTotal basketPriceTotal = shoppingService.calculateBasketPrice(basket);
        assertTrue(BigDecimal.valueOf(3.4).compareTo(basketPriceTotal.getSubTotal()) == 0);
        assertTrue(BigDecimal.valueOf(3).compareTo(basketPriceTotal.getTotal()) == 0);
    }

    @Test
    public void calculateBasketPriceMultipleOffers() {
        List<Product> selectedProducts = new ArrayList<Product>() {{
            add(Product.Soup);
            add(Product.Soup);
            add(Product.Bread);
            add(Product.Apples);
        }};
        Basket basket = new Basket(selectedProducts);
        BasketPriceTotal basketPriceTotal = shoppingService.calculateBasketPrice(basket);
        assertTrue(BigDecimal.valueOf(3.1).compareTo(basketPriceTotal.getSubTotal()) == 0);
        assertTrue(BigDecimal.valueOf(2.6).compareTo(basketPriceTotal.getTotal()) == 0);
    }

    @Test
    public void calculateBasketPriceMultiBuyMultipleBread() {
        List<Product> selectedProducts = new ArrayList<Product>() {{
            add(Product.Soup);
            add(Product.Soup);
            add(Product.Bread);
            add(Product.Bread);
            add(Product.Soup);
            add(Product.Soup);
        }};
        Basket basket = new Basket(selectedProducts);
        BasketPriceTotal basketPriceTotal = shoppingService.calculateBasketPrice(basket);
        assertTrue(BigDecimal.valueOf(4.2).compareTo(basketPriceTotal.getSubTotal()) == 0);
        assertTrue(BigDecimal.valueOf(3.4).compareTo(basketPriceTotal.getTotal()) == 0);
    }
}
