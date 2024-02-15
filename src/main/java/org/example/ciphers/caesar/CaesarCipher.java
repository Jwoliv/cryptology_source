package org.example.ciphers.caesar;

import static org.example.utils.StepValidator.validateStep;

public class CaesarCipher {
    private static final String ALPHABET = "АБВГҐДЕЄЖЗИІЇЙКЛМНОПРСТУФХЦЧШЩЬЮЯабвгґдеєжзиіїйклмнопрстуфхцчшщьюя ";

    public String proceedCipher(String plainMsg, int step, Boolean isDecrypt) {
        step = validateStep(step, ALPHABET);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < plainMsg.length(); i++) {
            char currentChar = plainMsg.charAt(i);
            int indexOfSymbol = ALPHABET.indexOf(currentChar);
            char newChar = generateChar(step, isDecrypt, indexOfSymbol, currentChar);
            sb.append(newChar);
        }
        return sb.toString();
    }

    private Character generateChar(int step, Boolean isDecrypt, int indexOfSymbol, char currentChar) {
        return indexOfSymbol == -1
                ? currentChar :
                ALPHABET.charAt(generateNewIndex(step, isDecrypt, indexOfSymbol));
    }

    private Integer generateNewIndex(int step, Boolean isDecrypt, int indexOfSymbol) {
        return isDecrypt
                ? validateStep(indexOfSymbol + step, ALPHABET)
                : validateStep(indexOfSymbol - step + ALPHABET.length(), ALPHABET);
    }
}
