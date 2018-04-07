

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.json.JSONException;
import org.json.JSONObject;

class UnitTest {

    UnitTest() {}

    private String getTestResourcePath() {
        String path = "build/resources/test/";
        String osName = System.getProperty("os.name").toLowerCase();
        if (osName.startsWith("win")) {
            path.replace('/', '\\');
        }
        return path;
    }

    @Test
    void testSortMap() {
        IOController io_controller = new IOController();
        HashMap<String, Integer> testMap = new HashMap<>();
        String jsonStr = "";
        try {
            BufferedReader reader = new BufferedReader(new FileReader(getTestResourcePath()+"sortmap.json"));
            String line = reader.readLine();
            while(line != null) {
                jsonStr += line;
                line = reader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            JSONObject jsonObject = new JSONObject(jsonStr);
            Iterator<String> keyIterator = jsonObject.keys();
            while (keyIterator.hasNext()) {
                String key = keyIterator.next();
                Integer value = jsonObject.getInt(key);
                testMap.put(key, value);
            }

        } catch (JSONException e){
            e.printStackTrace();
        }
        
        int res = io_controller.save(testMap);
        assertEquals(0, res);

    }

    @Test
    @DisplayName("Custom test name containing spaces")
    void testIOHandling() {
        IOController io_controller = new IOController();
        String[] args = {"./build/resources/test/usecase1.txt","10023"};
        String res = io_controller.get(args);
        String exp = args[0];
        File f = new File(args[0]);
        if (!f.exists()) {
            exp = "";
        }
        assertEquals(exp, res);
    }
}