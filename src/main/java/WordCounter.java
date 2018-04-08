import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.io.*;
import java.lang.String;

/**
 * The {@code WordCounter} class is a multi-purpose text file
 * counter, which can judge a legal word and count word numbers,
 * to put the result to a {@code HashMap}.
 * <br><br>
 *
 * @author VectorLu
 * @author YangLeee
 * @since JDK1.8
 */

public class WordCounter {

    /**
     * Reserve the default {@code constructor}.
     */
    WordCounter() {
    }

    /**
     * Return whether the argument is in the English alphabet.
     * @param c
     * @return {@code true} if the argument is in the English alphabet,
     * otherwise {@code false}.
     */
    private boolean isEngChar(char c){
        return ((c >= 'a' &&c <= 'z' )||(c >= 'A' && c<='Z'));
        
    }

    /**
     * Return whether the argument is hyphen, which is {@code -}.
     * @param c
     * @return {@code true} if the argument is hyphen
     * otherwise {@code false}.
     */
    private boolean isHyphen(char c){
        return (c == '-');
        
    }

    /**
     * Counts the words in the specific file
     * 
     * @param filename the file to be counted
     * @return the result saves the word(lowercase) as key and count as value
     */
    public HashMap<String, Integer> count(String filename) {
        FileReader reader = null;
        HashMap<String , Integer> wMap = new HashMap<String, Integer>();
        try {
                File file = new File(filename);
                reader = new FileReader(file);
                //nowChar represent now readFile character
                int nowChar = reader.read();    
                while (nowChar != -1) {
                    //nowWord represent now word
                    String nowWord = "";        
                    // skip char that isn't hyphen or isn't in English Alphabet 
                    while (nowChar != -1 && !(isEngChar((char)nowChar)) && !(isHyphen((char)nowChar))){
                        nowChar = reader.read();
                    }
                    // judge a legal word
                    while (nowChar != -1 && (isEngChar((char)nowChar) || isHyphen((char)nowChar))){
                        if (isHyphen((char)nowChar)) {
                            nowChar = reader.read();
                            if (isEngChar((char)nowChar)) {
                                if ("".equals(nowWord)) {
                                    nowWord += String.valueOf((char)nowChar) ; 
                                } else {
                                    nowWord += "-" + String.valueOf((char)nowChar) ;
                                }    
                                nowChar = reader.read();
                            } else {
                                break;
                            }      
                        }
                        if (isEngChar((char)nowChar)) {
                            nowWord += String.valueOf((char)nowChar);
                            nowChar = reader.read();
                        }
                    }
                    nowWord = nowWord.toLowerCase();
                    if (wMap.containsKey(nowWord))
                        wMap.put(nowWord, wMap.get(nowWord) + 1);
                    else if (!"".equals(nowWord)) {
                        wMap.put(nowWord, 1);
                    }
                }
                reader.close();
        }
        catch(IOException e) {
            e.printStackTrace();
        }
        return wMap;
    }
}