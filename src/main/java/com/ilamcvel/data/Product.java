package com.ilamcvel.data;

import java.math.BigDecimal;

/**
 * Enum that represents Product Type in the system.
 * TODO Load product information into a xml and read it from that to a java class instead of Enum
 */
public enum Product {
    Apples(BigDecimal.valueOf(1.0)), Milk(BigDecimal.valueOf(1.30)), Soup(BigDecimal.valueOf(0.65)), Bread(BigDecimal.valueOf(0.80));

    private BigDecimal price;

    public BigDecimal getPrice() {
        return price;
    }

    Product(BigDecimal price) {
        this.price = price;
    }


}
