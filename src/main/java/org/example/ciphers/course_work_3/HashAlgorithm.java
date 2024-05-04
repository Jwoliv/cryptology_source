package org.example.ciphers.course_work_3;

import java.util.Arrays;
import java.util.stream.Collectors;

public class HashAlgorithm {
    public static void main(String[] args) {
        String text = "Lorem Ipsum is simply dummy text of the printing and typesetting industry.";
        String binaryText = turnPlainStringIntoBinarySequence(text);
        System.out.println(binaryText);
    }

    public static String turnPlainStringIntoBinarySequence(String text) {
        byte[] bytes = text.getBytes();
        int[] h = new int[8];

        for (byte b : bytes) {
            int[] a = getBinaryArray(b);
            calculateHValues(a, h);
        }

        int[] h1 = new int[8];
        for (int i = 0; i < 5; i++) {
            calculateHValues(h, h1);
        }

        return Arrays.stream(h)
                .mapToObj(String::valueOf)
                .collect(Collectors.joining());
    }

    private static int[] getBinaryArray(byte b) {
        String binaryStr = String.format("%8s", Integer.toBinaryString(b & 0xFF)).replace(' ', '0');
        return Arrays.stream(binaryStr.split(""))
                .mapToInt(Integer::parseInt)
                .toArray();
    }

    private static void calculateHValues(int[] a, int[] h) {
        h[0] = a[0] ^ a[4] ^ a[5];
        h[1] = a[1] ^ a[2] ^ a[5];
        h[2] = a[2] ^ a[6] ^ a[7];
        h[3] = a[0] ^ a[4] ^ a[5];
        h[4] = a[4] ^ a[5] ^ a[6];
        h[5] = a[1] ^ a[3] ^ a[7];
        h[6] = a[2] ^ a[5] ^ a[6];
        h[7] = a[0] ^ a[3] ^ a[7];
    }
}
