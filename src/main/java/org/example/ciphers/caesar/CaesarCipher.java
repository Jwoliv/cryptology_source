package org.example.ciphers.caesar;

import static org.example.utils.StepValidator.validateStep;

public class CaesarCipher {
    private static final String ALPHABET = "АБВГҐДЕЄЖЗИІЇЙКЛМНОПРСТУФХЦЧШЩЬЮЯабвгґдеєжзиіїйклмнопрстуфхцчшщьюя ";

    public String proceedCipher(String plainMsg, Integer step, Boolean isDecrypt) {
        step = validateStep(step, ALPHABET);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < plainMsg.length(); i++) {
            char currentChar = plainMsg.charAt(i);
            sb.append(generateChar(step, isDecrypt, ALPHABET.indexOf(currentChar), currentChar));
        }
        return sb.toString();
    }

    private Character generateChar(Integer step, Boolean isDecrypt, Integer indexOfSymbol, Character currentChar) {
        return indexOfSymbol != -1
                ? ALPHABET.charAt(generateNewIndex(step, isDecrypt, indexOfSymbol))
                : currentChar;
    }

    private Integer generateNewIndex(Integer step, Boolean isDecrypt, Integer indexOfSymbol) {
        return isDecrypt
                ? validateStep(indexOfSymbol + step, ALPHABET)
                : validateStep(indexOfSymbol - step + ALPHABET.length(), ALPHABET);
    }
}
