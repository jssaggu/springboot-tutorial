package com.saggu.eshop.controller;

import com.saggu.eshop.config.JssProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

//@Profile("dev")
@RestController
@RequestMapping("v1/")
public class SystemInfoPrinter {

    private final JssProperties jssProperties;

    public SystemInfoPrinter(JssProperties jssProperties) {
        this.jssProperties = jssProperties;

        System.out.println("JSS Properties -----------------");
        System.out.println("JSS Properties: " + jssProperties);
        System.out.println("JSS Properties --------X--------");
    }

    @GetMapping(value = "/system-info", produces = APPLICATION_JSON_VALUE)
    public Map<String, Object> systemInfo() {
        Map<String, Object> map = new HashMap<>();
        Runtime runtime = Runtime.getRuntime();
        map.put("Max Memory", runtime.maxMemory());
        map.put("Total Memory", runtime.totalMemory());
        map.put("Free Memory", runtime.freeMemory());
        return map;
    }
}
