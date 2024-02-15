package org.example.ciphers.linear;

import static org.example.utils.StepValidator.validateStep;

public class LinearCipher {
    private static final String ALPHABET = "АБВГҐДЕЄЖЗИІЇЙКЛМНОПРСТУФХЦЧШЩЬЮЯабвгґдеєжзиіїйклмнопрстуфхцчшщьюяQWERT";

    public String proceedCipher(String input, Integer key) {
        StringBuilder result = new StringBuilder();
        for (char ch : input.toCharArray()) {
            if (Character.isLetter(ch)) {
                int newIndex = validateNewIndex(key, ALPHABET.indexOf(ch));
                result.append(ALPHABET.charAt(newIndex));
            } else {
                result.append(ch);
            }
        }
        return result.toString();
    }

    private Integer validateNewIndex(Integer key, Integer index) {
        int newIndex = validateStep(index + key, ALPHABET);
        if (newIndex < 0) {
            newIndex += ALPHABET.length();
        }
        return newIndex;
    }

}
