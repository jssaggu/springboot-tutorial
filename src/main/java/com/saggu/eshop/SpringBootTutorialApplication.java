package com.saggu.eshop;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class SpringBootTutorialApplication {

    private static final Logger log = LoggerFactory.getLogger(SpringBootTutorialApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(SpringBootTutorialApplication.class, args);
        for (int i = 0; i < 2; i++) {
            log.info("This is just a msg: {}", i);
        }
    }
}
