
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.io.*;
import java.lang.String;

public class WordCounter {
    
    WordCounter() {
    }

    /**
     * Counts the words in the specific file
     * 
     * @param filename the file to be counted
     * @return the result saves the word(lowercased) as key and count as value
     */
    private boolean isNumber(char c){
        if(c >= '0'&& c <= '9')
            return true;
        return false;
    }

    private boolean isEngChar(char c){
        if((c >= 'a' &&c <= 'z' )||(c >= 'A' && c<='Z'))
            return true;
        return false;
    }

    private boolean isStrigula(char c){
        if(c == '-')
            return true;
        return false;
    }
    public HashMap<String, Integer> count(String filename) {
        FileReader reader = null;
        HashMap<String , Integer> wMap = new HashMap<String, Integer>();
        try{
                File file =new File(filename);
                reader = new FileReader(file);
                int nowChar = reader.read();    //nowChar represent now readFile character
                while(nowChar != -1){
                    String nowWord = "";        //nowWord represent now word
                    while(!(isEngChar((char)nowChar)) && !(isStrigula((char)nowChar)) && nowChar != -1){
                        nowChar = reader.read();
                    }
                    while(nowChar != -1 && (isEngChar((char)nowChar) || isStrigula((char)nowChar))){
                        if(isStrigula((char)nowChar)){
                            nowChar = reader.read();
                            if(nowChar != -1 && isEngChar((char)nowChar)){
                                if(nowWord.equals(""))
                                    nowWord += String.valueOf((char)nowChar) ; 
                                else
                                    nowWord += "-" + String.valueOf((char)nowChar) ; 
                                nowChar = reader.read();
                            }
                                
                            else 
                                break;
                        }

                        nowWord += String.valueOf((char)nowChar);
                        nowChar = reader.read();
                    }
                    nowWord = nowWord.toLowerCase();
                    if(wMap.containsKey(nowWord))
                        wMap.put(nowWord, wMap.get(nowWord) + 1);
                    else 
                        wMap.put(nowWord, 1);
                }
                reader.close();
        }
        catch(IOException e){
            e.printStackTrace();
        }
        return wMap;
    }
}