package com.saggu.eshop.config;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;


@Slf4j
@Configuration
@ConfigurationProperties(prefix = "jss")
@NoArgsConstructor
@Getter
@Setter
@ToString
public class JssProperties {

    /**
     * This property should be used to name the request.
     */
    private String name;

    /**
     * This property should be used to name the request.
     * Default value=I'm Default
     */
    private String nameWithDefault = "I am default";

    /**
     * This property should be used to specify number.
     */
    private int number;

    /**
     * This property should be used to specify number and has a default value.
     */
    private int numberWithDefault = 10;

    /**
     * This property should be used to define a list.
     */
    private List<String> cities;



}
