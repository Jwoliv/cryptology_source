package org.example.ciphers.linear;

public class LinearCipher {
    private static final String ALPHABET = "АБВГҐДЕЄЖЗИІЇЙКЛМНОПРСТУФХЦЧШЩЬЮЯабвгґдеєжзиіїйклмнопрстуфхцчшщьюяQWERT";

    public String proceedCipher(String input, int key) {
        StringBuilder result = new StringBuilder();
        for (char ch : input.toCharArray()) {
            if (Character.isLetter(ch)) {
                int index = ALPHABET.indexOf(ch);
                int newIndex = (index + key) % ALPHABET.length();
                if (newIndex < 0) {
                    newIndex += ALPHABET.length();
                }
                result.append(ALPHABET.charAt(newIndex));
            } else {
                result.append(ch);
            }
        }
        return result.toString();
    }

}
