package org.example.ciphers.vigenere;

import java.util.*;
import java.util.stream.Collectors;

public class VigenereCipher {
    private static final String ALPHABET = "_abcdefghijklmnopqrstuvwxyz";

    public String encrypt(String plainText, String key) {
        var plainTextIndexes = getTextIndexes(plainText);
        var fullSizeKeyIndexes = getFullIndexes(plainText, key, plainTextIndexes);
        List<Integer> resultAdditionIndexes = new ArrayList<>();
        if (plainTextIndexes.size() == fullSizeKeyIndexes.size()) {
            for (int i = 0; i < plainTextIndexes.size(); i++) {
                resultAdditionIndexes.add((plainTextIndexes.get(i) + fullSizeKeyIndexes.get(i)) % ALPHABET.length());
            }
        }

        StringBuilder encryptedText = new StringBuilder();
        resultAdditionIndexes.forEach(index -> encryptedText.append(ALPHABET.charAt(index)));

        return encryptedText.toString();
    }

    public String decrypt(String encryptText, String key) {
        var encryptedTextIndexes = getTextIndexes(encryptText);
        var fullSizeKeyIndexes = getFullIndexes(encryptText, key, encryptedTextIndexes);

        List<Integer> resultAdditionIndexes = new ArrayList<>();
        if (encryptedTextIndexes.size() == fullSizeKeyIndexes.size()) {
            for (int i = 0; i < encryptedTextIndexes.size(); i++) {
                resultAdditionIndexes.add((encryptedTextIndexes.get(i) - fullSizeKeyIndexes.get(i) + ALPHABET.length()) % ALPHABET.length());
            }
        }

        StringBuilder encryptedText = addSymbolsToResponseByFinalIndexes(resultAdditionIndexes);
        return encryptedText.toString();
    }


    private List<Integer> getFullIndexes(String text, String key, List<Integer> textIndexes) {
        var plainTextSymbols = turnStringIntoCharactersList(text.toLowerCase());
        return getFullLengthKeyIndexes(plainTextSymbols.size(), getKeyIndexes(key), textIndexes);
    }

    private List<Integer> getTextIndexes(String text) {
        var plainTextSymbols = turnStringIntoCharactersList(text.toLowerCase());
        return generateIndexesByList(plainTextSymbols);
    }

    private List<Character> turnStringIntoCharactersList(String encryptText) {
        return encryptText.chars().mapToObj(x -> (char) x).toList();
    }

    private ArrayList<Integer> generateIndexesByList(List<Character> encryptedTextSymbols) {
        var encryptedTextIndexes = new ArrayList<Integer>();
        for (char symbol: encryptedTextSymbols) {
            encryptedTextIndexes.add(ALPHABET.indexOf(symbol));
        }
        return encryptedTextIndexes;
    }

    private StringBuilder addSymbolsToResponseByFinalIndexes(List<Integer> resultAdditionIndexes) {
        StringBuilder encryptedText = new StringBuilder();
        for (int index: resultAdditionIndexes) {
            encryptedText.append(ALPHABET.charAt(index));
        }
        return encryptedText;
    }

    private List<Integer> getKeyIndexes(String key) {
        var keySymbols = turnStringIntoCharactersList(key);
        return keySymbols.stream()
                .map(ALPHABET::indexOf)
                .collect(Collectors.toList());
    }


    private List<Integer> getFullLengthKeyIndexes(int plainTextSymbols, List<Integer> keyIndexes, List<Integer> plainTextIndexes) {
        List<Integer> fullSizeKeyIndexes = new ArrayList<>();
        while (fullSizeKeyIndexes.size() < plainTextSymbols) {
            fullSizeKeyIndexes.addAll(keyIndexes);
        }
        fullSizeKeyIndexes = fullSizeKeyIndexes.subList(0, plainTextIndexes.size());
        return fullSizeKeyIndexes;
    }
}
