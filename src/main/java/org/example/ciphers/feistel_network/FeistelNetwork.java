package org.example.ciphers.feistel_network;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class FeistelNetwork {
    private static final Integer[] POSITION_IP_OPEN_TEXT = {
            58,50,42,34,26,18,10,2,
            60,52,44,36,28,20,12,4,
            62,54,46,38,30,22,14,6,
            64,56,48,40,32,24,16,8,
            57,49,41,33,25,17, 9,1,
            59,51,43,35,27,19,11,3,
            61,53,45,37,29,21,13,5,
            63,55,47,39,31,23,15,7,
    };

    private static final Integer[] POSITION_56_KEY = {
            57,49,41,33,25,17, 9, 1,58,50,42,34,26,18,
            10, 2,59,51,43,35,27,19,11, 3,60,52,44,36,
            63,55,47,39,31,23,15, 7,62,54,46,38,30,22,
            14, 6,61,53,45,37,29,21,13, 5,28,20,12, 4
    };

    private static final Integer[] SHIFT = {
            1,1,2,2,2,2,2,2,1,2,2,2,2,2,2,1
    };

    private static final Integer[] COMPLETED_POSITION_KEY = {
            14,17,11,24,1,5,3,28,
            15,6,21,10,23,19,12,4,
            26,8,16,7,27,20,13,2,
            41,52,31,37,47,55,30,40,
            51,45,33,48,44,49,39,56,
            34,53,46,42,50,36,29,32,
    };

    private static final Integer[] EXPAND_RIGHT_SIDE_FROM_32_TO_48 = {
            32, 1, 2, 3, 4, 5,
             4, 5, 6, 7, 8, 9,
             8, 9,10,11,12,13,
            12,13,14,15,16,17,
            16,17,18,19,20,21,
            20,21,22,23,24,25,
            24,25,26,27,28,29,
            28,29,30,31,32, 1
    };

    private static final Integer[][] S1_SK = {
            {
                14,4,13,1,2,15,11,8,3,10,6,13,5,9,0,7,
                0,15,7,4,14,2,13,1,10,6,12,11,9,5,3,8,
                4,1,14,8,13,6,2,11,15,12,9,7,3,10,5,0,
                15,12,8,2,4,9,1,7,5,11,3,14,10,0,6,13,
            },
            {
                15,1,8,14,6,11,3,4,9,7,2,13,12,0,5,10,
                3,13,4,7,15,2,8,14,12,0,1,10,6,9,11,5,
                0,14,7,11,10,4,13,1,5,8,12,6,9,3,2,15,
                13,8,10,1,3,15,4,2,11,6,7,12,0,5,14,9
            },
            {
                10,0,9,14,6,3,15,5,1,13,12,7,11,4,2,8,
                13,7,0,9,3,4,6,10,2,8,5,14,12,11,15,1,
                13,6,4,9,8,15,3,0,11,1,2,12,5,10,14,7,
                1,10,13,0,6,9,8,74,5,14,3,11,5,2,12
            },
            {
                7,13,14,3,0,6,9,10,1,2,8,5,11,12,4,15,
                13,8,11,5,6,15,0,3,4,7,2,12,1,10,14,9,
                10,6,9,0,12,11,7,13,7,8,15,9,12,5,6,3,
                11,8,12,7,1,14,2,13,6,15,0,9,10,4,5,3
            },
            {
                2,12,4,1,7,10,11,6,8,5,3,15,13,3,4,14,7,5,11,
                14,11,2,12,4,7,13,1,5,0,15,10,3,9,8,6,
                4,2,1,11,10,13,7,8,15,9,12,5,6,3,0,14,
                11,8,12,7,1,14,2,13,6,15,0,9,10,4,5,3
            },
            {
                12,1,10,14,9,2,6,8,0,13,3,4,14,7,5,11,
                14,11,2,12,4,7,13,1,5,0,15,10,3,9,8,6,
                9,14,15,5,2,8,12,3,7,0,4,10,1,13,11,6,
                4,3,2,12,9,5,15,10,11,14,1,7,6,0,8,13
            },
            {
                4,11,2,14,15,0,8,13,3,12,9,7,5,10,6,1,
                13,0,11,7,4,9,1,10,14,3,5,12,2,15,8,6,
                1,4,11,13,12,3,7,14,10,15,6,8,0,5,9,2,
                6,11,13,8,1,4,10,7,9,5,0,15,14,2,3,12
            },
            {
                13,2,8,4,6,15,11,1,10,9,3,14,5,0,12,7,
                1,14,13,8,10,3,7,4,12,5,6,11,0,14,9,2,
                7,11,4,1,9,12,14,2,0,6,10,13,15,3,5,8,
                2,1,14,7,4,10,8,13,15,12,9,0,3,5,6,11
            }
    };

    private final static Integer[] FINAL_CHANGE_POSITIONS = {
            16, 7,20,21,29,12,28,17,
            1,15,23,26,5,18,31,10,
            2,8,24,14,32,27,3,9,
            19,13,30,6,22,11,4,25
    };

    public String encrypt(String text, String key) {
        String binaryText = convertToBinary(text);

        String textAfterIpOpenText = changePositions(POSITION_IP_OPEN_TEXT, binaryText);

        String L0 = textAfterIpOpenText.substring(0, 32);
        String R0 = textAfterIpOpenText.substring(32);
        String R0Expanded = changePositions(EXPAND_RIGHT_SIDE_FROM_32_TO_48, R0);


        String binaryKey = convertToBinary(key);
        String binaryKeyPrepare56Bites = changePositions(POSITION_56_KEY, binaryKey);
        String shiftedKey56LS = shiftKey(binaryKeyPrepare56Bites.substring(0, 28), 0);
        String shiftedKey56RS = shiftKey(binaryKeyPrepare56Bites.substring(28), 0);
        String shiftedKey = shiftedKey56LS + shiftedKey56RS;

        String keyFirstRound = changePositions(COMPLETED_POSITION_KEY, shiftedKey);

        String xorFirstKeyAndR0 = xorOperation(48, keyFirstRound, R0Expanded);

        List<String> s1skXor = new ArrayList<>();
        for (int i = 0; i < 48; i += 6) {
            s1skXor.add(xorFirstKeyAndR0.substring(i, i + 6));
        }

        int s1skCounter = 0;
        StringBuilder block32bites = new StringBuilder();
        for (String s1skElement: s1skXor) {
            String rowBinaryValue = s1skElement.charAt(0) + s1skElement.substring(s1skElement.length() - 1);
            String columnBinaryValue = s1skElement.substring(1, s1skElement.length() - 2);

            int numberRow = Integer.parseInt(rowBinaryValue, 2);
            int numberColumn = Integer.parseInt(columnBinaryValue, 2);
            int index = (numberRow * 15) + numberColumn;
            String s1skValueBinary = String.format("%4s", Integer.toBinaryString(S1_SK[s1skCounter][index])).replace(' ', '0');
            block32bites.append(s1skValueBinary);
            s1skCounter++;
        }
        String rPartFinal = changePositions(FINAL_CHANGE_POSITIONS, block32bites.toString());

        String xorRPartFinalAndL0 = xorOperation(32, rPartFinal, L0);
        return xorRPartFinalAndL0 + R0;
    }

    private static String xorOperation(int x, String rPartFinal, String L0) {
        StringBuilder xorRes = new StringBuilder();
        for (int i = 0; i < x; i++) {
            int val1 = Integer.parseInt(String.valueOf(rPartFinal.charAt(i)));
            int val2 = Integer.parseInt(String.valueOf(L0.charAt(i)));
            xorRes.append(val1 ^ val2);
        }
        return xorRes.toString();
    }

    private String shiftKey(String key, Integer round) {
        int shift = SHIFT[round];
        return key.substring(shift) + key.substring(0, shift);
    }

    private String changePositions(Integer[] position, String binaryKey) {
        StringBuilder newBinaryKey = new StringBuilder();
        Arrays.stream(position).map(idx -> binaryKey.charAt(idx - 1)).forEach(newBinaryKey::append);
        return newBinaryKey.toString();
    }

    public String convertToBinary(String text) {
        byte[] bytes = text.getBytes();
        StringBuilder binary = new StringBuilder(IntStream.range(0, bytes.length)
                .mapToObj(i -> Integer.toBinaryString(bytes[i]))
                .collect(Collectors.joining()));
        while (binary.length() < 64) {
            binary.insert(0, "0");
        }
        return binary.toString();
    }
}
