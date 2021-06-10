package br.com.zupacademy.armando.propostamicroservice.config.healthcheck;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

@Component
public class AccountsClientCheck implements HealthIndicator {

    @Value("${service.accounts.port-only}")
    private int port;

    @Value("${service.accounts}")
    private String serviceAccountsCompleteUrl;

    @Value("${service.accounts.url-only}")
    private String serviceAccountsUrlOnly;

    @Override
    public Health health() {
        Map<String, Object> details = new HashMap<>();
        details.put("descrição", "Api externa de cartões");
        details.put("endereço", serviceAccountsCompleteUrl);

        try (Socket socket = new Socket(new java.net.URL(serviceAccountsUrlOnly).getHost(), port)) {
        } catch (Exception e) {
            return Health.down().withDetails(details).build();
        }
        return Health.up().withDetails(details).build();
    }

}
