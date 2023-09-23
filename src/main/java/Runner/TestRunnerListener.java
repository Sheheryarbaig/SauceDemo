package Runner;

import Setup.Initialization.EmailReportFactory;
import Setup.Initialization.ExtentReportFactory;
import Setup.Initialization.ServiceFactory;
import atu.testrecorder.ATUTestRecorder;
import atu.testrecorder.exceptions.ATUTestRecorderException;
import com.aventstack.extentreports.gherkin.model.Feature;
import org.testng.*;

import java.io.IOException;

import static Setup.Initialization.ServiceFactory.*;
import static Setup.Initialization.UtilFactory.features;
import static Setup.Initialization.UtilFactory.recording;


public class TestRunnerListener implements ITestListener,IExecutionListener {

    ExtentReportFactory extentReport = new ExtentReportFactory();
    EmailReportFactory emailReport = new EmailReportFactory();
    String emailReporting;
    String emailRecipients;

    private final ServiceFactory serviceFactoryInstance = ServiceFactory.getInstance();
    String path = "\\src\\main\\java\\Resources\\ExecutionVideo";
    //ATUTestRecorder recorder = new ATUTestRecorder(System.getProperty("user.dir")+"\\src\\test\\resources\\ExecutionVideo","TestVideo-"+datefromat.format(date),false);
    ATUTestRecorder recorder = recording(path);

    public TestRunnerListener() throws Exception {
        extentReport.ExtentReport();
    }

    @Override
    public void onTestStart(ITestResult iTestResult) {
//        killChromeInstance();
          serviceFactoryInstance.setBrowser(getParameterValue("browser"));
          emailReporting = getParameterValue("emailReport");
          emailRecipients = getParameterValue("emailRecipients");
//        try {
//            recorder.start();
//        } catch (ATUTestRecorderException e) {
//            throw new RuntimeException(e);
//        }
    }

    @Override
    public void onTestSuccess(ITestResult iTestResult) {
        try{
            extentReport.ExtentPassStep();
            if(getDriver()!=null){
                getDriver().close();
                getDriver().quit();
                killChromeInstance();
            }else if (getAndroidDriver()!=null){
                getAndroidDriver().quit();
            }else if (getIOSDriver()!=null){
                getIOSDriver().quit();
            } else if (getRequest()!=null){
                setRequest(null);
                setResponse(null);
                setParams(null);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void onTestFailure(ITestResult iTestResult) {
        try{
            extentReport.ExtentFailStep();
            if(getDriver()!=null){
                getDriver().close();
                getDriver().quit();
                killChromeInstance();
            }else if (getAndroidDriver()!=null){
                getDriver().close();
                getAndroidDriver().quit();
            }else if (getIOSDriver()!=null){
                getIOSDriver().quit();
            } else if (getRequest()!=null){
                setRequest(null);
                setResponse(null);
                setParams(null);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void onStart(ITestContext iTestContext) {
        features = extentReport.extent.createTest(Feature.class, iTestContext.getName());
        try {
            recorder.start();
        } catch (ATUTestRecorderException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onFinish(ITestContext iTestContext) {
        extentReport.FlushReport();
            if(getDriver()!=null){
                getDriver().quit();
                killChromeInstance();
                try {
                    recorder.stop();
                } catch (ATUTestRecorderException e) {
                    throw new RuntimeException(e);
                }
            }else if (getAndroidDriver()!=null){
                getAndroidDriver().quit();
//                recorder.stop();
            }else if (getIOSDriver()!=null){
                getIOSDriver().quit();
//                recorder.stop();
            }else if (getRequest()!=null){
                setRequest(null);
                setResponse(null);
                setParams(null);
            }
    }

    @Override
    public void onExecutionFinish() {
        if (emailReporting.equalsIgnoreCase("on"))
        {
            emailReport.EmailReporter(emailRecipients);
//            try {
//                recorder.stop();
//            } catch (ATUTestRecorderException e) {
//                throw new RuntimeException(e);
//            }
        }
    }

    public String getParameterValue(String key){
        return Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest().getParameter(key);
    }

    public void killChromeInstance() {
        String systemType = System.getProperty("os.name").toLowerCase();
        if (systemType.contains("win")) {
            try {
                // Selenium drivers don't always close properly, kill them
                System.out.println("Close one or more driver exe files");
                Runtime.getRuntime().exec("taskkill /f /im chromedriver.exe");
                Runtime.getRuntime().exec("taskkill /f /im operadriver.exe");
                Runtime.getRuntime().exec("taskkill /f /im geckodriver.exe");
                Runtime.getRuntime().exec("taskkill /f /im IEDriverServer.exe");
            } catch (IOException e) {
                System.out.println("Failed to close one or more driver exe files");
            }
        }
    }
}