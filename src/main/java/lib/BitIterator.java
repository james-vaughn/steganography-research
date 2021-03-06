package lib;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * BitIterator allows iterating over bits sequentially from an input string
 * Since java does not allow bits, bytes are substituted
 */
public class BitIterator implements Iterator {
    private List<Byte> message;
    private Byte currentByte;
    private int bitsIteratedInByte = 0;
    public static final int BITS_IN_A_BYTE = 8;
    public static final char END_DELIMITER = '\0';
    public int bytesIterated;

    public BitIterator(String message) throws UnsupportedEncodingException {
        this.message = new ArrayList<>();

        for (char c : message.toCharArray()) {
            this.message.add((byte) c);
        }

        this.message.add((byte) END_DELIMITER);

        if (this.message.size() > 0) {
            this.currentByte = this.message.remove(0);
        }
    }

    @Override
    public boolean hasNext() {
        return !message.isEmpty() || (bitsIteratedInByte < 7);
    }

    //Returns the next bit in the message
    //Returns in the form of a byte due to necessity
    @Override
    public Byte next() {
        if(hasNext() == false) return null;

        int b = currentByte >> (BITS_IN_A_BYTE - (bitsIteratedInByte + 1));
        b = b & 0x01;

        bitsIteratedInByte ++;

        if (bitsIteratedInByte >= BITS_IN_A_BYTE) {
            currentByte = message.remove(0);
            bitsIteratedInByte = 0;
            bytesIterated++;
        }

        return (byte) b;
    }

    public String toString() {
        StringBuilder msg = new StringBuilder();

        for(byte b : message) {
            msg.append((char)b);
        }
        return msg.toString();
    }
}