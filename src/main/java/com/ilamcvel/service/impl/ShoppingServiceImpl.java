package com.ilamcvel.service.impl;

import com.ilamcvel.data.Basket;
import com.ilamcvel.data.BasketPriceTotal;
import com.ilamcvel.data.Offer;
import com.ilamcvel.data.Product;
import com.ilamcvel.service.DisplayService;
import com.ilamcvel.service.ProductService;
import com.ilamcvel.service.ShoppingService;
import com.ilamcvel.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Class that implements Shopping interface.
 * Calculates price of items
 * Displays results to console
 */
@Component
public class ShoppingServiceImpl implements ShoppingService {

    @Autowired
    protected ProductService productService;

    @Autowired
    protected DisplayService displayService;

    /**
     * Calculates the sub total from the normal price.
     * Checks the available offers for each product and if the offer is applicable for basket then it adds it to OfferTotal.
     * Calculates the total after discouting the total offer.
     *
     * @param basket
     * @return
     */
    @Override
    public BasketPriceTotal calculateBasketPrice(Basket basket) {
        BasketPriceTotal basketPriceTotal = new BasketPriceTotal();
        basketPriceTotal.setSubTotal(basket.getSelectedProducts().stream().map(p ->
                p.getPrice()).reduce(BigDecimal.ZERO, (p1, p2) -> p1.add(p2)));

        Map<Offer, BigDecimal> offerTotal = new HashMap<>();
        for (Product product : basket.getSelectedProducts()) {
            List<Offer> offers = productService.getAvailableOffers(product);
            if (null != offers) {
                for (Offer offer : offers) {
                    if (offer.isOfferApplicable(basket)) {
                        offerTotal.merge(offer, offer.getOfferPrice(), (v1, v2) -> v1.add(v2));
                    }
                }
            }
        }
        basketPriceTotal.setOfferTotal(offerTotal);

        basketPriceTotal.setTotal(basketPriceTotal.getSubTotal().subtract(
                basketPriceTotal.getOfferTotal().values().stream().reduce(BigDecimal.ZERO, (p1, p2) -> p1.add(p2))));

        return basketPriceTotal;
    }

    @Override
    public void displayResult(BasketPriceTotal basketPriceTotal) {
        System.out.println(displayService.getMessage(Constants.SUBTOTAL, formatCurrency(basketPriceTotal.getSubTotal())));
        if (basketPriceTotal.getOfferTotal().isEmpty()) {
            System.out.println(displayService.getMessage(Constants.NO_OFFERS_AVAILABLE));
        } else {
            basketPriceTotal.getOfferTotal().entrySet().forEach(
                    e -> {
                        System.out.println(displayService.getMessage(Constants.OFFER_DESC, e.getKey().getOfferProduct().name(),
                                e.getKey().getDiscountPercentage(), formatCurrency(e.getValue())));
                    });
        }
        System.out.println(displayService.getMessage(Constants.TOTAL, formatCurrency(basketPriceTotal.getTotal())));
    }

    @Override
    public List<Product> createBasketItems(List<String> shoppingItems) {
        return shoppingItems.stream().map(ProductService::getProduct).
                filter(Optional::isPresent).map(p -> p.get()).collect(Collectors.toList());
    }

    private String formatCurrency(BigDecimal value) {
        return NumberFormat.getCurrencyInstance().format(value);
    }
}
