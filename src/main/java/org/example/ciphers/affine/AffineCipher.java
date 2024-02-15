package org.example.ciphers.affine;

import static org.example.utils.StepValidator.validateStep;

public class AffineCipher {
    private static final String ALPHABET = "АБВГҐДЕЄЖЗИІЇЙКЛМНОПРСТУФХЦЧШЩЬЮЯабвгґдеєжзиіїйклмнопрстуфхцчшщьюя ";

    public String encrypt(String text, Integer key1, Integer key2) {
        StringBuilder sb = new StringBuilder();
        for (char ch : text.toCharArray()) {
            int index = ALPHABET.indexOf(ch);
            sb.append(index < 0 ? ch : generateNewCharEncrypt(key1, key2, index));
        }
        return sb.toString();
    }

    private Character generateNewCharEncrypt(Integer key1, Integer key2, Integer index) {
        int newIndex = validateStep((index * key1 + key2), ALPHABET);
        return ALPHABET.charAt(newIndex);
    }

    public String decrypt(String text, Integer key1, Integer key2) {
        StringBuilder sb = new StringBuilder();
        int modInverse = modInverse(key1, ALPHABET.length());
        for (char ch : text.toCharArray()) {
            int index = ALPHABET.indexOf(ch);
            sb.append(index < 0 ? ch : generateNewCharDecrypt(modInverse, key2, index));
        }
        return sb.toString();
    }

    private Character generateNewCharDecrypt(Integer key1, Integer key2, Integer index) {
        int newIndex = validateStep(key1 * (index - key2), ALPHABET);
        newIndex = validateByIndexBelowZero(newIndex);
        return ALPHABET.charAt(newIndex);
    }

    private Integer validateByIndexBelowZero(Integer newIndex) {
        newIndex += newIndex < 0 ? ALPHABET.length() : 0;
        return newIndex;
    }

    private Integer modInverse(Integer a, Integer m) {
        int r = a % m;
        for (int x = 1; x < m; x++) {
            if ((r * x) % m == 1) {
                return x;
            }
        }
        return 1;
    }

}

