package com.saggu.eshop;

import org.springframework.boot.health.contributor.Health;
import org.springframework.boot.health.contributor.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
public class JSSHealthIndicator implements HealthIndicator {

    @Override
    public Health health() {
        Health health = Health.up().build();
        // System.out.println("JSS Health: " + health);
        return health;
    }
}
