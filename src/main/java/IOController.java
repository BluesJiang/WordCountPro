
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.security.Principal;
import java.security.KeyStore.Entry;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

class IOController {
    IOController() {

    }

    /**
     * Parses the main function arguments
     * 
     * @param args the main function arguments
     * @return a valid file name
     */
    public String get(String[] args) {
        String filename = args[1];
        File file = new File(filename);
        if (!file.exists()) {
            System.out.println("file not exists");
            filename = "";
        }
        return filename;
    }

    /**
     * Saves the result sorted
     * 
     * @param result the result contain word as key as count as value
     * @return the state code of operation
     */
    public int save(HashMap<String, Integer> result) {
        Comparator<Map.Entry<String, Integer>> compareValue = new Comparator<Map.Entry<String,Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> obj1, Map.Entry<String, Integer> obj2) {
                return obj1.getValue() - obj2.getValue();
            }
        };
        ArrayList<Map.Entry<String, Integer>> countList = new ArrayList<Map.Entry<String, Integer>>(result.entrySet());
        countList.sort(compareValue);
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("result.txt"));
            for (Map.Entry<String, Integer> keyPair: countList) {
                String key = keyPair.getKey();
                Integer value = keyPair.getValue();
                writer.write(""+key+": "+value+"\n");
            }
            writer.close();
        } catch(IOException e) {
            e.printStackTrace();
            return -1;
        }
        
        // System.out.println(countList);
        return 0;
    }
}