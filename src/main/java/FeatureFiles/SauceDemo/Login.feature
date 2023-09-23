Feature: Login SauceDemo

  Background:


  @Login1
  Scenario: Login Application with incorrect Username and Password

    When User Navigates to "SauceDemo" URL
    Then User Click on "Login" Button on "Login" Page
    And User Enters "Invalid Username" on "Username" Field on "Login" Page
    And User Enters "Invalid Password" on "Password" Field on "Login" Page
    Then User Click on "Login" Button on "Login" Page

  @Login2
  Scenario: Login Application with correct Username and Password

    When User Navigates to "SauceDemo" URL
    Then User Click on "Login" Button on "Login" Page
    And User Enters "Username" on "Username" Field on "Login" Page
    And User Enters "Password" on "Password" Field on "Login" Page
    Then User Click on "Login" Button on "Login" Page
