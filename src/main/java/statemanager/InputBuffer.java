package statemanager;


import server.TheServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class InputBuffer {

    private static InputBuffer _instance = null;
    private String[] buffer = new String[4];
    private int bufferSlot = 0;

    /* This class implements the singleton design pattern
     */
    public static InputBuffer get_instance() {

        if (_instance == null) {
            _instance = new InputBuffer();
        }
        return _instance;
    }

    public static String readStream(InputStream is) throws IOException{
        StringBuilder stringBuilder = new StringBuilder();
        String line;

        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
        }
        return stringBuilder.toString();
    }

    /* Calls readStream on input stream, then pushes it into a buffer slot.
     * Return slot number of the slot that the string was inserted into.
     */
    public int streamToBuffer(InputStream is) throws IOException {
        String commands = readStream(is);
        int slot = push(commands);
        return slot;
    }

    /* Insert string into buffer. Return number of slot that the string was inserted into.
     */
    private int push(String s) {
        int slot = bufferSlot;
        buffer[bufferSlot] = s;
        bufferSlot++;
        return slot;
    }

    /* Empty buffer for next round of input.
     */
    protected void flush() {
        Arrays.fill(buffer, null);
        bufferSlot = 0;
    }

    protected String[] getBuffer() {
        return buffer;
    }
}
