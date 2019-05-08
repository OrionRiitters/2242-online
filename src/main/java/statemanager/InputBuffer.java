package statemanager;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Arrays;

public class InputBuffer {

    private static InputBuffer ib_instance = null;
    private String[] buffer = new String[4];
    private int bufferSlot = 0;

    /* This class implements the singleton design pattern
    */
    public static InputBuffer InputBuffer() {

        if (ib_instance == null) {
            ib_instance = new InputBuffer();
        }
        return ib_instance;
    }

    private String readStream(InputStream is, Charset cs) throws IOException{
        StringBuilder stringBuilder = new StringBuilder();
        String line = null;

        /* Not sure if the while statement below will close the stream early or not.
        */
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is, cs))) {
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }

        }
        return stringBuilder.toString();
    }

    private void push(String s) {
        buffer[bufferSlot] = s;
        bufferSlot++;
    }

    /* Empty buffer for next round of input.
    */
    private void flush() {
        Arrays.fill(buffer, null);
        bufferSlot = 0;
    }



}
