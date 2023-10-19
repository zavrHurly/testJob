package AnalogScanner;

import java.io.*;
import java.nio.charset.Charset;

public class AnalogScanner implements AutoCloseable {

    private final InputStreamReader reader;

    private char[] buffer = new char[512];

    private int index = 0;

    private int itemsInBuffer = 0;

    private final char lineSeparator = '\n';

    public AnalogScanner(InputStream src, String charsetName) throws UnsupportedEncodingException {
        this.reader = new InputStreamReader(src, charsetName);
    }

    public AnalogScanner(File file, String charsetName) throws IOException {
        this.reader = new FileReader(file, Charset.forName(charsetName));
    }

    public void close() throws IOException {
        reader.close();
    }

    public String next() throws IOException {
        final StringBuilder text = new StringBuilder();
        beginToString();
        while (!endNext()) {
            text.append(getCurrentChar());
            toNextChar();
            if (!hasNext()) {
                break;
            }
        }
        return text.toString();
    }

    public String nextWord() throws IOException {
        final StringBuilder text = new StringBuilder();
        beginToWord();
        while(checkWord()) {
            text.append(getCurrentChar());
            toNextChar();
            if (!hasNext()) {
                break;
            }
        }
        return text.toString();
    }

    public String nextLine() throws IOException {
        final StringBuilder text = new StringBuilder();
        beginToString();
        while (!endLine()) {
            text.append(getCurrentChar());
            toNextChar();
            if (!hasNext()) {
                break;
            }
        }
        toNextLine();
        return text.toString();
    }

    public int beginToWord() throws IOException {
        while (!checkWord()) {
            if(!checkEndBuf()) toNextChar();
        }
        return index;
    }

    public boolean hasNextWord() throws IOException {
        return beginToWord() < itemsInBuffer;
    }

    private void beginToString() throws IOException {
        while (endNext()) {
            toNextChar();
        }
    }

    private void toNextChar() throws IOException {
        index++;
        checkEndBuf();
    }

    private boolean checkEndBuf() throws IOException {
        if (index < itemsInBuffer) {
            return false;
        } else {
            updateBuffer();
            return true;
        }
    }

    private void updateBuffer() throws IOException {
        index = 0;
        itemsInBuffer = reader.read(buffer);
    }

    private boolean endNext() {
        return Character.isWhitespace(getCurrentChar()) || getCurrentChar() == lineSeparator;
    }

    private boolean checkWord(){
        return Character.isLetter(getCurrentChar()) || getCurrentChar() == '\'' || Character.DASH_PUNCTUATION == Character.getType(getCurrentChar());
    }

    private char getCurrentChar() {
        return buffer[index];
    }

    public boolean endLine() {
        return getCurrentChar() == lineSeparator || index >= itemsInBuffer;
    }

    public void toNextLine() throws IOException {
        if(buffer[index] == lineSeparator) index++;
        checkEndBuf();
    }

    public boolean hasNext() throws IOException {
        checkEndBuf();
        return index < itemsInBuffer;
    }
}
