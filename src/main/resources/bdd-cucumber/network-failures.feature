@toxi
Feature: Network Failures Testing
  Service should be able to handle network failures

  Scenario Outline: Service should wait for defined period of time and after that disconnect
    Given server health endpoint "http://localhost:9080/actuator/health"
    And network latency is "<numberOfSeconds>"
    When endpoint is called
    Then it should return "<status>"

    Examples:
      | status        | numberOfSeconds |
      | OK            |1                |
      | OK            |2                |
      | Read timed out|4                |
      | Read timed out|5                |