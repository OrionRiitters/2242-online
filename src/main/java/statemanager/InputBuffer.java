package statemanager;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
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

    /* Transform InputStream into String, then push String to buffer.
     * Return slot that String was inserted into.
     */
    public int streamToBuffer(InputStream is) throws IOException {
        String commands = readStream(is);
        int slot = push(commands);
        return slot;
    }

    private String readStream(InputStream is) throws IOException{
        StringBuilder stringBuilder = new StringBuilder();
        String line = null;

        /* Not sure if the while statement below will close the stream early or not.
        */
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
        }
        return stringBuilder.toString();
    }

    /* Insert string into buffer. Return slot that string was inserted into.
     */
    private int push(String s) {
        int slot = bufferSlot;
        buffer[bufferSlot] = s;
        bufferSlot++;
        return slot;
    }

    protected String[] getBuffer() {
        return buffer;
    }

    /* Empty buffer for next round of input.
    */
    protected void flush() {
        Arrays.fill(buffer, null);
        bufferSlot = 0;
    }



}
