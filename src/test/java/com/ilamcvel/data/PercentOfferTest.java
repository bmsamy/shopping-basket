package com.ilamcvel.data;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;

public class PercentOfferTest {

    PercentOffer applesOffer;
    PercentOffer breadOffer;


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
    }

    @Test
    public void isOfferApplicableMethod() {
        Basket basket = new Basket(new ArrayList<Product>() {{
            add(Product.Apples);
        }});

        assertTrue(applesOffer.isOfferApplicable(basket));
    }

    @Test
    public void isOfferApplicableMethodExpiryNull() {
        applesOffer = new PercentOffer.OfferBuilder().with(b -> {
            b.offerProduct = Product.Apples;
            b.discount = BigDecimal.valueOf(0.10);
        }).build();

        Basket basket = new Basket(new ArrayList<Product>() {{
            add(Product.Apples);
        }});

        assertTrue(applesOffer.isOfferApplicable(basket));
    }

    @Test
    public void isOfferApplicableMethodAfterExpiry() {
        applesOffer = new PercentOffer.OfferBuilder().with(b -> {
            b.offerProduct = Product.Apples;
            b.discount = BigDecimal.valueOf(0.10);
            b.expiryDate = LocalDate.now().minusDays(1);
        }).build();

        Basket basket = new Basket(new ArrayList<Product>() {{
            add(Product.Apples);
        }});

        assertFalse(applesOffer.isOfferApplicable(basket));
    }

    @Test
    public void isOfferApplicableMethodComboMapTest() {
        Basket basket = new Basket(new ArrayList<Product>() {{
            add(Product.Bread);
        }});

        assertFalse(breadOffer.isOfferApplicable(basket));
    }

    @Test
    public void isOfferApplicableMethodComboMapSuccessTest() {
        Basket basket = new Basket(new ArrayList<Product>() {{
            add(Product.Bread);
            add(Product.Soup);
            add(Product.Soup);
            add(Product.Bread);
            add(Product.Soup);
        }});

        assertTrue(breadOffer.isOfferApplicable(basket));
    }
}
