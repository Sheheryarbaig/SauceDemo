Feature: Login And Checkout

  Background:

  @AInValidLogin
  Scenario: Login Application with incorrect Username and Password

    When User Navigates to "SauceDemo" URL
    Then User Click on "Login" Button on "Login" Page
    And User Enters "Invalid Username" on "Username" Field on "Login" Page
    And User Enters "Invalid Password" on "Password" Field on "Login" Page
    Then User Click on "Login" Button on "Login" Page

  @Checkout
  Scenario: User completes the flow for product checkout with filters
    When User Navigates to "SauceDemo" URL
    Then User Click on "Login" Button on "Login" Page
    And User Enters "Username" on "Username" Field on "Login" Page
    And User Enters "Password" on "Password" Field on "Login" Page
    Then User Click on "Login" Button on "Login" Page
   And User filters "Ascending Order" on "Product" Page
   And User filters "Descending Order" on "Product" Page
   And User filters "Low To High" on "Product" Page
   And User filters "High To Low" on "Product" Page
    And User adds products to their carts on "Product" Page
    And User Navigates to Shopping Cart on "Product" Page
    And User removes product from their cart on "Product" Page
    And User checkouts on "Product" Page
    And User fills information and press continue on "Product" Page
    And User clicks on BackHomePage button on "Product" Page
    And User Logout on "Product" Page