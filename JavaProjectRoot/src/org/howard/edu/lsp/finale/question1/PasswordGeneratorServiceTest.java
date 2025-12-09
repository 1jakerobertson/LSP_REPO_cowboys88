// J Unit Tests
package org.howard.edu.lsp.finale.question1;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PasswordGeneratorServiceTest {

    private PasswordGeneratorService service;

    @BeforeEach
    public void setup() {
        service = PasswordGeneratorService.getInstance();
    }

    @Test
    public void checkInstanceNotNull() {
        assertNotNull(service, "getInstance() should never return null");
    }

    @Test
    public void checkSingleInstanceBehavior() {
        PasswordGeneratorService second = PasswordGeneratorService.getInstance();
        // Both references must point to the exact same object in memory
        assertSame(service, second,
                "PasswordGeneratorService must be a true singleton");
    }

    @Test
    public void generateWithoutSettingAlgorithmThrowsException() {
        PasswordGeneratorService s = PasswordGeneratorService.getInstance();

        assertThrows(IllegalStateException.class,
                () -> s.generatePassword(8),
                "Calling generatePassword before selecting an algorithm " +
                "must throw IllegalStateException");
    }

    @Test
    public void basicAlgorithmGeneratesCorrectLengthAndDigitsOnly() {
        service.setAlgorithm("basic");
        String p = service.generatePassword(10);

        assertEquals(10, p.length(), "Basic algorithm must honor requested length");

        for (char c : p.toCharArray()) {
            assertTrue(Character.isDigit(c),
                    "Basic algorithm must generate digits only, but found: " + c);
        }
    }

    @Test
    public void enhancedAlgorithmGeneratesCorrectCharactersAndLength() {
        service.setAlgorithm("enhanced");
        String p = service.generatePassword(12);

        assertEquals(12, p.length(),
                "Enhanced algorithm must honor requested length");

        for (char c : p.toCharArray()) {
            boolean isDigit = (c >= '0' && c <= '9');
            boolean isUpper = (c >= 'A' && c <= 'Z');
            boolean isLower = (c >= 'a' && c <= 'z');

            assertTrue(isDigit || isUpper || isLower,
                    "Enhanced algorithm must generate only A–Z, a–z, or 0–9, but found: " + c);
        }
    }

    @Test
    public void lettersAlgorithmGeneratesLettersOnly() {
        service.setAlgorithm("letters");
        String p = service.generatePassword(8);

        assertEquals(8, p.length(),
                "Letters algorithm must honor requested length");

        for (char c : p.toCharArray()) {
            assertTrue(Character.isLetter(c),
                    "Letters algorithm must generate letters only, but found: " + c);
        }
    }

    @Test
    public void switchingAlgorithmsChangesBehavior() {
        service.setAlgorithm("basic");
        String p1 = service.generatePassword(10);

        service.setAlgorithm("letters");
        String p2 = service.generatePassword(10);

        service.setAlgorithm("enhanced");
        String p3 = service.generatePassword(10);

        // basic -> digits only
        for (char c : p1.toCharArray()) {
            assertTrue(Character.isDigit(c),
                    "Basic algorithm must generate digits only");
        }

        // letters -> letters only
        for (char c : p2.toCharArray()) {
            assertTrue(Character.isLetter(c),
                    "Letters algorithm must generate letters only");
        }

        // enhanced -> must be in [A–Z, a–z, 0–9]
        for (char c : p3.toCharArray()) {
            boolean isDigit = (c >= '0' && c <= '9');
            boolean isUpper = (c >= 'A' && c <= 'Z');
            boolean isLower = (c >= 'a' && c <= 'z');

            assertTrue(isDigit || isUpper || isLower,
                    "Enhanced algorithm must generate only A–Z, a–z, or 0–9");
        }
    }
}
