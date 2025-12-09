// J Unit Tests
package org.howard.edu.lsp.finale.question1;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class PasswordGeneratorServiceTest {

    private PasswordGeneratorService service;

    @Before
    public void setup() {
        service = PasswordGeneratorService.getInstance();
    }

    @Test
    public void checkInstanceNotNull() {
        assertNotNull("getInstance() should never return null", service);
    }

    @Test
    public void checkSingleInstanceBehavior() {
        PasswordGeneratorService second = PasswordGeneratorService.getInstance();
        // Both references must point to the exact same object in memory
        assertSame("PasswordGeneratorService must be a true singleton",
                   service, second);
    }

    @Test(expected = IllegalStateException.class)
    public void generateWithoutSettingAlgorithmThrowsException() {
        PasswordGeneratorService s = PasswordGeneratorService.getInstance();
        // No algorithm selected yet -> should throw IllegalStateException
        s.generatePassword(8);
    }

    @Test
    public void basicAlgorithmGeneratesCorrectLengthAndDigitsOnly() {
        service.setAlgorithm("basic");
        String p = service.generatePassword(10);

        assertEquals("Basic algorithm must honor requested length",
                     10, p.length());

        for (char c : p.toCharArray()) {
            assertTrue("Basic algorithm must generate digits only, but found: " + c,
                       Character.isDigit(c));
        }
    }

    @Test
    public void enhancedAlgorithmGeneratesCorrectCharactersAndLength() {
        service.setAlgorithm("enhanced");
        String p = service.generatePassword(12);

        assertEquals("Enhanced algorithm must honor requested length",
                     12, p.length());

        for (char c : p.toCharArray()) {
            boolean isDigit = (c >= '0' && c <= '9');
            boolean isUpper = (c >= 'A' && c <= 'Z');
            boolean isLower = (c >= 'a' && c <= 'z');

            assertTrue("Enhanced algorithm must generate only A–Z, a–z, or 0–9, but found: " + c,
                       isDigit || isUpper || isLower);
        }
    }

    @Test
    public void lettersAlgorithmGeneratesLettersOnly() {
        service.setAlgorithm("letters");
        String p = service.generatePassword(8);

        assertEquals("Letters algorithm must honor requested length",
                     8, p.length());

        for (char c : p.toCharArray()) {
            assertTrue("Letters algorithm must generate letters only, but found: " + c,
                       Character.isLetter(c));
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
            assertTrue("Basic algorithm must generate digits only",
                       Character.isDigit(c));
        }

        // letters -> letters only
        for (char c : p2.toCharArray()) {
            assertTrue("Letters algorithm must generate letters only",
                       Character.isLetter(c));
        }

        // enhanced -> must be in [A–Z, a–z, 0–9]
        for (char c : p3.toCharArray()) {
            boolean isDigit = (c >= '0' && c <= '9');
            boolean isUpper = (c >= 'A' && c <= 'Z');
            boolean isLower = (c >= 'a' && c <= 'z');
            assertTrue("Enhanced algorithm must generate only A–Z, a–z, or 0–9",
                       isDigit || isUpper || isLower);
        }
    }
}
