package statemanager;


import server.TheServer;

import java.io.IOException;
import java.io.InputStream;
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

    /* Transform InputStream into String, then push String to buffer.
     * Return slot that String was inserted into.
     */
    public int streamToBuffer(InputStream is) throws IOException {
        String commands = TheServer.readStream(is);
        int slot = push(commands);
        return slot;
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
