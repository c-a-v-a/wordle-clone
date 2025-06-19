package com.mbfc.wordleclone.lib.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

public class HighScoreManagerTest {
  @TempDir Path tempDir;

  // Tests that updateHighScore updates the record only when a new higher score is provided and
  // retains the current record otherwise.
  @Test
  void updateHighScore_updatesOnlyHigherScore() throws IOException {
    // given
    Path highScoreFile = tempDir.resolve("highscore.txt");
    Files.write(highScoreFile, "5".getBytes());
    HighScoreManager manager = new HighScoreManager(highScoreFile.toString());
    assertEquals(5, manager.getHighScore());

    // when
    manager.updateHighScore(3);
    // then
    assertEquals(5, manager.getHighScore());

    // when
    manager.updateHighScore(10);
    // then
    assertEquals(10, manager.getHighScore());

    // when: creating a new manager instance
    HighScoreManager manager2 = new HighScoreManager(highScoreFile.toString());
    // then
    assertEquals(10, manager2.getHighScore());
  }

  // Tests that when the file does not exist, the high score initializes to zero and then updates
  // correctly when a new score is provided.
  @Test
  void updateHighScore_whenFileDoesNotExist_initializesToZero() {
    // given
    Path filePath = tempDir.resolve("nonexistent.txt");
    HighScoreManager manager = new HighScoreManager(filePath.toString());
    assertEquals(0, manager.getHighScore());

    // when
    manager.updateHighScore(7);
    // then
    assertEquals(7, manager.getHighScore());
  }
}
