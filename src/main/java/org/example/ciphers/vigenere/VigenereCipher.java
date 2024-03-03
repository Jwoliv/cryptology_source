package org.example.ciphers.vigenere;

import java.util.*;
import java.util.stream.Collectors;

public class VigenereCipher {
    private static final String ALPHABET = "_abcdefghijklmnopqrstuvwxyz";

    public String encrypt(String plainText, String key) {
        plainText = plainText.toLowerCase();
        var keySymbols = key.chars().mapToObj(x -> (char) x).toList();
        var plainTextSymbols = plainText.chars().mapToObj(x -> (char) x).toList();

        List<Integer> keyIndexes = getKeyIndexes(key);

        var plainTextIndexes = new ArrayList<Integer>();
        for (char symbol: plainTextSymbols) {
            plainTextIndexes.add(ALPHABET.indexOf(symbol));
        }


        List<Integer> fullSizeKeyIndexes = getFullLengthKeyIndexes(plainTextSymbols.size(), keyIndexes, plainTextIndexes);

        List<Integer> resultAdditionIndexes = new ArrayList<>();
        if (plainTextIndexes.size() == fullSizeKeyIndexes.size()) {
            for (int i = 0; i < ((List<Integer>) plainTextIndexes).size(); i++) {
                resultAdditionIndexes.add((((List<Integer>) plainTextIndexes).get(i) + fullSizeKeyIndexes.get(i)) % ALPHABET.length());
            }
        }

        StringBuilder encryptedText = new StringBuilder();
        resultAdditionIndexes.forEach(index -> encryptedText.append(ALPHABET.charAt(index)));

        return encryptedText.toString();
    }

    public String decrypt(String encryptText, String key) {
        var encryptedTextSymbols = encryptText.chars().mapToObj(x -> (char) x).toList();

        var encryptedTextIndexes = new ArrayList<Integer>();
        for (char symbol: encryptedTextSymbols) {
            encryptedTextIndexes.add(ALPHABET.indexOf(symbol));
        }

        List<Integer> keyIndexes = getKeyIndexes(key);

        List<Integer> fullSizeKeyIndexes = getFullLengthKeyIndexes(encryptedTextIndexes.size(), keyIndexes, encryptedTextIndexes);

        List<Integer> resultAdditionIndexes = new ArrayList<>();
        if (encryptedTextIndexes.size() == fullSizeKeyIndexes.size()) {
            for (int i = 0; i < encryptedTextIndexes.size(); i++) {
                resultAdditionIndexes.add((( encryptedTextIndexes).get(i) - fullSizeKeyIndexes.get(i) + ALPHABET.length()) % ALPHABET.length());
            }
        }

        StringBuilder encryptedText = addSymbolsToResponseByFinalIndexes(resultAdditionIndexes);

        return encryptedText.toString();
    }

    private StringBuilder addSymbolsToResponseByFinalIndexes(List<Integer> resultAdditionIndexes) {
        StringBuilder encryptedText = new StringBuilder();
        for (int index: resultAdditionIndexes) {
            encryptedText.append(ALPHABET.charAt(index));
        }
        return encryptedText;
    }

    private List<Integer> getKeyIndexes(String key) {
        var keySymbols = key.chars().mapToObj(x -> (char) x).toList();
        return keySymbols.stream()
                .map(ALPHABET::indexOf)
                .collect(Collectors.toList());
    }


    private List<Integer> getFullLengthKeyIndexes(int plainTextSymbols, List<Integer> keyIndexes, ArrayList<Integer> plainTextIndexes) {
        List<Integer> fullSizeKeyIndexes = new ArrayList<>();
        while (fullSizeKeyIndexes.size() < plainTextSymbols) {
            fullSizeKeyIndexes.addAll(keyIndexes);
        }
        fullSizeKeyIndexes = fullSizeKeyIndexes.subList(0, plainTextIndexes.size());
        return fullSizeKeyIndexes;
    }
}
