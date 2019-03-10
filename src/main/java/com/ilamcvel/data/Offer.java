package com.ilamcvel.data;

import java.math.BigDecimal;

/**
 * Offer interface that has information about the offer.
 */
public interface Offer {

    public boolean isOfferApplicable(Basket basket);

    public BigDecimal getOfferPrice();

    public Product getOfferProduct();

    public String getDiscountPercentage();

}
