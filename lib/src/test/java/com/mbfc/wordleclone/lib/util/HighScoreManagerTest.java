package com.mbfc.wordleclone.lib.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

/**
 * Unit tests for HighScoreManager.
 *
 * <p>These tests verify that the high score is loaded from file, that updateHighScore only updates
 * the stored score when a higher score is provided, and that a non-existing file causes the score
 * to initialize to zero.
 */
public class HighScoreManagerTest {
  @TempDir Path tempDir;

  @Test
  void updateHighScore_updatesOnlyHigherScore() throws IOException {
    // Given: a high score file with an initial score of 5.
    Path highScoreFile = tempDir.resolve("highscore.txt");
    Files.write(highScoreFile, "5".getBytes());
    HighScoreManager manager = new HighScoreManager(highScoreFile.toString());
    assertEquals(5, manager.getHighScore());

    // When: updating with a lower score.
    manager.updateHighScore(3);
    // Then: high score remains unchanged.
    assertEquals(5, manager.getHighScore());

    // When: updating with a higher score.
    manager.updateHighScore(10);
    // Then: high score is updated to 10.
    assertEquals(10, manager.getHighScore());

    // When: creating a new instance reading from the same file.
    HighScoreManager manager2 = new HighScoreManager(highScoreFile.toString());
    // Then: the loaded high score is 10.
    assertEquals(10, manager2.getHighScore());
  }

  @Test
  void updateHighScore_whenFileDoesNotExist_initializesToZero() {
    // Given: a file path that does not exist.
    Path filePath = tempDir.resolve("nonexistent.txt");
    HighScoreManager manager = new HighScoreManager(filePath.toString());
    // Then: the high score initializes to 0.
    assertEquals(0, manager.getHighScore());

    // When: a new score is provided.
    manager.updateHighScore(7);
    // Then: the high score is updated to 7.
    assertEquals(7, manager.getHighScore());
  }
}
