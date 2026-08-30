package com.saggu.eshop.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.util.Map;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Profile("dev")
@RestController
@RequestMapping("v1/")
public class SystemInfoPrinter {
    @GetMapping(value = "/system-info", produces = APPLICATION_JSON_VALUE)
    public Map<String, Object> systemInfo() {
        Runtime runtime = Runtime.getRuntime();
        return Map.of(
                "Max Memory", runtime.maxMemory(),
                "Total Memory", runtime.totalMemory(),
                "Free Memory", runtime.freeMemory());
    }
}
