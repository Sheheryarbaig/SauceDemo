package Setup.Initialization;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static Setup.Initialization.EmailReportFactory.failed;
import static Setup.Initialization.EmailReportFactory.passed;

public class ExtentReportFactory extends UtilFactory {

    String REPORT_LOCATION = reportLocation;
    String fileName;

    public ExtentReportFactory() throws Exception {
        SimpleDateFormat folderDate = new SimpleDateFormat("yyyy-MMM-dd");
        String date = folderDate.format(new Date());

        File folder = new File(REPORT_LOCATION + date);
        if (!folder.exists()) {
            folder.mkdirs(); // Creates the folder and its parent directories if necessary
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MMM-dd_HH-mm-ss");
        String dateTime = dateFormat.format(new Date());
        fileName = folder.getAbsolutePath() + "/SMALL_WORLD" + dateTime + ".html";
    }


    public void ExtentReport() {
        //First is to create Extent Reports
        extent = new ExtentReports();

        ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(fileName);
//        ExtentSparkReporter htmlReporter = new ExtentSparkReporter(fileName);
        htmlReporter.config().setTheme(Theme.DARK);
        htmlReporter.config().setDocumentTitle("PSW");
        htmlReporter.config().setEncoding("uft-8");
        htmlReporter.config().setReportName("PSW Automation Report");
        htmlReporter.config().setTimeStampFormat("MMM dd, yyyy HH:mm:ss");
        extent.setSystemInfo("User Name", System.getProperty("user.name"));
        extent.setSystemInfo("Time Zone", System.getProperty("user.timezone"));
        extent.setSystemInfo("OS", System.getProperty("os.name"));
        extent.setSystemInfo("Selenium Web Driver Version", System.getProperty("webdriver.__version__"));
        extent.setSystemInfo("Maven", "3.5.2");
        extent.setSystemInfo("Java Version", System.getProperty("java.version"));
        extent.attachReporter(htmlReporter);

    }

    public void ExtentFailStep() throws IOException {
        failed++;
        if(deviceName == "API"){
            scenarioDef.log(Status.FAIL,
                      "<summary> <b> <font color=red> Test Failed </b> "
                            + "</font>" + "</summary>");
        }else {
            scenarioDef.log(Status.FAIL,
                    "<details>" + "<summary> <b> <font color=red> Cause of Failure: </b> "
                            + "</font>" + "</summary>"
                            + failureException.replaceAll(",", "<br>") + "</details>", MediaEntityBuilder.createScreenCaptureFromBase64String(
                            UtilFactory.getBase64Screenshot()).build());
        }
    }

    public void ExtentPassStep() throws IOException {
        passed++;
        scenarioDef.log(Status.PASS,
                "<summary> <b> <font color=green> Test Passed </b> "
                        + "</font>" + "</summary>"
                , MediaEntityBuilder.createScreenCaptureFromBase64String(UtilFactory.getBase64Screenshot()).build());
    }

    public void FlushReport(){
        extent.flush();
    }
}
