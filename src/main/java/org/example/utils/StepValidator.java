package org.example.utils;

public class StepValidator {
    public static Integer validateStep(Integer step, String alphabet) {
        return step % alphabet.length();
    }
}
