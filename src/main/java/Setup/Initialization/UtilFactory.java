package Setup.Initialization;

import atu.testrecorder.ATUTestRecorder;
import atu.testrecorder.exceptions.ATUTestRecorderException;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.touch.LongPressOptions;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.restassured.response.Response;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.WordUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.*;
import org.openqa.selenium.html5.WebStorage;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.touch.TouchActions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static Setup.Initialization.WaitFactory.staticWait;

public class UtilFactory {

    protected static ElementFactory elementFactory;
    protected static WaitFactory waitFactory;
    private static String envPropFile = "environment.properties";
    public static String ENumPackage = "Locators.EnumFactory.";
    private static String screenshotFolder;
    public static String reportLocation;
    protected static String deviceName= "";
    public static ServiceFactory serviceFactoryInstance = ServiceFactory.getInstance();
    private static ATUTestRecorder recorder;

    static {
        try {
            screenshotFolder = new PropertyLoaderFactory().getPropertyFile(envPropFile).getProperty("screenshot.folder");
            reportLocation = new PropertyLoaderFactory().getPropertyFile(envPropFile).getProperty("extent.report.folder");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //For Reporting

    public ExtentReports extent;

    public static String scenarioName;
    public static String PageName;
    public static ExtentTest scenarioDef;
    public static ExtentTest features;
    public static String failureException;

    public UtilFactory() throws Exception {
    }

    protected void loadUrl(String url){
        try{
            ServiceFactory.getDriver().manage().deleteAllCookies();
            ServiceFactory.getDriver().get(url);
            waitForPageLoad();
            scenarioDef.log(Status.PASS,"Initiated the browser session");
        }catch (Exception e){
            scenarioDef.log(Status.FAIL,"Could not initiate the browser session");
            throw e;
        }
    }

    protected void openURL(String url){
        try{
            ServiceFactory.getDriver().get(url);
            waitForPageLoad();
            scenarioDef.log(Status.PASS,"Opened URL: " + url);
        }catch (Exception e){
            scenarioDef.log(Status.FAIL,"Could not open URL: "+url);
            throw e;
        }
    }

    protected Boolean isAndroid(){
        if(deviceName.equalsIgnoreCase("ANDROID")){
            return true;
        }else {
            return false;
        }
    }

    protected void validateURL(String expectedUrl){
        waitForPageLoad();
        String errorMsg = null;
        try{
            waitForPageLoad();
            String actualURL= ServiceFactory.getDriver().getCurrentUrl();

            if (actualURL.contains(expectedUrl)){
                scenarioDef.log(Status.PASS,"Validated Url as Expected Url: "+expectedUrl);
            }else {
                errorMsg = "Could not Validate Url as Expected Url: "+expectedUrl+" , Actual Url: "+actualURL;
                throw new NoSuchContextException("Actual and Expected Url Differs");
            }
        }catch (Exception e){
            failureException = e.toString();
            if (errorMsg == null){
                scenarioDef.log(Status.FAIL,"Unable to Load the Url");
            }else {
                scenarioDef.log(Status.FAIL,errorMsg);
            }
            throw e;
        }
    }


    protected void click(String locator){
        WebElement element = elementFactory.getElement(locator);
        click(element);
    }
    protected void uploadfile(String filename,String locator,String uploader){
        WebElement element = elementFactory.getElement(locator);
        click(element);
        upload(filename,uploader);

    }

    protected void click(WebElement element){
        try
        {
            element.click();
        } catch (Exception e)
        {
            throw e;
        }
    }
    protected void upload(String filename,String uploader){
        try
        {
            WebElement fileInput = ServiceFactory.getDriver().findElement(By.xpath(uploader)); // Change this to the actual locator
            File file = new File("src/main/java/Resources/"+filename);
            String absolutePath = file.getAbsolutePath();
            // Provide the path to the file you want to upload
//            String filePath = "src/main/java/Resources/"+filename; // Change this to the correct file path

            // Use sendKeys to upload the file
            fileInput.sendKeys(absolutePath);
        } catch (Exception e)
        {
            throw e;
        }
    }

    protected void jsClick(String locatorValue)
    {
        WebElement element = elementFactory.getElement(locatorValue);
        jsClick(element);
    }

    protected void jsClick(WebElement element)
    {
        JavascriptExecutor executor = (JavascriptExecutor) ServiceFactory.getDriver();
        executor.executeScript("arguments[0].click();", element);
    }
    protected void clearField(String locatorValue) throws Exception
    {
        WebElement element = elementFactory.getElement(locatorValue);
        clearField(element);
    }
    protected void clearField(WebElement element) throws Exception
    {
        element.clear();
    }

    protected void removeOneSpace(String locatorValue) throws Exception
    {
        WebElement element = elementFactory.getElement(locatorValue);
        removeOneSpace(element);
    }

    protected void removeOneSpace(WebElement element) throws Exception
    {
        element.sendKeys(Keys.BACK_SPACE);
    }

    protected void enterString(String locatorValue, String fieldValue)
    {
        WebElement element = elementFactory.getElement(locatorValue);
        element.clear();
        enterString(element,fieldValue);
    }

    protected void enterString(WebElement element, String fieldValue)
    {
        if(fieldValue.equals("ABFS1222"))
        {
            String randomSuffix = RandomStringUtils.randomAlphanumeric(3);
            fieldValue = fieldValue + randomSuffix;
        }
        element.sendKeys(fieldValue);
    }

    protected void enterNumberByForce(String textToEnter){
        for (int i=0;i<textToEnter.length();i++){
            switch (textToEnter.charAt(i)) {
                case '1':
                    ServiceFactory.getAndroidDriver().pressKey(new KeyEvent(AndroidKey.DIGIT_1));
                    break;
                case '2':
                    ServiceFactory.getAndroidDriver().pressKey(new KeyEvent(AndroidKey.DIGIT_2));
                    break;
                case '3':
                    ServiceFactory.getAndroidDriver().pressKey(new KeyEvent(AndroidKey.DIGIT_3));
                    break;
                case '4':
                    ServiceFactory.getAndroidDriver().pressKey(new KeyEvent(AndroidKey.DIGIT_4));
                    break;
                case '5':
                    ServiceFactory.getAndroidDriver().pressKey(new KeyEvent(AndroidKey.DIGIT_5));
                    break;
                case '6':
                    ServiceFactory.getAndroidDriver().pressKey(new KeyEvent(AndroidKey.DIGIT_6));
                    break;
                case '7':
                    ServiceFactory.getAndroidDriver().pressKey(new KeyEvent(AndroidKey.DIGIT_7));
                    break;
                case '8':
                    ServiceFactory.getAndroidDriver().pressKey(new KeyEvent(AndroidKey.DIGIT_8));
                    break;
                case '9':
                    ServiceFactory.getAndroidDriver().pressKey(new KeyEvent(AndroidKey.DIGIT_9));
                    break;
                case '0':
                    ServiceFactory.getAndroidDriver().pressKey(new KeyEvent(AndroidKey.DIGIT_0));
                    break;
                case ' ':
                    ServiceFactory.getAndroidDriver().pressKey(new KeyEvent(AndroidKey.SPACE));
                    break;
                default:
                    break;
            }
        }
    }

    protected void clickDoneByForce(){
        if(isAndroid()){
            ServiceFactory.getAndroidDriver().pressKey(new KeyEvent(AndroidKey.SPACE));

        }
    }

    public void hover(String locatorValue) {
        WebElement element = elementFactory.getElement(locatorValue);
        hover(element);
    }

    protected void hover(WebElement element) {
        Actions action = new Actions(ServiceFactory.getDriver());
        action.moveToElement(element).perform();
    }

    protected void jsHover(String locatorValue) throws Exception
    {
        WebElement element = elementFactory.getElement(locatorValue);
        jsHover(element);
    }

    protected void jsHover(WebElement element) throws Exception
    {
        String mouseOverScript = "if(document.createEvent){var evObj = document.createEvent('MouseEvents');evObj.initEvent('mouseover', true, false); arguments[0].dispatchEvent(evObj);} else if(document.createEventObject) { arguments[0].fireEvent('onmouseover');}";
        ((JavascriptExecutor) ServiceFactory.getDriver()).executeScript(mouseOverScript, element);
    }

    protected void switchToIframe(String locatorValue) throws Exception
    {
        WebElement element = elementFactory.getElement(locatorValue);
        switchToIframe(element);
    }

    protected void switchToIframe(WebElement element) throws Exception
    {
        ServiceFactory.getDriver().switchTo().frame(element);
    }

    public static void switchToAlertAccept() throws Exception
    {
        ServiceFactory.getDriver().navigate().refresh();
        ServiceFactory.getDriver().switchTo().alert().accept();
    }

    protected void switchToDefaultContent() throws Exception
    {
        ServiceFactory.getDriver().switchTo().defaultContent();
    }

    protected void scrollToElement(String locatorValue) throws Exception
    {
        WebElement element = elementFactory.getElement(locatorValue);
        scrollToElement(element);
    }

    protected void scrollToElement(WebElement element) throws Exception
    {
        JavascriptExecutor js = (JavascriptExecutor) ServiceFactory.getDriver();
        js.executeScript("arguments[0].scrollIntoView();", element);
    }

    protected void scrollByPixels(int x, int y) {
        JavascriptExecutor js = (JavascriptExecutor) ServiceFactory.getDriver();
        js.executeScript("window.scrollBy("+x+","+y+")");
    }

    protected static void scrollToTop()
    {
        JavascriptExecutor js = (JavascriptExecutor) ServiceFactory.getDriver();
        js.executeScript("window.scrollTo(0, 0)");
    }

    protected void scrollDownToBottom()
    {
        JavascriptExecutor js = (JavascriptExecutor) ServiceFactory.getDriver();
        js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
    }

    protected Boolean isVisible(String locatorValue)
    {
        int size = elementFactory.getElementList(locatorValue);
        if(size > 0 ){
            return true;
        }
        else {
            return false;
        }
    }

    protected Boolean isVisibleAdv(String locatorValue)
    {
        try{
            waitFactory.waitForElementToBeVisibleAdv(locatorValue);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    protected Boolean isSelected(String locatorValue){
        WebElement element =  elementFactory.getElement(locatorValue);
        if (element.isSelected()) {
            return true;
        }
        else {
            return false;
        }

    }

    protected int getSize(String locatorValue){
        return elementFactory.getElementList(locatorValue);
    }

    protected Boolean isScrollable(String locatorValue)
    {
        Boolean isScrollable = getAttribute(locatorValue,"style").contains("scroll");
        if(isScrollable){
            return true;
        }
        else {
            return false;
        }
    }
    protected String getCSS(String locatorValue,String attributeValue){
        WebElement element = elementFactory.getElement(locatorValue);
        return getCSSAttribute(element,attributeValue);
    }

    protected String getCSSAttribute(WebElement element,String attributeValue)
    {
        return element.getCssValue(attributeValue);
    }


    protected String getAttribute(String locatorValue,String attributeValue)
    {
        WebElement element = elementFactory.getElement(locatorValue);
        return getAttribute(element,attributeValue);
    }

    protected String getAttribute(WebElement element,String attributeValue)
    {
        return element.getAttribute(attributeValue);
    }

    protected String getText(String locatorValue)
    {
        return getText(elementFactory.getElement(locatorValue));
    }

    protected String getText(WebElement element)
    {
        return element.getText();
    }
    protected String getTextFromElement(String locatorValue) {
        WebElement element = null;
        try {
            WebDriverWait wait = new WebDriverWait(ServiceFactory.getDriver(), 10);
            element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locatorValue)));
            return element.getText();
        } catch (Exception e) {
            throw new RuntimeException("Error getting text from element: " + e.getMessage());
        }
    }

    protected void waitForPageLoad(){
        waitFactory.waitForPageToFinishLoading(ServiceFactory.getDriver());
    }

    protected static void customWait(int waitTime){
        staticWait(waitTime);
    }

    public static String getBase64Screenshot() throws IOException {
        String base64StringOfScreenshot = "";

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MMMM/dd/");
        DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("__HH_mm_ss");
        LocalDateTime now = LocalDateTime.now();
        TakesScreenshot ts = null;

        if (deviceName == null || deviceName.isEmpty()) {
            throw new IllegalArgumentException("Device name is not provided.");
        }

        if (deviceName.equalsIgnoreCase("ANDROID")) {
            ts = ServiceFactory.getAndroidDriver();
        } else if (deviceName.equalsIgnoreCase("IOS")) {
            ts = ServiceFactory.getIOSDriver();
        } else {
            ts = (TakesScreenshot) ServiceFactory.getDriver();
        }

        if (ts != null) {
            File source = ts.getScreenshotAs(OutputType.FILE);

            String dest = screenshotFolder + "/" + dtf.format(now) + "/" + deviceName + "/" + scenarioName + "/" + dtf2.format(now) + ".png";
            File destination = new File(dest);
            FileUtils.copyFile(source, destination);

            byte[] fileContent = FileUtils.readFileToByteArray(source);
            base64StringOfScreenshot = "data:image/png;base64," + Base64.getEncoder().encodeToString(fileContent);
        } else {
            System.out.println("Device Name: " + deviceName);
            throw new IllegalStateException("Screenshot capability is not available for the specified device: " + deviceName);
        }

        return base64StringOfScreenshot;
    }


    public static String getVideocreenshot() throws IOException {

        String getVideocreenshot="";
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MMMM/dd/");
        DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("__HH_mm_ss");
        LocalDateTime now = LocalDateTime.now();
        TakesScreenshot ts;
        if(deviceName.equalsIgnoreCase("ANDROID")){
            ts = ServiceFactory.getAndroidDriver();
        }else if (deviceName.equalsIgnoreCase("IOS")){
            ts = ServiceFactory.getIOSDriver();
        }else{
            ts = (TakesScreenshot) ServiceFactory.getDriver();
        }
        File source = ts.getScreenshotAs(OutputType.FILE);

        String dest = screenshotFolder + "/" +dtf.format(now) + "/" + deviceName + "/" +scenarioName+"/" +dtf2.format(now)+ ".mp4";

        File destination = new File(dest);
        FileUtils.copyFile(source, destination);

        return getVideocreenshot;
    }

    public static String getLocatorNameforLog(String Locator) throws IOException {
        Locator = Locator.replace("XPATH_","");
        Locator = Locator.replace("_"," ");
        Locator = WordUtils.capitalizeFully(Locator);
        return Locator;
    }

    protected void clearSession(){
        try{
            WebDriver driver = ServiceFactory.getDriver();
            driver.manage().deleteAllCookies();
            ((WebStorage)driver).getSessionStorage().clear();
            ((WebStorage)driver).getLocalStorage().clear();
            customWait(3000);
            scenarioDef.log(Status.PASS,"Successfully Clear Browser Session");
        }catch (Exception e){
            failureException = e.toString();
            scenarioDef.log(Status.FAIL,"Unable to Clear Browser Session");
            throw e;
        }
    }

    protected void refreshPage(){
        try{
            ServiceFactory.getDriver().navigate().refresh();
            waitForPageLoad();
            customWait(3000);
            scenarioDef.log(Status.PASS,"Successfully Refresh Browser Page");
        }catch (Exception e){
            failureException = e.toString();
            scenarioDef.log(Status.FAIL,"Unable to Refresh Browser Page");
            throw e;
        }
    }

    protected void navigateToBackPage(){
        try{
            ServiceFactory.getDriver().navigate().back();
            waitForPageLoad();
            customWait(3000);
            scenarioDef.log(Status.PASS,"Successfully Navigate to Back Page");
        }catch (Exception e){
            failureException = e.toString();
            scenarioDef.log(Status.FAIL,"Unable to Navigate to Back Page");
            throw e;
        }
    }

    protected String getCurrentURL() {
        return ServiceFactory.getDriver().getCurrentUrl();
    }

    protected void switchesNewTab() {
        WebDriver driver = ServiceFactory.getDriver();
        String[] tabs = driver.getWindowHandles().toArray(new String[0]);
        driver.switchTo().window(tabs[1]);
    }
    protected void switchToParentTab() {
        WebDriver driver = ServiceFactory.getDriver();
        String[] tabs = driver.getWindowHandles().toArray(new String[0]);
        driver.switchTo().window(tabs[0]);
    }

    protected void closeOldTab() {
        WebDriver driver = ServiceFactory.getDriver();
        String[] tabs = driver.getWindowHandles().toArray(new String[0]);
        driver.switchTo().window(tabs[0]);
        driver.close();
        driver.switchTo().window(tabs[1]);
    }

    protected String getRandomStrings() {
        String rndName = RandomStringUtils.randomAlphabetic(6).toLowerCase();
        return rndName;
    }

    protected int getRandomNumber() {
        Random random = new Random();
        return 1000000 + random.nextInt(9000000);
    }
    protected String getCurrentDate() {
        SimpleDateFormat format = new SimpleDateFormat("M/dd/yyyy");
        format.setTimeZone(TimeZone.getTimeZone("Asia/Dubai"));
        String date = format.format(new Date());
        return date;
    }
    protected String getCurrentMonth() {
        SimpleDateFormat format = new SimpleDateFormat("M");
        format.setTimeZone(TimeZone.getTimeZone("Asia/Dubai"));
        String month = format.format(new Date());
        return month;
    }
    protected String getCurrentDateWithoutYear() {
        SimpleDateFormat format = new SimpleDateFormat("M/dd");
        format.setTimeZone(TimeZone.getTimeZone("Asia/Dubai"));
        String date = format.format(new Date());
        return date;
    }

    protected String getCurrentTime(){
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        format.setTimeZone(TimeZone.getTimeZone("Asia/Dubai"));
        return format.format(new Date());
    }

    protected int getCurrentDay(String currentDate) throws ParseException {
        Date oldDate=new SimpleDateFormat("M/dd/yyyy").parse(currentDate);
        Calendar c = Calendar.getInstance();
        c.setTime(oldDate);
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
        return dayOfWeek;
    }

    protected Boolean currentDateIsSchedule(String startDate,String endDate) throws ParseException {
        String currentDate=getCurrentDateWithoutYear();
        Date start=new SimpleDateFormat("M/dd").parse(startDate);
        Date end=new SimpleDateFormat("M/dd").parse(endDate);
        Date current=new SimpleDateFormat("M/dd").parse(currentDate);
        if(!start.after(current) && !end.before(current)){
            return true;
        }else {
            return false;
        }
    }

    protected Boolean dateIsInPast(String actualDate, String expectedDate) throws ParseException {
        Date actual=new SimpleDateFormat("dd-M-yyyy").parse(actualDate);
        Date expected=new SimpleDateFormat("M/dd/yyyy").parse(expectedDate);
        if(actual.before(expected) || actual.equals(expected)){
            return true;
        }else {
            return false;
        }
    }

    protected Boolean dateIsInFuture(String actualDate, String expectedDate) throws ParseException {
        Date actual=new SimpleDateFormat("dd-M-yyyy").parse(actualDate);
        Date expected=new SimpleDateFormat("M/dd/yyyy").parse(expectedDate);
        if(actual.after(expected) || actual.equals(expected)){
            return true;
        }else {
            return false;
        }
    }

    protected void longPress(String locator){
        WebElement element = elementFactory.getElement(locator);
        longPress(element);
    }

    protected void longPress(WebElement element){
        if(ServiceFactory.getIOSDriver()!=null){
            TouchAction action = new TouchAction(ServiceFactory.getIOSDriver());
            action.longPress((LongPressOptions) element);
            action.perform();
        }else if(ServiceFactory.getDriver()!=null){
            TouchActions action = new TouchActions(ServiceFactory.getDriver());
            action.longPress(element);
            action.perform();
        }else if(ServiceFactory.getAndroidDriver()!=null){
            TouchAction action = new TouchAction(ServiceFactory.getAndroidDriver());
            action.longPress((LongPressOptions) element);
            action.perform();
        }
    }

    protected void scrollToElementMobile(String locator){
        customWait(1000);
        String oldSource = "Initial Source";
        String currentSource;
        Dimension size;
        if(isAndroid()){
            size = ServiceFactory.getAndroidDriver().manage().window().getSize();
        }else {
            size = ServiceFactory.getIOSDriver().manage().window().getSize();
        }
        int startX = 0;
        int endX = 0;
        int startY = 0;
        int endY = 0;
        startY = (int) (size.height * 0.70);
        endY = (int) (size.height * 0.30);
        startX = (size.width / 2);
        boolean flag = true;
        boolean displayFlag = false;
        while (!displayFlag && flag){
            if(isAndroid()){
                currentSource = ServiceFactory.getAndroidDriver().getPageSource();
            }else {
                currentSource = ServiceFactory.getIOSDriver().getPageSource();
            }
            try {
                elementFactory.getElement(locator);
                displayFlag = true;
            } catch (Exception e) {
                if(isAndroid()){
                    new TouchAction(ServiceFactory.getAndroidDriver()).press(PointOption.point(startX, startY))
                            .waitAction(WaitOptions.waitOptions(Duration.ofMillis(400)))
                            .moveTo(PointOption.point(startX, endY))
                            .release()
                            .perform();
                }else {
                    new TouchAction(ServiceFactory.getIOSDriver()).press(PointOption.point(startX, startY))
                            .waitAction(WaitOptions.waitOptions(Duration.ofMillis(400)))
                            .moveTo(PointOption.point(startX, endY))
                            .release()
                            .perform();
                }
            }
            if (currentSource.equals(oldSource)) {
                flag = false;
            }
            oldSource = currentSource;
        }
    }

    protected void scrollDownMobile(){
        customWait(200);
        Dimension size;
        if(isAndroid()){
            size = ServiceFactory.getAndroidDriver().manage().window().getSize();
        }else {
            size = ServiceFactory.getIOSDriver().manage().window().getSize();
        }
        int startX = 0;
        int startY = 0;
        int endY = 0;
        startY = (int) (size.height * 0.70);
        endY = (int) (size.height * 0.30);
        startX = (size.width / 2);
        if(isAndroid()){
            new TouchAction(ServiceFactory.getAndroidDriver()).press(PointOption.point(startX, startY))
                    .waitAction(WaitOptions.waitOptions(Duration.ofMillis(400)))
                    .moveTo(PointOption.point(startX, endY))
                    .release()
                    .perform();
        }else {
            new TouchAction(ServiceFactory.getIOSDriver()).press(PointOption.point(startX, startY))
                    .waitAction(WaitOptions.waitOptions(Duration.ofMillis(400)))
                    .moveTo(PointOption.point(startX, endY))
                    .release()
                    .perform();
        }
    }

    protected void scrollDownToBottomMobile(){
        String oldSource = "Initial Source";
        String currentSource;
        Dimension size;
        if(isAndroid()){
            size = ServiceFactory.getAndroidDriver().manage().window().getSize();
        }else {
            size = ServiceFactory.getIOSDriver().manage().window().getSize();
        }
        int startX = 0;
        int endX = 0;
        int startY = 0;
        int endY = 0;
        startY = (int) (size.height * 0.70);
        endY = (int) (size.height * 0.30);
        startX = (size.width / 2);
        boolean flag = true;
        while (flag){
            if(isAndroid()){
                currentSource = ServiceFactory.getAndroidDriver().getPageSource();
                new TouchAction(ServiceFactory.getAndroidDriver()).press(PointOption.point(startX, startY))
                        .waitAction(WaitOptions.waitOptions(Duration.ofMillis(400)))
                        .moveTo(PointOption.point(startX, endY))
                        .release()
                        .perform();
            }else {
                currentSource = ServiceFactory.getIOSDriver().getPageSource();
                new TouchAction(ServiceFactory.getIOSDriver()).press(PointOption.point(startX, startY))
                        .waitAction(WaitOptions.waitOptions(Duration.ofMillis(400)))
                        .moveTo(PointOption.point(startX, endY))
                        .release()
                        .perform();
            }
            if (currentSource.equals(oldSource)) {
                flag = false;
            }
            oldSource = currentSource;
        }
    }

    protected void swipeElement(WebElement element, String dir) {
        final int ANIMATION_TIME = 200;
        final int PRESS_TIME = 200;
        int edgeBorder;
        PointOption pointOptionStart, pointOptionEnd;
        Rectangle rect = element.getRect();
        edgeBorder = 0;
        if (dir.equals("DOWN")) {
            pointOptionStart = PointOption.point(rect.x + rect.width / 2,
                    rect.y + edgeBorder);
            pointOptionEnd = PointOption.point(rect.x + rect.width / 2,
                    rect.y + rect.height - edgeBorder);
        } else if (dir.equals("UP")) {
            pointOptionStart = PointOption.point(rect.x + rect.width / 2,
                    rect.y + rect.height - edgeBorder);
            pointOptionEnd = PointOption.point(rect.x + rect.width / 2,
                    rect.y + edgeBorder);
        } else if (dir.equals("LEFT")) {
            pointOptionStart = PointOption.point(rect.x + rect.width - edgeBorder,
                    rect.y + rect.height / 2);
            pointOptionEnd = PointOption.point(rect.x + edgeBorder,
                    rect.y + rect.height / 2);
        } else if (dir.equals("RIGHT")) {
            pointOptionStart = PointOption.point(rect.x + edgeBorder,
                    rect.y + rect.height / 2);
            pointOptionEnd = PointOption.point(rect.x + rect.width - edgeBorder,
                    rect.y + rect.height / 2);
        } else {
            throw new IllegalArgumentException("swipeElementAndroid(): Direction: '" + dir + "' not supported");
        }
        try {
            if(isAndroid()){
                new TouchAction(ServiceFactory.getAndroidDriver())
                        .press(pointOptionStart)
                        // a bit more reliable when we add small wait
                        .waitAction(WaitOptions.waitOptions(Duration.ofMillis(PRESS_TIME)))
                        .moveTo(pointOptionEnd)
                        .release().perform();
            }else {
                new TouchAction(ServiceFactory.getIOSDriver())
                        .press(pointOptionStart)
                        // a bit more reliable when we add small wait
                        .waitAction(WaitOptions.waitOptions(Duration.ofMillis(PRESS_TIME)))
                        .moveTo(pointOptionEnd)
                        .release().perform();
            }
        } catch (Exception e) {
            System.err.println("swipeElement(): TouchAction Failed\n" + e.getMessage());
            return;
        }
        try {
            Thread.sleep(ANIMATION_TIME);
        } catch (InterruptedException e) {
        }
    }

    protected void hideDoneKeyboardIOS(){
        if(!isAndroid()) {
            ServiceFactory.getIOSDriver().findElement(By.name("Done")).click();
        }
    }

    protected void selectDropdownOptionIOS(String optionToSelect){
        if(!isAndroid()) {
            ServiceFactory.getIOSDriver().findElement(By.className("XCUIElementTypePickerWheel")).sendKeys(optionToSelect);
            hideDoneKeyboardIOS();
        }
    }

    protected void goBack(){
        if(isAndroid()){
            ServiceFactory.getAndroidDriver().navigate().back();
        }else {
            ServiceFactory.getIOSDriver().navigate().back();
        }
        customWait(200);
    }
    protected static void pressEnter() {
        ServiceFactory.getDriver(); // Replace with the appropriate method to get the driver
        try{
            customWait(200);
            Actions actions = new Actions(ServiceFactory.getDriver());
            actions.sendKeys(Keys.ENTER).perform();
            scenarioDef.log(Status.PASS,"Pressed Enter");
        }catch (Exception e){
            scenarioDef.log(Status.FAIL,"Press Enter is not supported for the current driver");
            throw e;
        }
    }

    protected static void ControlA() {
        ServiceFactory.getDriver(); // Replace with the appropriate method to get the driver
        try{
            customWait(200);
            Actions actions = new Actions(ServiceFactory.getDriver());
            actions.keyDown(Keys.CONTROL).sendKeys("a").keyUp(Keys.CONTROL).perform();
            // Simulate Backspace
            actions.sendKeys(Keys.BACK_SPACE).perform();

            scenarioDef.log(Status.PASS,"Pressed Enter");
        }catch (Exception e){
            scenarioDef.log(Status.FAIL,"Press Enter is not supported for the current driver");
            throw e;
        }
    }
    protected static void pressDown() {
        ServiceFactory.getDriver(); // Replace with the appropriate method to get the driver
        try{
            customWait(200);
            Actions actions = new Actions(ServiceFactory.getDriver());
            actions.sendKeys(Keys.DOWN).perform();
            scenarioDef.log(Status.PASS,"Pressed Enter");
        }catch (Exception e){
            scenarioDef.log(Status.FAIL,"Press Enter is not supported for the current driver");
            throw e;
        }
    }

    protected Boolean elementInAscending(List<WebElement> elements){
        Boolean flag = true;
        float previousValue=0,currentValue=0;
        for(int i=0;i<3 && flag; i++){
            previousValue=currentValue;
            currentValue= Float.parseFloat(elements.get(i).getText().replaceAll("[^0-9\\.]",""));
            if(previousValue<=currentValue){
                flag=true;
            }else {
                flag=false;
            }
        }
        return flag;
    }

    protected float roundUpAmount(float amount){
        return (float) (Math.round(amount * 100.00)/100.00);
    }

    protected JSONObject responseToJSONObject(Response response) throws org.json.simple.parser.ParseException {
        JSONParser parser = new JSONParser();
        JSONObject actualResponse = (JSONObject) parser.parse(response.asString());
        return actualResponse;
    }

    protected JSONObject stringToJSONObject(String response) throws org.json.simple.parser.ParseException {
        JSONParser parser = new JSONParser();
        JSONObject actualResponse = (JSONObject) parser.parse(response);
        return actualResponse;
    }

    protected boolean compareJSONObjects(JSONObject actualResponse,JSONObject expectedResponse,String parameter) throws org.json.simple.parser.ParseException {
        actualResponse = byPassParameter(actualResponse,parameter);
        System.out.println("Actual: "+actualResponse);
        System.out.println("Expected: "+expectedResponse);
        if(actualResponse.equals(expectedResponse)){
            return true;
        }else {
            return false;
        }
    }

    protected JSONObject byPassParameter(JSONObject jsonBody, String parameter) throws org.json.simple.parser.ParseException {
        if(parameter!=null){
            String paramValues[] = parameter.split(",");
            String temp;
            JSONObject tempBody=jsonBody;
            for(int i=0;i<paramValues.length;i++){
                String val[] = paramValues[i].split("\\.");
                if(val.length>0){
                    for(int j=0;j<val.length-1;j++){
                        tempBody = (JSONObject) tempBody.get(val[j]);
                    }
                    tempBody = updateJSONWithTag(tempBody,val[val.length-1]);
                    jsonBody = updateJSONWithMyVal(tempBody,parameter,tempBody.toString());
                }else {
                    jsonBody = updateJSONWithTag(jsonBody,parameter);
                }
            }
        }
        return jsonBody;
    }

    protected JSONObject updateJSONWithTag(JSONObject jsonObject,String parameter){
        jsonObject.put(parameter,"#tag");
        return jsonObject;
    }

    protected JSONObject updateJSONWithMyVal(JSONObject jsonObject,String parameter,String myVal){
        jsonObject.put(parameter,myVal);
        return jsonObject;
    }

    public static String locatorXpath(String enumClassName, String locator) throws ClassNotFoundException {
        int i = 0;
        String XPath = null;
        Class cls = Class.forName(ENumPackage+enumClassName);
        for (Object obj : cls.getEnumConstants()) {
            try {
                Method m = cls.getMethod("getValue", null);
                XPath = m.invoke(obj, null).toString();
                if(cls.getEnumConstants()[i].toString().equals(locator)){
                    return XPath;
                }
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException ex) {
                System.out.println("could not find enum");
            }
            i++;
        }
        return XPath;
    }

    public static ATUTestRecorder recording(String path) throws ATUTestRecorderException, IOException {
        DateFormat datefromat = new SimpleDateFormat("yy-MM-dd HH-mm-ss");
        Date date = new Date();
        path = System.getProperty("user.dir")+path+"\\";
        Path fileToDeletePath = Paths.get(path).toAbsolutePath();
        File delFile = fileToDeletePath.toAbsolutePath().toFile();
        if(delFile.isDirectory()){
            File[] files = delFile.listFiles();

            for (File f: files) {
                f.delete();
            }
        }
        recorder = new ATUTestRecorder(path,"Execution Video-"+datefromat.format(date),false);
        return recorder;
    }
    public static String loadJSONFile(String filePath) {
        try {
            byte[] encodedBytes = Files.readAllBytes(Paths.get(filePath));
            return new String(encodedBytes, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


//
//    public static boolean compareJsonObjects(JSONObject object1, JSONObject object2, List<String> exclusionList, String lastKey) {
////        if(exclusionList == null) {
////            exclusionList = new ArrayList<>();
////        }
//        JSONObject elementsObject1 = object1;
//        JSONObject elementsObject2 = object2;
//        Set<String> keys = elementsObject1.keySet();
//        boolean compareFlag = false;
//        for (String key: keys) {
//            if(!exclusionList.contains(lastKey+key)) {
//                JsonElement objectElement1 = (JsonElement) elementsObject1.get(key);
//                JsonElement objectElement2 = (JsonElement) elementsObject2.get(key);
//                if (objectElement1.isJsonObject()) {
//                    String newKey = lastKey + key + ".";
//                    compareFlag = compareJsonObjects(objectElement1.getAsJsonObject(),
//                            objectElement2.getAsJsonObject(),
//                            exclusionList,
//                            newKey);
//                } else {
//                    compareFlag = objectElement1.equals(objectElement2);
//                }
//                if (!compareFlag) {
//                    return compareFlag;
//                }
//            }
//            else compareFlag = true;
//        }
//        return compareFlag;
//    }

    protected String getAttributeFromElement(String locatorValue, String attributeName) {
        WebElement element = null;
        try {
            WebDriverWait wait = new WebDriverWait(ServiceFactory.getDriver(), 10);
            element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locatorValue)));
            return element.getAttribute(attributeName);
        } catch (Exception e) {
            throw new RuntimeException("Error getting attribute from element: " + e.getMessage());
        }
    }

}
