package org.example.ciphers.course_3.linear;

import static org.example.utils.StepValidator.validateStep;

public class LinearCipher {
    private static final String ALPHABET = "АБВГҐДЕЄЖЗИІЇЙКЛМНОПРСТУФХЦЧШЩЬЮЯабвгґдеєжзиіїйклмнопрстуфхцчшщьюяQWERT";

    public String proceedCipher(String input, Integer key) {
        StringBuilder result = new StringBuilder();
        for (char ch : input.toCharArray()) {
            if (Character.isLetter(ch)) {
                int newIndex = validateStep(key + ALPHABET.indexOf(ch) + ALPHABET.length(), ALPHABET);
                result.append(ALPHABET.charAt(newIndex));
            } else {
                result.append(ch);
            }
        }
        return result.toString();
    }
}
