package Runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        features = "src/main/java/FeatureFiles/SauceDemo/CompleteFlow.feature",
        monochrome = true,

        plugin = {"json:target/cucumber.json", "pretty"},
        glue = {"Helpers",
        "Setup.Initialization"})

public class SauceDemoRunner extends AbstractTestNGCucumberTests {


}