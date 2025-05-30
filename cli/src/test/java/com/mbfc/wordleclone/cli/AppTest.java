package com.mbfc.wordleclone.cli;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;

/** Unit tests for the {@link App} class. */
public class AppTest {

    /**
     * Tests that the main application exits gracefully when the Exit option is selected.
     */
    @Test
    void app_main_exitsGracefully() {
        // Simulate input that immediately selects "3" (Exit) at the main menu.
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
