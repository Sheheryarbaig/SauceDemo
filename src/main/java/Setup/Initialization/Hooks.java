package Setup.Initialization;

import io.cucumber.java.Before;
import io.cucumber.java.Scenario;

import static Setup.Initialization.UtilFactory.*;

public class Hooks {

    @Before
    public void beforeTest(Scenario scenario) {
        scenarioName=scenario.getName();
        scenarioDef = features.createNode(scenarioName);
    }
}
