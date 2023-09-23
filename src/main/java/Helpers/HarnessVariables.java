package Helpers;

import Setup.Initialization.UtilFactory;
import io.restassured.response.Response;
import org.json.simple.JSONObject;

import java.beans.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HarnessVariables extends UtilFactory {
    public HarnessVariables() throws Exception {
    }
    //Database Connection Creds
    public static String dbConnectionString,
            dbUserName,
            dbPassword;

    public static String kWebprop,
            runPropFile="run.properties";
    public static String paymentid = "",
            applicationid="",
    VerificationFlag="",
            userid="",
            Activation_URL,
            Verification_Flag="",
            biometricStatus="",
            SMS_OTP = null,
            Email_OTP = null,
            NTN=null,
            biometricVerifiedStatus = "VERIFIED",
            fbrSTRN="";
}
