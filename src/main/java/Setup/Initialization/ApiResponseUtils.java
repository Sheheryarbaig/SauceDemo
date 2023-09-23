package Setup.Initialization;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;


public class ApiResponseUtils {
    private static JSONObject testData;
    // Method to save the API response to a JSON file
    public static void saveResponseToJsonFile(Response response, String fileName) {
        try {
            String responseBody = response.getBody().asString();
            JSONObject jsonResponse = (JSONObject) new JSONParser().parse(responseBody);
            FileWriter file = new FileWriter("src/main/java/Resources/APIsJSONs/Responses/"+fileName+".json");
            file.write(jsonResponse.toJSONString());
            file.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String loadDataFromJson(String scenarioKey, String jsonFilePath) throws Exception {
        String path = "src/main/java/Resources/APIsJSONs/Responses/"+jsonFilePath+".json";

        File file = new File(path);
        JsonPath jsonPath = new JsonPath(file);

        String keyValue = jsonPath.get(scenarioKey);

        if (keyValue == null) {
            throw new IllegalArgumentException("Key value not found in the JSON file.");
        }

        return keyValue;
    }
}
