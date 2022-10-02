package fr.uge.jee.annotation.onlineshop;

import java.util.Set;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan
@PropertySource("classpath:onlineshop.properties")
public class OnlineShopConfig {
//
//    @Bean
//    public StandardDelivery standardDelivery() {
//        return new StandardDelivery(999);
//    }
//
//    @Bean
//    public ReturnInsurance returnInsurance() {
//        return new ReturnInsurance(false);
//    }
//
//    @Bean
//    public TheftInsurance theftInsurance() {
//        return new TheftInsurance();
//    }
//
//    @Bean
//    public OnlineShop onlineShop() {
//        return new OnlineShop("AhMaZone", Set.of(standardDelivery()), Set.of(returnInsurance(), theftInsurance()));
//    }
}
