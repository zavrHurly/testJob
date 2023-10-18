package WordStatInput;

import AnalogScanner.AnalogScanner;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import java.util.LinkedHashMap;
import java.util.Objects;

public class WordStatInputAnalogScanner implements WordStatInput {

    public void getStatistic(String inputFile, String outputFile) {
        LinkedHashMap<String, Integer> returnMap = new LinkedHashMap<>();

        try (AnalogScanner sc = new AnalogScanner(new File(inputFile), "UTF-8");
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outputFile), StandardCharsets.UTF_8))) {
            while(sc.hasNext()) {
                String text = sc.next().toLowerCase();
                StringBuffer buffer = new StringBuffer();
                StringCharacterIterator iterator = new StringCharacterIterator(text);

                char character = iterator.current();
                while (character != CharacterIterator.DONE) {
                    addCharToBuffer(character, buffer);
                    character = iterator.next();
                }

                createStatistic(buffer, returnMap);
            }
            for (String key: returnMap.keySet()) {
                writer.write(key + " " + returnMap.get(key) + "\n");
            }
        } catch (FileNotFoundException e) {
            System.out.println("file is not found");
        } catch (UnsupportedEncodingException e) {
            System.out.println("this encoding is not support");
        } catch (IOException e) {
            System.out.print(e.getMessage());
        }
    }

    private void addCharToBuffer(char character, StringBuffer buffer) {
        if (Character.isLetter(character) || character == '\'' || Character.DASH_PUNCTUATION == Character.getType(character)) {
            buffer.append(character);
        } else {
            buffer.append(' ');
        }
    }

    private void createStatistic(StringBuffer buffer, LinkedHashMap<String, Integer> returnMap) {
        String[] words = buffer.toString().split(" ");
        for (String word: words) {
            if (word == null || Objects.equals(word, "")) continue;

            if (!returnMap.containsKey(word)) {
                returnMap.put(word, 1);
            } else {
                returnMap.put(word, returnMap.get(word) + 1);
            }
        }
    }
}