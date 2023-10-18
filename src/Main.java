import WordStatInput.WordStatInputAnalogScanner;
import WordStatInput.WordStatInputAnalogScannerWord;
import WordStatInput.WordStatInputScanner;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        WordStatInputAnalogScannerWord analogScannerNew = new WordStatInputAnalogScannerWord();
        analogScannerNew.getStatistic(args[0], args[1]);
    }
}