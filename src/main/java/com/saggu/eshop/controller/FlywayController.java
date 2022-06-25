package com.saggu.eshop.controller;

import org.flywaydb.core.Flyway;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("flyway/v1")
public class FlywayController {
    @GetMapping(value = "/migrate", produces = APPLICATION_JSON_VALUE)
    public Map<String, Object> migrate() {
        Map<String, Object> map = new HashMap<>();
        try {
            map.put("Migration Started At", new Date().toString());

            // Create the Flyway instance and point it to the database
            Flyway flyway = Flyway
                    .configure()
                    .dataSource("jdbc:postgresql://localhost:5432/postgres", "admin", "admin")
                    .load();

            // Start the migration
            flyway.migrate();

        } catch (Exception e) {
            e.printStackTrace();
            map.put("Exception", e.getMessage());
        }

        map.put("Migration finished At", new Date().toString());
        return map;

    }
}
