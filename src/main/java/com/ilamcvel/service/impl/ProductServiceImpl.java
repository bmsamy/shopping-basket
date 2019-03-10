package com.ilamcvel.service.impl;

import com.ilamcvel.data.Offer;
import com.ilamcvel.data.Product;
import com.ilamcvel.service.ProductService;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Product service is applicationContextaware to know the configured offers for the products
 */
@Component
public class ProductServiceImpl implements ProductService, ApplicationContextAware {

    Map<Product, List<Offer>> availableOfferMap;

    @Override
    public List<Offer> getAvailableOffers(Product product) {
        return availableOfferMap.get(product);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Map<String, Offer> availableOffers = applicationContext.getBeansOfType(Offer.class);
        createAvailableOfferMap(availableOffers);
    }

    private void createAvailableOfferMap(Map<String, Offer> availableOffers) {
        this.availableOfferMap = availableOffers.entrySet().stream().
                collect(Collectors.groupingBy(e -> e.getValue().getOfferProduct(),
                        Collectors.mapping(entry -> entry.getValue(), Collectors.toList())));
    }
}
