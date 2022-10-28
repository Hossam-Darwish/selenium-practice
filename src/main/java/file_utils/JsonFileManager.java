package file_utils;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class JsonFileManager {

    public static Object[][] readJsonData(String filePath,  String [] keys) throws IOException, ParseException {

        Object object = new JSONParser().parse(new FileReader(filePath)) ;
        JSONObject jsonObject = (JSONObject) object ;

        JSONArray jsonArrayCredentials = (JSONArray) jsonObject.get("userCredentials");
        Object[][] resultData = new Object[jsonArrayCredentials.size()][keys.length] ;
        for (int i = 0; i < jsonArrayCredentials.size() ; i++) {
            JSONObject jsonObject1 = (JSONObject) jsonArrayCredentials.get(i);
            for (int j = 0; j < keys.length ; j++) {
                resultData[i][j] = jsonObject1.get(keys[j]).toString()  ;
            }
        }
        return  resultData ;
    }
}
