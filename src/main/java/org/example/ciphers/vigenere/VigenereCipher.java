package org.example.ciphers.vigenere;

import java.util.*;
import java.util.stream.Collectors;

public class VigenereCipher {
    private static final String ALPHABET = "_abcdefghijklmnopqrstuvwxyz";

    public String encrypt(String plainText, String key) {
        List<Integer> finallyIndexes = getFinallyIndexes(plainText, key, true);
        StringBuilder encryptedText = addSymbolsToResponseByFinalIndexes(finallyIndexes);
        return encryptedText.toString();
    }

    public String decrypt(String encryptText, String key) {
        List<Integer> finallyIndexes = getFinallyIndexes(encryptText, key, false);
        StringBuilder encryptedText = addSymbolsToResponseByFinalIndexes(finallyIndexes);
        return encryptedText.toString();
    }

    private List<Integer> getFinallyIndexes(String text, String key, Boolean isEncrypt) {
        var encryptedTextIndexes = getTextIndexes(text);
        var fullSizeKeyIndexes = getFullIndexes(text, key, encryptedTextIndexes);
        return proceedIndexes(encryptedTextIndexes, fullSizeKeyIndexes, isEncrypt);
    }

    private List<Integer> proceedIndexes(List<Integer> textIndexes, List<Integer> fullSizeIndexes, Boolean isEncrypt) {
        List<Integer> resultAdditionIndexes = new ArrayList<>();
        for (int i = 0; i < textIndexes.size(); i++) {
            int newIndex = generateNewIndex(textIndexes, fullSizeIndexes, isEncrypt, i);
            resultAdditionIndexes.add(newIndex);
        }
        return resultAdditionIndexes;
    }

    private int generateNewIndex(List<Integer> textIndexes, List<Integer> fullSizeIndexes, Boolean isEncrypt, int i) {
        return (calculateTwoIndexes(textIndexes, fullSizeIndexes, isEncrypt, i) + ALPHABET.length()) % ALPHABET.length();
    }

    private int calculateTwoIndexes(List<Integer> textIndexes, List<Integer> fullSizeIndexes, Boolean isEncrypt, int i) {
        return isEncrypt
                ? textIndexes.get(i) + fullSizeIndexes.get(i)
                : textIndexes.get(i) - fullSizeIndexes.get(i);
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
        return encryptText.chars()
                .mapToObj(x -> (char) x)
                .toList();
    }

    private ArrayList<Integer> generateIndexesByList(List<Character> encryptedTextSymbols) {
        var encryptedTextIndexes = new ArrayList<Integer>();
        encryptedTextSymbols.forEach(symbol ->  encryptedTextIndexes.add(ALPHABET.indexOf(symbol)));
        return encryptedTextIndexes;
    }

    private StringBuilder addSymbolsToResponseByFinalIndexes(List<Integer> resultAdditionIndexes) {
        StringBuilder encryptedText = new StringBuilder();
        resultAdditionIndexes.forEach(index -> encryptedText.append(ALPHABET.charAt(index)));
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
