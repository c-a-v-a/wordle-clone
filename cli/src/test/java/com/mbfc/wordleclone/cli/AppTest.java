package com.mbfc.wordleclone.cli;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for the {@link App} class.
 */
public class AppTest {

    /**
     * Tests that the main method runs and terminates gracefully when provided with an exit option.
     */
    @Test
    public void testAppMainExitsGracefully() {
        // Simulate user input that immediately selects "Exit" (option 3).
        String simulatedInput = "3\n";
        InputStream originalIn = System.in;
        try {
            System.setIn(new ByteArrayInputStream(simulatedInput.getBytes(StandardCharsets.UTF_8)));
            assertDoesNotThrow(() -> App.main(new String[] {}));
        } finally {
            System.setIn(originalIn);
        }
    }
}
