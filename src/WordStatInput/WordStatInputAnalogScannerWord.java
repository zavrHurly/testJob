package WordStatInput;

import AnalogScanner.AnalogScanner;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Objects;

public class WordStatInputAnalogScannerWord implements WordStatInput {

    private LinkedHashMap<String, Integer> returnMap = new LinkedHashMap<>();


    public void getStatistic(String inputFile, String outputFile) {
        try(AnalogScanner sc = new AnalogScanner(new File(inputFile), "UTF-8");
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outputFile), StandardCharsets.UTF_8))){
            while (sc.hasNextWord()) {
                wordToStatistic(sc.nextWord().toLowerCase());
            }
            for (String key: returnMap.keySet()) {
                writer.write(key + " " + returnMap.get(key) + "\n");
            }
        } catch (FileNotFoundException e) {
            System.out.println("file is not found");
        } catch (UnsupportedEncodingException e) {
            System.out.println("this encoding is not support");
        } catch(IOException e) {
            System.out.print(e.getMessage());
        }
    }

    public void wordToStatistic(String word){
        if(Objects.equals(word, "")) return;
        if (!returnMap.containsKey(word)) {
            returnMap.put(word, 1);
        } else {
            returnMap.put(word, returnMap.get(word) + 1);
        }
    }
}
