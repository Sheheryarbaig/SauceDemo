package Setup.Initialization;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class WaitFactory {

    private WebDriver driver;

    private String envPropFile = "environment.properties";

    protected int timeOutInMilliSeconds = Integer.valueOf(new PropertyLoaderFactory().getPropertyFile(envPropFile).getProperty("timeOut"));
    protected int advTimeOutInMilliSeconds = Integer.valueOf(new PropertyLoaderFactory().getPropertyFile(envPropFile).getProperty("advTimeOut"));
    protected int pollingTimeInMilliSeconds = Integer.valueOf(new PropertyLoaderFactory().getPropertyFile(envPropFile).getProperty("pollingTime"));


    public WaitFactory(WebDriver driver) throws Exception {
        this.driver = driver;
    }
    public WaitFactory(AndroidDriver androidDriver) throws Exception {
        this.driver = androidDriver;
    }

    public void waitForPageToFinishLoading(WebDriver driver) {

        ExpectedCondition<Boolean> pageLoadCondition = driver1 -> ((JavascriptExecutor) driver1).executeScript("return document.readyState").equals("complete");

        Wait<WebDriver> wait = new FluentWait<>(driver)
                .withTimeout(Duration.ofMillis(timeOutInMilliSeconds))
                .pollingEvery(Duration.ofMillis(pollingTimeInMilliSeconds))
                .ignoring(NoSuchElementException.class);

        wait.until(pageLoadCondition);
    }

    public void waitForElementToBeClickable(String locatorValue) {

        WebDriverWait wait = new WebDriverWait(driver, TimeUnit.MILLISECONDS.toSeconds(timeOutInMilliSeconds));
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(locatorValue)));
    }

    public void waitForElementToBeClickable(WebElement element) {

        WebDriverWait wait = new WebDriverWait(driver, TimeUnit.MILLISECONDS.toSeconds(timeOutInMilliSeconds));
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    public void waitForElementToBeVisible(String locatorValue) {

        WebDriverWait wait = new WebDriverWait(driver, TimeUnit.MILLISECONDS.toSeconds(timeOutInMilliSeconds));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locatorValue)));
    }

    public void waitForElementToBeVisible(WebElement element) {

        WebDriverWait wait = new WebDriverWait(driver, TimeUnit.MILLISECONDS.toSeconds(timeOutInMilliSeconds));
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    public void waitForElementToBeVisibleAdv(String locatorValue) {

        WebDriverWait wait = new WebDriverWait(driver, TimeUnit.MILLISECONDS.toSeconds(advTimeOutInMilliSeconds));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locatorValue)));
    }

    public void waitForElementToBeVisibleAdv(WebElement element) {

        WebDriverWait wait = new WebDriverWait(driver, TimeUnit.MILLISECONDS.toSeconds(advTimeOutInMilliSeconds));
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    public void waitForElementToBeInVisible(String locatorValue) {

        WebDriverWait wait = new WebDriverWait(driver, TimeUnit.MILLISECONDS.toSeconds(timeOutInMilliSeconds));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(locatorValue)));
    }

    public void waitForElementToBeInVisible(WebElement element) {

        WebDriverWait wait = new WebDriverWait(driver, TimeUnit.MILLISECONDS.toSeconds(timeOutInMilliSeconds));
        wait.until(ExpectedConditions.invisibilityOf(element));
    }

    public static void staticWait(int waitTime) {
        try {
            Thread.sleep(waitTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
