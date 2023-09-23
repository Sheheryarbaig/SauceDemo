package Locators.EnumFactory;

public enum Product {
    XPATH_FILTER_AES("//select[@class='product_sort_container']/option[@value='az']"),
    XPATH_FILTER_DES("//select[@class='product_sort_container']/option[@value='za']"),
    XPATH_PRICE_LOW_TO_HIGH("//select[@class='product_sort_container']/option[@value='lohi']"),
    XPATH_PRICE_HIGH_TO_LOW("//select[@class='product_sort_container']/option[@value='hilo']"),
    XPATH_JACKET("//*[@id=\"add-to-cart-sauce-labs-fleece-jacket\"]"),
    XPATH_REMOVE_JACKET("//*[@id=\"remove-sauce-labs-fleece-jacket\"]"),
    XPATH_BAGPACK("//*[@id=\"add-to-cart-sauce-labs-backpack\"]"),
    XPATH_SHOPPING_CART("//*[@id=\"shopping_cart_container\"]"),
    XPATH_CHECKOUT_BTN("//*[@id=\"checkout\"]"),
    XPATH_CONTINUE_BTN("//*[@id=\"continue\"]"),
    XPATH_FINISH_BTN("//*[@id=\"finish\"]"),
    XPATH_LOGOUT_BTN("//*[@id=\"logout_sidebar_link\"]"),
    XPATH_BACKHOME_BTN("//*[@id=\"back-to-products\"]"),
    XPATH_NAV_BAR("//*[@id=\"react-burger-menu-btn\"]"),
    XPATH_FIRST_NAME("//*[@id=\"first-name\"]"),
    XPATH_LAST_NAME("//*[@id=\"last-name\"]"),
    XPATH_POSTAL_CODE("//*[@id=\"postal-code\"]"),
    XPATH_TSHIRT("//*[@id=\"add-to-cart-sauce-labs-bolt-t-shirt\"]"),
    XPATH_DOTS_LOADER("//*[@attributeName='transform']");
    private String pageVariables;
    private Product(String pageVariables) {

        this.pageVariables = pageVariables;
    }

    public String getValue() {
        return this.pageVariables;
    }

    public String getPageVariables(){return this.pageVariables;}
}
