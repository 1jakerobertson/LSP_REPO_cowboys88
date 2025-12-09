package org.howard.edu.lsp.finale.question1;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Singleton service that generates passwords using pluggable algorithms.
 */
public class PasswordGeneratorService {

    private static final PasswordGeneratorService INSTANCE =
            new PasswordGeneratorService();

    private final Map<String, PasswordAlgorithm> algorithms = new HashMap<>();
    private PasswordAlgorithm currentAlgorithm;

    /**
     * Strategy interface for password-generation algorithms.
     */
    private interface PasswordAlgorithm {
        String generate(int length);
    }

    /**
     * Digits-only algorithm using java.util.Random.
     */
    private static class BasicAlgorithm implements PasswordAlgorithm {
        private static final String DIGITS = "0123456789";
        private final Random random = new Random();

        @Override
        public String generate(int length) {
            if (length < 0) {
                throw new IllegalArgumentException("Length must be non-negative.");
            }
            StringBuilder sb = new StringBuilder(length);
            for (int i = 0; i < length; i++) {
                int index = random.nextInt(DIGITS.length());
                sb.append(DIGITS.charAt(index));
            }
            return sb.toString();
        }
    }

    /**
     * Letters-only algorithm using SecureRandom.
     */
    private static class LettersAlgorithm implements PasswordAlgorithm {
        private static final String LETTERS =
                "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        private final SecureRandom random = new SecureRandom();

        @Override
        public String generate(int length) {
            if (length < 0) {
                throw new IllegalArgumentException("Length must be non-negative.");
            }
            StringBuilder sb = new StringBuilder(length);
            for (int i = 0; i < length; i++) {
                int index = random.nextInt(LETTERS.length());
                sb.append(LETTERS.charAt(index));
            }
            return sb.toString();
        }
    }

    /**
     * Letters + digits algorithm using SecureRandom.
     */
    private static class EnhancedAlgorithm implements PasswordAlgorithm {
        private static final String ALLOWED =
                "ABCDEFGHIJKLMNOPQRSTUVWXYZ" +
                "abcdefghijklmnopqrstuvwxyz" +
                "0123456789";
        private final SecureRandom random = new SecureRandom();

        @Override
        public String generate(int length) {
            if (length < 0) {
                throw new IllegalArgumentException("Length must be non-negative.");
            }
            StringBuilder sb = new StringBuilder(length);
            for (int i = 0; i < length; i++) {
                int index = random.nextInt(ALLOWED.length());
                sb.append(ALLOWED.charAt(index));
            }
            return sb.toString();
        }
    }

    /**
     * Private constructor: registers built-in algorithms.
     */
    private PasswordGeneratorService() {
        algorithms.put("basic", new BasicAlgorithm());
        algorithms.put("letters", new LettersAlgorithm());
        algorithms.put("enhanced", new EnhancedAlgorithm());
    }

    /**
     * Returns the single shared instance of this service.
     *
     * @return the singleton PasswordGeneratorService
     */
    public static PasswordGeneratorService getInstance() {
        return INSTANCE;
    }

    /**
     * Selects the password-generation algorithm by name.
     * Supported values: "basic", "enhanced", "letters".
     *
     * @param name algorithm name; must not be null
     * @throws IllegalArgumentException if name is null or unsupported
     */
    public void setAlgorithm(String name) {
        if (name == null) {
            throw new IllegalArgumentException("Algorithm name cannot be null.");
        }
        PasswordAlgorithm algo = algorithms.get(name.toLowerCase());
        if (algo == null) {
            throw new IllegalArgumentException("Unsupported algorithm: " + name);
        }
        currentAlgorithm = algo;
    }

    /**
     * Generates a password of the given length using the selected algorithm.
     *
     * @param length desired password length (non-negative)
     * @return generated password
     * @throws IllegalStateException    if no algorithm has been selected
     * @throws IllegalArgumentException if length is negative
     */
    public String generatePassword(int length) {
        if (currentAlgorithm == null) {
            throw new IllegalStateException(
                    "No algorithm selected. Call setAlgorithm(String) first.");
        }
        return currentAlgorithm.generate(length);
    }