package com.saggu.eshop.configuration;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@ConfigurationProperties(prefix = "jss")
@NoArgsConstructor
@Getter
@Setter
@ToString
public class EshopProperties {

    /**
     * Name on the order.
     */
    private String name;

}
