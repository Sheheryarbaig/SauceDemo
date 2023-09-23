package Helpers.SauceDemo;

import Helpers.commonActions;

public class Login extends commonActions {

    commonActions actions;
    public Login() throws Exception {
        actions = new commonActions();
    }

    public void loginApp(String userName, String password) throws Exception {
        actions.clickBtn("Login", "Login");
        actions.enterText(userName, "Login","Username", "Login");
        actions.enterText(password, "Login","Password", "Login");
        actions.clickBtn("Login", "Login");
    }
}
