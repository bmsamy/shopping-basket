package com.ilamcvel.main;

import com.ilamcvel.data.Offer;
import com.ilamcvel.data.PercentOffer;
import com.ilamcvel.data.Product;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;

/**
 * Class that provides all the configuration information to Spring Application Context.
 */
@Configuration
@ComponentScan(basePackages = "com.ilamcvel")
public class AppConfig {

    @Bean(name = "applesOffer")
    public Offer getApplesOffer() {
        return new PercentOffer.OfferBuilder().with(b -> {
            b.offerProduct = Product.Apples;
            b.discount = BigDecimal.valueOf(0.10);
            b.expiryDate = LocalDate.now().plusDays(7);
        }).build();
    }

    @Bean(name = "breadOffer")
    public Offer getBreadOffer() {
        return new PercentOffer.OfferBuilder().with(b -> {
            b.offerProduct = Product.Bread;
            b.discount = BigDecimal.valueOf(0.50);
            b.comboOfferMap = new HashMap<Product,Integer>(){{put(Product.Soup,2);}};
        }).build();
    }

    @Bean
    public MessageSource messageSource () {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("resources");
        return messageSource;
    }
}
