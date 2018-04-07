import static org.junit.jupiter.api.Assertions.assertEquals;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

import org.junit.Rule;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.rules.ExpectedException;

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
//    @DisplayName("Custom test name containing spaces")
    @DisplayName("Custom test file that doesn't exist")
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

    /**
     * Use reflection to test {@code private} method {@isEngChar()}
     */
    @Test
    void testIsEngChar() {
        /* a English Alphabet */
        final char[] ALPHABET = new char[52];

        final char[] NOT_ALPHABET = {',', '&', '@', '\\', '/'};

        char ch = 'a';
        for (int i = 0; i < 26; i++) {
            ALPHABET[i] = ch;
            ch++;
        }
        ch = 'A';
        for (int i = 26; i < 52; i++) {
            ALPHABET[i] = ch;
            ch++;
        }

        /* get a {@code Class} object of {@code WordCounter} */
        Class<WordCounter> classOfWordCounter = WordCounter.class;
        try {
            /* get an instance of target class */
            Object wcInstance = classOfWordCounter.newInstance();
            try {
                Method privateMethod = classOfWordCounter.getDeclaredMethod("isEngChar", char.class);
                privateMethod.setAccessible(true);
                for (char letter: ALPHABET) {
                    try {
                        boolean result = (Boolean) privateMethod.invoke(wcInstance, new Object[]{letter});
                        assertEquals(true, result);
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
                for (char notLetter: NOT_ALPHABET) {
                    try {
                        boolean result = (Boolean) privateMethod.invoke(wcInstance, new Object[]{notLetter});
                        assertEquals(false, result);
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }

            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * Use reflection to test {@code private} method {@isHyphen()}
     */
    @Test
    void testIsHyphen() {
        final char hyphen = '-';
        final char[] notHyphenChars = {'a', 'b', ' ', '/'};
        /* get a {@code Class} object of {@code WordCounter} */
        Class<WordCounter> classOfWordCounter = WordCounter.class;
        /* get an instance of target class */
        try {
            Object wcInstance = classOfWordCounter.newInstance();
            try {
                Method isHyphen = classOfWordCounter.getDeclaredMethod("isHyphen", char.class);
                isHyphen.setAccessible(true);
                try {
                    boolean result = (Boolean) isHyphen.invoke(wcInstance, new Object[]{hyphen});
                    assertEquals(true, result);
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
                for (char ch: notHyphenChars) {
                    try {
                        boolean result = (Boolean) isHyphen.invoke(wcInstance, new Object[]{ch});
                        assertEquals(false, result);
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Rule
    private ExpectedException exception = ExpectedException.none();

    String fileParentPath = "src/test/resources/";

    @Test
    void testCountFileNotFoundException() {
        String fileName = "fileNotExist";
        String relativePath = fileParentPath + fileName;
        WordCounter wc = new WordCounter();
        HashMap result = wc.count(relativePath);
        exception.expect(IOException.class);
        assertEquals(0, result.size());
    }

    @Test
    void testCountEmptyFile() {
        String fileName = "emptyFile.txt";
        String relativePath = fileParentPath + fileName;
        WordCounter wc = new WordCounter();
        HashMap result = wc.count(relativePath);
        assertEquals(0, result.size());
    }

    @Test
    @DisplayName("Border test: wc.count(endWithHyphen.txt)")
    void testCountFileEndWithHyphen() {
        String fileName = "endWithHyphen.txt";
        String relativePath = fileParentPath + fileName;
        WordCounter wc = new WordCounter();
        HashMap result = wc.count(relativePath);
        assertEquals(1, result.size());
    }
}