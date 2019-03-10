package com.ilamcvel.service.impl;

import com.ilamcvel.data.Offer;
import com.ilamcvel.data.PercentOffer;
import com.ilamcvel.data.Product;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.ApplicationContext;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

public class ProductServiceImplTest {

    Offer applesOffer;
    Offer breadOffer;
    // creating a sub class to test instead of mock
    ProductServiceImpl productService = new ProductServiceImpl();

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
        MockitoAnnotations.initMocks(this);
    }

    @Mock
    ApplicationContext applicationContext;

    @Test
    public void getProduct() {
    }

    @Test
    public void createAvailableOfferMapTest() {
        Map<String, Offer> offerMap = new HashMap<>();
        offerMap.put("applesOffer", applesOffer);
        offerMap.put("breadOffer", breadOffer);
        when(applicationContext.getBeansOfType(Offer.class)).thenReturn(offerMap);

        productService.setApplicationContext(applicationContext);
        assertEquals(1, productService.getAvailableOffers(Product.Apples).size());
        assertTrue(BigDecimal.valueOf(0.10).compareTo(
                productService.getAvailableOffers(Product.Apples).get(0).getOfferPrice()) == 0);
    }
}