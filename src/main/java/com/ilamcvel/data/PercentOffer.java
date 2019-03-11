package com.ilamcvel.data;

import com.ilamcvel.service.DisplayService;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

/**
 * Class that implements Offer and calculates discounts based on percentage.
 * This class has a private construtor and can be created by OfferBuilder class.
 */
public class PercentOffer implements Offer {

    private Product offerProduct;
    private BigDecimal discount;
    private LocalDate expiryDate;
    private Map<Product, Integer> comboOfferMap = new HashMap<>();

    @Autowired
    private DisplayService displayService;

    /**
     * Private constructor and this is invoked by the static class OfferBuilder
     *
     * @param builder
     */
    private PercentOffer(OfferBuilder builder) {
        this.offerProduct = builder.offerProduct;
        this.discount = builder.discount;
        this.expiryDate = builder.expiryDate;
        this.comboOfferMap = builder.comboOfferMap;
    }

    @Override
    public boolean isOfferApplicable(Basket basket) {
        return isValidOffer() && isComboOfferApplicable(basket);
    }

    /**
     * If the offer is multi buy then checks if the basket has the required number of items.
     * The items accounted for an offer are then removed.
     */
    private boolean isComboOfferApplicable(Basket basket) {
        if (comboOfferMap == null || comboOfferMap.isEmpty()) {
            return true;
        }

        boolean hasRequiredComboItems = comboOfferMap.entrySet().stream()
                .allMatch(e -> basket.getItemsCountMapForOffer().containsKey(e.getKey())
                        && basket.getItemsCountMapForOffer().get(e.getKey()) >= e.getValue());
        /**
         * Remove the item from offer count map so that we dont discount the same item again
         * eg. Soup,Soup,Bread,Bread
         */
        if (hasRequiredComboItems) {
            comboOfferMap.entrySet().stream()
                    .filter(e -> basket.getItemsCountMapForOffer().containsKey(e.getKey())
                            && basket.getItemsCountMapForOffer().get(e.getKey()) >= e.getValue())
                    .forEach(e -> {
                        basket.getItemsCountMapForOffer().compute(e.getKey(), (p, v) -> v - e.getValue());
                    });
        }
        return hasRequiredComboItems;
    }

    /**
     * If the offer has a expiry date then check if the offer is still valid.
     */
    private boolean isValidOffer() {
        return (null == expiryDate || LocalDate.now().isBefore(expiryDate));
    }

    @Override
    public BigDecimal getOfferPrice() {
        return getOfferProduct().getPrice().multiply(discount);
    }

    @Override
    public Product getOfferProduct() {
        return offerProduct;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    @Override
    public String getDiscountPercentage() {
        DecimalFormat format = new DecimalFormat("#.##");
        return format.format(getDiscount().multiply(BigDecimal.valueOf(100)));
    }

    /**
     * Class used to create PercentOffer class.
     * As this class has different combination of input data. Builder is used to create this object.
     */
    public static class OfferBuilder {
        public Product offerProduct;
        public BigDecimal discount;
        public LocalDate expiryDate;
        public Map<Product, Integer> comboOfferMap = new HashMap<>();

        public OfferBuilder with(Consumer<OfferBuilder> builder) {
            builder.accept(this);
            return this;
        }

        public PercentOffer build() {
            return new PercentOffer(this);
        }
    }

    @Override
    public String toString() {
        return "PercentOffer{" +
                "offerProduct=" + offerProduct +
                ", discount=" + discount +
                ", expiryDate=" + expiryDate +
                ", comboOfferMap=" + comboOfferMap +
                '}';
    }
}
