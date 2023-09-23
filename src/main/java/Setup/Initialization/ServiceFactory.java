package Setup.Initialization;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ServiceFactory {

    private static ThreadLocal<WebDriver> driver = new ThreadLocal<WebDriver>();
    private static AndroidDriver androidDriver ;
    private static IOSDriver iosDriver;
    private static ServiceFactory instance = null;
    private static String BROWSER;
    private static RequestSpecification request;
    private static Response response;
    private static JSONObject requestParams = new JSONObject();

    // Singleton to make thread safe
    private ServiceFactory()
    {
        // hide this
    }

    public static ServiceFactory getInstance()
    {
        if (instance == null)
        {
            instance = new ServiceFactory();
        }
        return instance;
    }

    public final void setDriver(String browser)
    {
        switch (browser.toUpperCase())
        {

            //Emulator settings for Android Mobile
            case "MOBILE":
                WebDriverManager.chromedriver().setup();
                driver.set(new ChromeDriver(chromeOptions_androidMobile()));
                break;

            case "FIREFOX":

                WebDriverManager.firefoxdriver().setup();
                driver.set(new FirefoxDriver(firefoxOptions()));
                break;

            case "CHROME":
//                WebDriverManager.chromedriver().proxy("https://chromedriver.storage.googleapis.com:443").setup();
                WebDriverManager.chromedriver().setup();
                driver.set(new ChromeDriver(chromeOptions_desktop()));
                break;

            case "EDGE":
                WebDriverManager.edgedriver().setup();
                driver.set(new EdgeDriver(edgeOptions()));
                break;

            default:
                WebDriverManager.chromedriver().setup();
                driver.set(new ChromeDriver(chromeOptions_desktop()));
                break;
        }
    }

    public static WebDriver getDriver()
    {
        return driver.get();
    }

    public static AndroidDriver getAndroidDriver()
    {
        return androidDriver;
    }

    public static IOSDriver getIOSDriver()
    {
        return iosDriver;
    }

    private ChromeOptions chromeOptions_androidMobile() {

        Map<String, String> mobileEmulation = new HashMap<>();

        mobileEmulation.put("deviceName", "Galaxy S5");

        // Setup Chrome environment:
        ChromeOptions chromeOptions = new ChromeOptions();

        // Add command-line switches:
        chromeOptions.addArguments("--disable-extensions");
        chromeOptions.addArguments("--disable-extensions-file-access-check");
        chromeOptions.addArguments("--disable-extensions-http-throttling");
        chromeOptions.addArguments("--no-sandbox");
        chromeOptions.addArguments("--window-size=240,720");
        //chromeOptions.setExperimentalOption("useAutomationExtension", true);
        chromeOptions.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
        chromeOptions.addArguments("disable-popup-blocking");
        chromeOptions.setExperimentalOption("mobileEmulation", mobileEmulation);
        chromeOptions.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
        chromeOptions.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true);

        chromeOptions.addArguments("--enable-automation");
        if (System.getenv("CHROME_HEADLESS") != null) {

            chromeOptions.addArguments("--headless");
            chromeOptions.addArguments("--disable-gpu");
            chromeOptions.addArguments("--disable-setuid-sandbox");
            chromeOptions.addArguments("--hide-scrollbars");
            chromeOptions.addArguments("--ignore-ssl-errors");
        }

        // Set Chrome Profile Preferences
        Map<String, Object> prefs = new HashMap<String, Object>();
        prefs.put("credentials_enable_service", false);
        prefs.put("password_manager_enabled", false);
        chromeOptions.setExperimentalOption("prefs", prefs);

        return chromeOptions;
    }

    private ChromeOptions chromeOptions_desktop()
    {
        // Setup Chrome environment:
        ChromeOptions chromeOptions = new ChromeOptions();

        // Add command-line switches:
//        chromeOptions.addArguments("--disable-extensions");
        chromeOptions.addArguments("-incognito");
        //chromeOptions.setExperimentalOption("useAutomationExtension", true);
        chromeOptions.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
        chromeOptions.addArguments("disable-popup-blocking");
        chromeOptions.addArguments("start-maximized");
        chromeOptions.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
        chromeOptions.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true);

        chromeOptions.addArguments("--enable-automation");
        if (System.getenv("CHROME_HEADLESS") != null)
        {
            chromeOptions.addArguments("--headless");
            chromeOptions.addArguments("--disable-gpu");
            chromeOptions.addArguments("--disable-setuid-sandbox");
            chromeOptions.addArguments("--hide-scrollbars");
            chromeOptions.addArguments("--ignore-ssl-errors");
        }

        // Set Chrome Profile Preferences
        Map<String, Object> prefs = new HashMap<String, Object>();
        prefs.put("credentials_enable_service", false);
        prefs.put("password_manager_enabled", false);
        chromeOptions.setExperimentalOption("prefs", prefs);

        return chromeOptions;
    }

    private FirefoxOptions firefoxOptions()
    {
        // Setup Firefox Environment
        FirefoxOptions firefoxOptions = new FirefoxOptions();

        firefoxOptions.addArguments("--window-size=1536,722");
        firefoxOptions.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
        firefoxOptions.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true);


        return firefoxOptions;
    }

    private EdgeOptions edgeOptions()
    {
        // Setup Edge Environment
        EdgeOptions edgeOptions = new EdgeOptions();
        edgeOptions.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
        edgeOptions.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true);

        return edgeOptions;
    }

    public final void setAndroidDriver() throws Exception {
        String propFile = "run.properties";
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("deviceName", "My Phone");
        String appPackage= new PropertyLoaderFactory().getPropertyFile(propFile).getProperty("appPackage");
        String appActivity= new PropertyLoaderFactory().getPropertyFile(propFile).getProperty("appActivity");

        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "UiAutomator2");
        capabilities.setCapability("appPackage", appPackage);
        capabilities.setCapability("appActivity", appActivity);
        capabilities.setCapability("unicodeKeyboard", true);
        capabilities.setCapability("resetKeyboard", true);
        capabilities.setCapability("noReset", false);
        capabilities.setCapability(AndroidMobileCapabilityType.AUTO_GRANT_PERMISSIONS,true);

        androidDriver =  new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
    }

    public final void setIOSDriver() throws Exception {
        String propFile = "run.properties";
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("deviceName", "My Phone");
        String bundleId= new PropertyLoaderFactory().getPropertyFile(propFile).getProperty("bundleId");
        String udid= new PropertyLoaderFactory().getPropertyFile(propFile).getProperty("udid");
        String app= new PropertyLoaderFactory().getPropertyFile(propFile).getProperty("app");

        capabilities.setCapability("platformName", "IOS");
        capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "XCUITest");
        capabilities.setCapability("udid", udid);
        capabilities.setCapability("app", app);
        capabilities.setCapability("fullReset", true);

        iosDriver =  new IOSDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
    }

    public void setBrowser(String browser){
        BROWSER = browser;
    }

    public String getBrowser(){
        return BROWSER;
    }

    public static RequestSpecification getRequest(){
        return request;
    }

    public static Response getResponse(){
        return response;
    }

    public static JSONObject getRequestParams(){
        return requestParams;
    }

    public static void setRequest(RequestSpecification requestSpecificationData){
        request = requestSpecificationData;
    }

    public static void setResponse(Response responseData){
        response = responseData;
    }
    public static void setParams(JSONObject paramsData){
        requestParams = paramsData;
    }

}
