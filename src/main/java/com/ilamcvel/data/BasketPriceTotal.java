package com.ilamcvel.data;

import java.math.BigDecimal;
import java.util.Map;

/**
 * Class that holds the calculated results.
 */
public class BasketPriceTotal {

    private BigDecimal subTotal;
    private Map<Offer, BigDecimal> OfferTotal;
    private BigDecimal total;

    public BigDecimal getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(BigDecimal subTotal) {
        this.subTotal = subTotal;
    }

    public Map<Offer, BigDecimal> getOfferTotal() {
        return OfferTotal;
    }

    public void setOfferTotal(Map<Offer, BigDecimal> offerTotal) {
        OfferTotal = offerTotal;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "BasketPriceTotal{" +
                "subTotal=" + subTotal +
                ", OfferTotal=" + OfferTotal +
                ", total=" + total +
                '}';
    }
}
