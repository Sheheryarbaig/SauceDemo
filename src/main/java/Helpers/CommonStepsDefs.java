package Helpers;

import Helpers.SauceDemo.Login;
import Setup.Initialization.*;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import io.cucumber.java.ParameterType;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Assert;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;
import java.util.Properties;


public class CommonStepsDefs extends webdriverInitialization {

    CommonPageFactory commonPage;
    commonActions action;
    private ExtentReports extentReport;
    private ExtentTest extentTest;
    Statement stmt;
    Login login = new Login();

    public CommonStepsDefs() throws Exception {
        commonPage = new CommonPageFactory();
        action= new commonActions();
    }

    @Then("User Navigates to {string} URL")
    public void userNavigatesToURL(String url) throws Exception {
        url = commonPage.getpropertyName(url);
        url = new PropertyLoaderFactory().getPropertyFile(HarnessVariables.runPropFile).getProperty(url);
        ServiceFactory.getDriver().get(url);
    }
    @Then("User filters {string} on {string} Page")
    public void userfilters(String order,String locatorPageName) throws Exception {
        action.clickBtn(order,locatorPageName);
    }
   @Then("User adds products to their carts on {string} Page")
    public void userAddsProducts(String locatorPageName) throws Exception {
        action.clickBtn("Jacket",locatorPageName);
        action.clickBtn("Backpack",locatorPageName);
        action.clickBtn("T-Shirt",locatorPageName);
    }
    @Then("User Navigates to Shopping Cart on {string} Page")
    public void userNavCart(String locatorPageName) throws Exception {
        action.clickBtn("Shopping Cart",locatorPageName);
    }
    @Then("User removes product from their cart on {string} Page")
    public void userRemoveProduct(String locatorPageName) throws Exception {
        action.clickBtn("Jacket Remove",locatorPageName);
    }
 @Then("User checkouts on {string} Page")
    public void userCheckout(String locatorPageName) throws Exception {
        action.clickBtn("Checkout Btn",locatorPageName);
    }
    @Then("User fills information and press continue on {string} Page")
    public void userInformation(String locatorPageName) throws Exception {
       action.enterText("First Name",locatorPageName,"First Name",locatorPageName);
       action.enterText("Last Name",locatorPageName,"Last Name",locatorPageName);
       action.enterText("Postal Code",locatorPageName,"Postal Code",locatorPageName);
        action.clickBtn("Continue Btn",locatorPageName);
        action.clickBtn("Finish Btn",locatorPageName);
    }
    @Then("User clicks on BackHomePage button on {string} Page")
    public void NavHomePage(String locatorPageName) throws Exception {
        action.clickBtn("Backhome Btn",locatorPageName);
    }
    @Then("User Logout on {string} Page")
    public void userLogouts(String locatorPageName) throws Exception {
        action.clickBtn("Nav Bar",locatorPageName);
        action.clickBtn("Logout Btn",locatorPageName);
    }

    @Then("User Validates {string} Title")
    public void userValidatesTitle(String expectedTitle) throws Exception {
        expectedTitle = commonPage.getpropertyName(expectedTitle);
        expectedTitle = new PropertyLoaderFactory().getPropertyFile(HarnessVariables.runPropFile).getProperty(expectedTitle);
        String actualTitle = ServiceFactory.getDriver().getTitle();
        Assert.assertEquals(expectedTitle, actualTitle);
    }

    @Then("User Waits")
    public void userwait() throws Exception {
        WaitFactory.staticWait(200);
    }

    @When("User Enters {string} on {string} Field on {string} Page")
    public void user_enters_on_field_on_page(String testData, String locator, String screenName) throws Exception {
        screenName = commonPage.removeSpaces(screenName);
        if(testData.equals("Application ID")) {
            testData = HarnessVariables.applicationid;
        }
        else {
            testData = commonPage.getpropertyName(testData);
            testData = new PropertyLoaderFactory().getTestDataPropertyFile(screenName + ".properties").getProperty(testData);
        }
        locator = commonPage.getpropertyName(locator);
        locator = new PropertyLoaderFactory().getLocatorPropertyFile(screenName + ".properties").getProperty(locator);
        commonPage.textEnterField(testData, locator, screenName);
    }

    @And("User Press Down")
    public void userPressDown() throws Exception {
        UtilFactory.pressDown();
    }

    @Then("User Refresh the PSW Page and Click on Reload page")
    public void refreshPage() {
        try {
            UtilFactory.switchToAlertAccept();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @And("User Click on {string} Button on {string} Page")
    public void userClickOnButtonOnPage(String locator, String screenName) throws Exception {
        screenName = commonPage.removeSpaces(screenName);
        locator = commonPage.getpropertyName(locator);
        HarnessVariables.kWebprop = screenName + HarnessVariables.kWebprop;
        locator = new PropertyLoaderFactory().getLocatorPropertyFile(screenName + ".properties").getProperty(locator);
        try{
            commonPage.clickButton(locator, screenName);
        }catch(Exception e)
        {
            commonPage.clickButton(locator, screenName);
        }
    }

    @And("User Press Enter")
    public void userPressEnter() throws Exception {
        UtilFactory.pressEnter();
    }

    @Then("User Validate {string} Message for {string} on {string} Page")
    public void userValidateMessageForOnPage(String testData, String locator, String screenName) throws Exception {
        screenName = commonPage.removeSpaces(screenName);
        locator = commonPage.getpropertyName(locator);
        testData = commonPage.getpropertyName(testData);
        testData = new PropertyLoaderFactory().getTestDataPropertyFile(screenName + ".properties").getProperty(testData);
        locator = new PropertyLoaderFactory().getLocatorPropertyFile(screenName + ".properties").getProperty(locator);
            commonPage.validateTextonScreen(testData, locator, screenName);

    }


    @Then("User Validate {string} Field Appeared on {string} Page")
    public void userValidateFieldAppearedOnPage(String locator, String screenName) throws Exception {
        screenName = commonPage.removeSpaces(screenName);
        locator = commonPage.getpropertyName(locator);
        String expectedValue = null;
            expectedValue = new PropertyLoaderFactory().getTestDataPropertyFile(screenName + ".properties").getProperty(locator);

        locator = new PropertyLoaderFactory().getLocatorPropertyFile(screenName + ".properties").getProperty(locator);

        try{
            commonPage.validateFieldonScreen(expectedValue, locator, screenName);
        }catch(Exception e)
        {
            commonPage.validateFieldonScreen(expectedValue, locator, screenName);
        }
    }


    @When("User Hovers on {string} Button on {string} Page")
    public void userHoversOnButtonOnPage(String locator, String screenName) throws Exception {
        screenName = commonPage.removeSpaces(screenName);
        locator = new PropertyLoaderFactory().getLocatorPropertyFile(screenName + ".properties").getProperty(locator);
        commonPage.hoverOnButton(locator, screenName);
    }

    @Then("User Validate {string} of {string} Appeared on {string} Page")
    public void userValidateValueAppearedOnPage(String attribute, String locator, String screenName) throws Exception {
        screenName = commonPage.removeSpaces(screenName);
        locator = commonPage.getpropertyName(locator);
        String expectedValue = null;
        expectedValue = new PropertyLoaderFactory().getTestDataPropertyFile(screenName + ".properties").getProperty(locator);
        locator = new PropertyLoaderFactory().getLocatorPropertyFile(screenName + ".properties").getProperty(locator);
        commonPage.validateValueAttributeScreen(attribute, expectedValue, locator, screenName);
    }

    @Then("User Validates {string} Element Appeared on {string} Page")
    public void userValidatesElementAppearedOnPage(String locator, String screenName) throws Exception {
        screenName = commonPage.removeSpaces(screenName);
        String testData = new PropertyLoaderFactory().getTestDataPropertyFile(screenName + ".properties").getProperty(locator);
        locator = new PropertyLoaderFactory().getLocatorPropertyFile(screenName + ".properties").getProperty(locator);
        commonPage.validateDynamicElementOnScreen(locator, testData, screenName);
    }

    @Then("User Validates {string} Element Displayed on {string} Page")
    public void userValidatesElementDisplayedOnPage(String locator, String screenName) throws Exception {
        screenName = commonPage.removeSpaces(screenName);
        locator = new PropertyLoaderFactory().getLocatorPropertyFile(screenName + ".properties").getProperty(locator);
        commonPage.validateElementOnScreen(locator, screenName);
        String Value = "";
        Value = commonPage.getElementValue(locator, screenName, Value);

    }


    @Then("User Validates {string} of {string} On {string} Page")
    public void userValidatesOfOnPage(String childLocator, String dynamicData, String screenName) throws Exception {
        screenName = commonPage.removeSpaces(screenName);
        String expectedValue = new PropertyLoaderFactory().getTestDataPropertyFile(screenName + ".properties").getProperty(childLocator);
        childLocator = new PropertyLoaderFactory().getLocatorPropertyFile(screenName + ".properties").getProperty(childLocator);
        String parentLocator = new PropertyLoaderFactory().getLocatorPropertyFile(screenName + ".properties").getProperty(dynamicData);
        dynamicData = new PropertyLoaderFactory().getTestDataPropertyFile(screenName + ".properties").getProperty(dynamicData);
        commonPage.validateDynamicString(parentLocator, dynamicData, childLocator, expectedValue, screenName);
    }

    @When("User Clicks on {string} Button of {string} on {string} Page")
    public void userClicksOnButtonOfOnPage(String childLocator, String dynamicData, String screenName) throws Exception {
        screenName = commonPage.removeSpaces(screenName);
        childLocator = new PropertyLoaderFactory().getLocatorPropertyFile(screenName + ".properties").getProperty(childLocator);
        String parentLocator = new PropertyLoaderFactory().getLocatorPropertyFile(screenName + ".properties").getProperty(dynamicData);
        dynamicData = new PropertyLoaderFactory().getTestDataPropertyFile(screenName + ".properties").getProperty(dynamicData);

        try {
            commonPage.clickOnDynamicElement(parentLocator, dynamicData, childLocator, screenName);
        }catch(Exception e)
        {
            commonPage.clickOnDynamicElement(parentLocator, dynamicData, childLocator, screenName);
        }
    }

    @Then("User Validates {string} Color On Hover of {string} Button on {string} Page")
    public void userValidatesColorOnHoverOfButtonOnPage(String childLocator, String dynamicData, String screenName) throws Exception {
        screenName = commonPage.removeSpaces(screenName);
        String expectedValue = new PropertyLoaderFactory().getTestDataPropertyFile(screenName + ".properties").getProperty(childLocator);
        childLocator = new PropertyLoaderFactory().getLocatorPropertyFile(screenName + ".properties").getProperty(childLocator);

        String parentLocator = new PropertyLoaderFactory().getLocatorPropertyFile(screenName + ".properties").getProperty(dynamicData);
        dynamicData = new PropertyLoaderFactory().getTestDataPropertyFile(screenName + ".properties").getProperty(dynamicData);
        commonPage.validateDynamicElementColorOnHover(parentLocator, dynamicData, childLocator, expectedValue, screenName);
    }


    @Then("User Validates {string} URL on {string} Page")
    public void userValidatesURLOnPage(String expectedText, String screenName) throws Exception {
        screenName = commonPage.removeSpaces(screenName);
        expectedText = new PropertyLoaderFactory().getTestDataPropertyFile(screenName + ".properties").getProperty(expectedText);
        commonPage.validatePageURL(expectedText, screenName);
    }


    @And("User Clicks on {string} Button on {string} Page")
    public void userClicksOnButtonOnPage(String locator, String screenName) throws Exception {
        screenName = commonPage.removeSpaces(screenName);
        locator = commonPage.getpropertyName(locator);
        HarnessVariables.kWebprop = screenName + HarnessVariables.kWebprop;
        locator = new PropertyLoaderFactory().getLocatorPropertyFile(screenName + ".properties").getProperty(locator);
        try {
            commonPage.clickButton(locator, screenName);
        }catch(Exception e)
        {
            commonPage.clickButton(locator, screenName);
        }
    }

    @Then("User Get {string} from {string} Page")
    public void userGetFromPage(String locator, String screenName) throws Exception {
        screenName = commonPage.removeSpaces(screenName);
        locator = commonPage.getpropertyName(locator);
        HarnessVariables.kWebprop = screenName + HarnessVariables.kWebprop;
        locator = new PropertyLoaderFactory().getLocatorPropertyFile(screenName + ".properties").getProperty(locator);
        String Value = "";
        Value = commonPage.getElementValue(locator, screenName, Value);
    }

    private String endpoint;
    private int expectedHttpCode;
    private Response response;

    @ParameterType(".*?")
    public int httpCode(String code) {
        return Integer.parseInt(code.trim());
    }

    @Given("I should see response code {int} for the requested API {string}")
    public void setEndpointAndExpectedHttpCode(int httpCode, String url) throws Exception {
        url = commonPage.getpropertyName(url);
        url = new PropertyLoaderFactory().getPropertyFile(HarnessVariables.runPropFile).getProperty(url);
        expectedHttpCode = httpCode;
        endpoint = url;
    }

    @When("I send a GET request to the API")
    public void sendGetRequestToApi() {
        response = RestAssured.get(endpoint);
    }

    @Then("I should receive the expected response code")
    public void verifyResponseCode() {
        int actualHttpCode = response.getStatusCode();
        assert actualHttpCode == expectedHttpCode : "Expected HTTP code: " + expectedHttpCode + ", Actual HTTP code: " + actualHttpCode;
    }

    @And("I verify {string} with {string}")
    public void userVerifyboth(String actual,String expected) throws Exception {
        actual = commonPage.getpropertyName(actual);
        actual = new PropertyLoaderFactory().getPropertyFile(HarnessVariables.runPropFile).getProperty(actual);
        try {
            CommonPageFactory.validateFields(actual,expected);
            System.out.println("Assertion Passed: Strings are equal.");
        } catch (AssertionError e) {
            System.out.println("Assertion failed: Strings are not equal.");
        }

    }

    @And("I Load data {string} from {string} JSON")
    public void loadDataFromJSON(String jsonPath , String requestBody) throws Exception {

        String jsonKeyValue = commonPage.getpropertyName(jsonPath).toUpperCase();
        String jsonBodyPath = commonPage.removeSpaces(requestBody);
        // HarnessVariables.fbrSTRN is the variable if you want to reflect the result for it.
        ApiResponseUtils.loadDataFromJson(jsonKeyValue, jsonBodyPath);
    }

}
