package GreenBlue;

import lib.TestingLib;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.io.IOException;

import spatial.GreenBlue.GreenBlueEncoder;

public class GreenBlueEncoderTest {

    private TestingLib testingLib = new TestingLib();

    @Test
    public void encodeDecode_nominal1() throws IOException {
        String inputImgPath = testingLib.inputImagesDir + "green_blue_input_1.png";
        String outputImgPath = testingLib.outputImagesDir + "green_blue_output_1.png";
        int secretKey = 27353474;
        String input = "Test Message";
        String expected = "Test Message";
        GreenBlueEncoder.encode(inputImgPath, outputImgPath, input, secretKey);
        String result = GreenBlueEncoder.decode(outputImgPath, secretKey);
        assertTrue(result.equals(expected));
    }

    @Test
    public void encodeDecode_nominal2() throws IOException {
        String inputImgPath = testingLib.inputImagesDir + "green_blue_input_1.png";
        String outputImgPath = testingLib.outputImagesDir + "green_blue_output_1.png";
        int secretKey = 5354363;
        String input = "Garbage Characters: {)(*jk2&fjs98j2rgdsg32j2@#2j15sd(*kjd9";
        String expected = "Garbage Characters: {)(*jk2&fjs98j2rgdsg32j2@#2j15sd(*kjd9";
        GreenBlueEncoder.encode(inputImgPath, outputImgPath, input, secretKey);
        String result = GreenBlueEncoder.decode(outputImgPath, secretKey);
        assertTrue(result.equals(expected));
    }

    @Test
    public void encode_decode_short() throws IOException {
        String inputImgPath = testingLib.inputImagesDir + "green_blue_input_1.png";
        String outputImgPath = testingLib.outputImagesDir + "green_blue_output_1.png";
        int secretKey = 725256;
        String input = "A";
        String expected = "A";
        GreenBlueEncoder.encode(inputImgPath, outputImgPath, input, secretKey);
        String result = GreenBlueEncoder.decode(outputImgPath, secretKey);
        assertTrue(result.equals(expected));
    }

    @Test
    public void encodeDecode_largeMessage() throws IOException {
        String inputImgPath = testingLib.inputImagesDir + "green_blue_input_1.png";
        String outputImgPath = testingLib.outputImagesDir + "green_blue_output_1.png";
        String inputMesPath = testingLib.inputMessagesDr + "1984 reduced-size.txt";
        String input = testingLib.readTextFile(inputMesPath);
        int secretKey = 135498;
        GreenBlueEncoder.encode(inputImgPath, outputImgPath, input, secretKey);
        String result = GreenBlueEncoder.decode(outputImgPath, secretKey);
        assertTrue(result.equals(input));
    }

    @Test
    public void scrambleMessage_nominal() {
        /*
        ASCII   Binary    Scrambled  Decimal Val
        T  84   01010100  00101010   42
        e  101  01100101  10100110   166
        s  115  01110011  11001110   206
        t  116  01110100  00101110   46
        '' 32   00100000  00000100   4
        M  77   01001101  10110010   178
        e  101  01100101  10100110   166
        s  115  01110011  11001110   206
        s  115  01110011  11001110   206
        a  97   01100001  10000110   134
        g  103  01100111  11100110   230
        e  101  01100101  10100110   166
                00000000  00000000   0
         */
        String inputMes = "Test Message";
        String expected = "*¦Î.\u0004²¦ÎÎ\u0086æ¦\u0000";
        String result = GreenBlueEncoder.scrambleMessage(inputMes);
        assertTrue(expected.equals(result));
    }

    @Test
    public void scrambleMessage_short() {
        String inputMes = "A";
        String expected = "\u0082\u0000";
        String result = GreenBlueEncoder.scrambleMessage(inputMes);
        assertTrue(expected.equals(result));
    }

    @Test
    public void unscrambleMessage_nominal() {
        String input = "*¦Î.\u0004²¦ÎÎ\u0086æ¦\u0000";
        String expected = "Test Message";
        String result = GreenBlueEncoder.unscrambleMessage(input);
        assertTrue(expected.equals(result));
    }

    @Test
    public void scrambleUnscrambleMessage_nominal() {
        String expected = "Custom message for testing";
        String scrambled = GreenBlueEncoder.scrambleMessage(expected);
        String unscrambled = GreenBlueEncoder.unscrambleMessage(scrambled);
        assertTrue(unscrambled.equals(expected));
    }

    @Test
    public void scrambleUnscrambleMessage_8bitChars() {
        String expected = "\u0082\u0000";
        String scrambled = GreenBlueEncoder.scrambleMessage(expected);
        String unscrambled = GreenBlueEncoder.unscrambleMessage(scrambled);
        assertTrue(unscrambled.equals(expected));
    }

    @Test
    public void swapBits_bitsAreTheSame() {
        int input_num = Integer.parseInt("0100100111", 2); // decimal value is 295
        int pos_1 = 0;
        int pos_2 = 2;
        // Both of the bits in position 1 and 9 are 1, so the input number should be unchanged
        assertEquals(295, GreenBlueEncoder.swapBits(input_num, pos_1, pos_2));
    }

    @Test
    public void swapBits_bitsAreDifferent() {
        int input_num = Integer.parseInt("0100100111", 2); // decimal value is 295
        int pos_1 = 2;
        int pos_2 = 7;
        assertEquals(419, GreenBlueEncoder.swapBits(input_num, pos_1, pos_2));
    }

    @Test
    public void getBitAt_nominal1() {
        int input_num = Integer.parseInt("0100100111", 2);
        int input_pos = 0;
        assertEquals(1, GreenBlueEncoder.getBitAt(input_num, input_pos));
    }

    @Test
    public void getBitAt_nominal2() {
        int input_num = Integer.parseInt("0100100111", 2);
        int input_pos = 3;
        assertEquals(0, GreenBlueEncoder.getBitAt(input_num, input_pos));
    }
}
