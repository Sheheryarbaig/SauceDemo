package Setup.Initialization;

public class webdriverInitialization extends UtilFactory {
    public webdriverInitialization() throws Exception {
        serviceFactoryInstance.setDriver(serviceFactoryInstance.getBrowser());
        deviceName = "WEB";
        waitFactory = new WaitFactory(ServiceFactory.getDriver());
        elementFactory = new ElementFactory(ServiceFactory.getDriver());
    }
}
