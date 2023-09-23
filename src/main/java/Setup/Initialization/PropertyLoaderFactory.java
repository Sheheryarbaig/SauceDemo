package Setup.Initialization;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.util.Properties;


public class PropertyLoaderFactory {

    private Properties properties;
    private String propertiesPath;

    public Properties getPropertyFile(String filename) throws Exception {

        String path = null;

        try {

            path = System.getProperty("user.dir") + File.separator + "src/main/java/Configuration/";

        } catch (Exception e) {

            System.err.println("Could not locate .properties file");
            e.printStackTrace();
        }
        return getProperty(path, filename);
    }

    public Properties getTestDataPropertyFile(String filename) throws Exception {

        String path = null;

        try {

            path = System.getProperty("user.dir") + File.separator + "src/main/java/TestData/";

        } catch (Exception e) {

            System.err.println("Could not locate Test Data properties file");
            e.printStackTrace();
        }
        return getProperty(path, filename);
    }
    public Properties getSQLPropertyFile(String filename) throws Exception {

        String path = null;

        try {

            path = System.getProperty("user.dir") + File.separator + "src/main/java/Resources/SqlQueries/";

        } catch (Exception e) {

            System.err.println("Could not locate Test Data properties file");
            e.printStackTrace();
        }
        return getProperty(path, filename);
    }

    public Properties getEnvironmentPropertyFile(String filename) throws Exception {

        String path = null;

        try {

            path = System.getProperty("user.dir") + File.separator + "src/main/java/Configuration/";

        } catch (Exception e) {

            System.err.println("Could not locate Test Data properties file");
            e.printStackTrace();
        }
        return getProperty(path, filename);
    }

    public Properties getLocatorPropertyFile(String filename) throws Exception {

        String path = null;

        try {

            path = System.getProperty("user.dir") + File.separator + "src/main/java/Locators/";

        } catch (Exception e) {

            System.err.println("Could not locate Locators properties file");
            e.printStackTrace();
        }
        return getProperty(path, filename);
    }

    public static JSONObject getRequestFile(String filename) throws Exception {

        String path = null;

        try {

            path = System.getProperty("user.dir") + File.separator + "src/main/java/Resources/APIsJSONs/Requests/";

        } catch (Exception e) {

            System.err.println("Could not locate JSON file");
            e.printStackTrace();
        }
        return getJson(path, filename);
    }

    public JSONObject getResponseFile(String filename) throws Exception {

        String path = null;

        try {

            path = System.getProperty("user.dir") + File.separator + "src/main/java/Resources/APIsJSONs/Responses/";

        } catch (Exception e) {

            System.err.println("Could not locate JSON file");
            e.printStackTrace();
        }
        return getJson(path, filename);
    }

    public Properties getProperty(String path, String filename) throws Exception {

        propertiesPath = path;

        try {

            File file;

            file = new File(propertiesPath + filename);

            FileInputStream fileInput = new FileInputStream(file);
            properties = new Properties();
            properties.load(fileInput);

        } catch (Exception e) {

            System.err.println("Could not get Properties from the .properties file");
            e.printStackTrace();
            throw e;
        }
        return properties;
    }

    public static JSONObject getJson(String path, String filename) throws Exception {
        filename = filename + ".json";
        JSONParser parser = new JSONParser();
        JSONObject jsonObject;
        try {
            File file;
            file = new File(path + filename);
            Object obj = parser.parse(new FileReader(file));
            jsonObject = (JSONObject) obj;
        } catch (Exception e) {
            System.err.println("Could not get JSON from the JSON file");
            e.printStackTrace();
            throw e;
        }
        return jsonObject;
    }
    public static String convertJsonToFormParameters(JSONObject jsonObject) {
        StringBuilder formParams = new StringBuilder();
        for (Object key : jsonObject.keySet()) {
            String value = jsonObject.get(key).toString();
            formParams.append(key).append("=").append(value).append("&");
        }
        // Remove the trailing "&" character
        if (formParams.length() > 0) {
            formParams.deleteCharAt(formParams.length() - 1);
        }
        return formParams.toString();
    }
}
