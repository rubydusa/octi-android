package com.example.octi;

import java.security.SecureRandom;

public class RoomCodeGenerator {

    // using base58 because I'm based and redpilled
    private static final String ALPHABET = "123456789ABCDEFGHJKLMNPQRSTUVWXYZabcdefghijkmnopqrstuvwxyz";
    private static final SecureRandom RANDOM = new SecureRandom();
    private static final int LENGTH = 6;

    public static String generateCode() {
        StringBuilder sb = new StringBuilder(LENGTH);
        for (int i = 0; i < LENGTH; i++) {
            int index = RANDOM.nextInt(ALPHABET.length());
            sb.append(ALPHABET.charAt(index));
        }
        return sb.toString();
    }
}
