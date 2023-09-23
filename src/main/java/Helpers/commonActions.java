package Helpers;

import Setup.Initialization.*;
import io.restassured.response.Response;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;
import java.util.Properties;

public class commonActions extends CommonPageFactory {

    CommonPageFactory commonPage;
    Statement stmt;

    private String endpoint;
    private int expectedHttpCode;
    Response response;

    public commonActions() throws Exception {
        commonPage = new CommonPageFactory();
    }


    public void clickBtn(String locator, String locatorPageName) throws Exception {
        locatorPageName = commonPage.removeSpaces(locatorPageName);
        locator = commonPage.getpropertyName(locator);
        HarnessVariables.kWebprop = locatorPageName + HarnessVariables.kWebprop;
        locator = new PropertyLoaderFactory().getLocatorPropertyFile(locatorPageName + ".properties").getProperty(locator);
        try{
            commonPage.clickButton(locator, locatorPageName);
        }catch(Exception e)
        {
            commonPage.clickButton(locator, locatorPageName);
        }
    }

    public void enterText(String testData, String testDataPageName, String locator, String locatorPageName) throws Exception {
        locatorPageName = commonPage.removeSpaces(locatorPageName);
        testDataPageName = commonPage.removeSpaces(testDataPageName);

        testData = commonPage.getpropertyName(testData);
        testData = new PropertyLoaderFactory().getTestDataPropertyFile(testDataPageName + ".properties").getProperty(testData);

        locator = commonPage.getpropertyName(locator);
        locator = new PropertyLoaderFactory().getLocatorPropertyFile(locatorPageName + ".properties").getProperty(locator);

        commonPage.textEnterField(testData, locator, locatorPageName);
    }

    public void assertion(String testData, String testDataPageName, String locator, String locatorPageName) throws Exception {
        locatorPageName = commonPage.removeSpaces(locatorPageName);
        testDataPageName = commonPage.removeSpaces(testDataPageName);
        locator = commonPage.getpropertyName(locator);
        testData = commonPage.getpropertyName(testData);
        testData = new PropertyLoaderFactory().getTestDataPropertyFile(testDataPageName + ".properties").getProperty(testData);
        locator = new PropertyLoaderFactory().getLocatorPropertyFile(locatorPageName + ".properties").getProperty(locator);
        validateTextonScreen(testData, locator, locatorPageName);
    }

    public void checkboxBtn(String locator, String locatorPageName) throws Exception {
        locatorPageName = commonPage.removeSpaces(locatorPageName);
        locator = commonPage.getpropertyName(locator);
        HarnessVariables.kWebprop = locatorPageName + HarnessVariables.kWebprop;
        locator = new PropertyLoaderFactory().getLocatorPropertyFile(locatorPageName + ".properties").getProperty(locator);
        try{
            commonPage.checkboxButton(locator, locatorPageName);
        }catch(Exception e)
        {
            commonPage.checkboxButton(locator, locatorPageName);
        }
    }

    public void assertionByAttributValue(String valueToCheck, String natureOfValueAttribute, String attributeName, String locator, String locatorPageName) throws Exception {
        locatorPageName = commonPage.removeSpaces(locatorPageName);
        locator = commonPage.getpropertyName(locator);
        locator = new PropertyLoaderFactory().getLocatorPropertyFile(locatorPageName + ".properties").getProperty(locator);
        validateByValueAttribute(valueToCheck,
                natureOfValueAttribute,
                attributeName,
                locator,
                locatorPageName);
    }

    public void refreshPageWithAlert(){
        try {
            UtilFactory.switchToAlertAccept();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
