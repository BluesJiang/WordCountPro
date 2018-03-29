/**
 * com.hust.wcPro
 * Created by Blues on 2018/3/27.
 */

import java.util.HashMap;

public class Main {
    static public void main(String[] args) {

        IOController io_control = new IOController();
        
        String valid_file = io_control.get(args);
        
        WordCounter wordcounter = new WordCounter();
        
        HashMap<String, Integer> result = wordcounter.count(valid_file);

        io_control.save(result);

    }
}
