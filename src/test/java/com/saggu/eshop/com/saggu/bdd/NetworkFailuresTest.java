package com.saggu.eshop.com.saggu.bdd;

import static java.lang.Integer.parseInt;

import eu.rekawek.toxiproxy.Proxy;
import eu.rekawek.toxiproxy.ToxiproxyClient;
import eu.rekawek.toxiproxy.model.ToxicDirection;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import java.io.IOException;
import java.time.Duration;
import org.junit.Assert;
import org.junit.runner.RunWith;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@RunWith(Cucumber.class)
@CucumberOptions(features = "classpath:bdd-cucumber", tags = "@toxi")
public class NetworkFailuresTest {

    private String healthEndpoint = "http://localhost:8080/actuator";
    String actual = "";

    RestTemplate restTemplate;

    ResponseEntity<Object> responseEntity;

    @Given("server health endpoint {string}")
    public void server_health_endpoint(String healthEndpoint) {
        this.healthEndpoint = healthEndpoint;
    }

    @And("network latency is {string}")
    public void networkLatencyIs(String latency) throws IOException {
        ToxiproxyClient client = new ToxiproxyClient("127.0.0.1", 8474);
        Proxy proxy = client.getProxy("Springboot8080");
        if (!proxy.toxics().getAll().isEmpty()) {
            proxy.toxics().get("latency").remove();
        }
        proxy.toxics().latency("latency", ToxicDirection.DOWNSTREAM, parseInt(latency) * 1000);

        restTemplate = new RestTemplateBuilder().setReadTimeout(Duration.ofSeconds(3)).build();
    }

    @When("endpoint is called")
    public void endpoint_is_called() {
        try {
            responseEntity = restTemplate.getForEntity(healthEndpoint, Object.class);
            actual = ((HttpStatus) responseEntity.getStatusCode()).name();
        } catch (Exception e) {
            actual = e.getCause().getMessage();
        }
    }

    @Then("it should return {string}")
    public void itShouldReturnWhenTimeExceedsSeconds(String expected) {
        Assert.assertEquals("Return should be equal", expected, actual);
    }
}
