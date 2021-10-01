Feature: feature1

  Scenario: Attempt to register a weather station without an API key

    Given The post request does not have an API key
    When The post request is made
    Then The HTTP response code should be 401
    And The response message text should read correctly

  Scenario: Attempt to register two stations with given details

    Given Details for two stations
    When The post request for the two stations is made
    Then The HTTP response code for the two stations should be 201

