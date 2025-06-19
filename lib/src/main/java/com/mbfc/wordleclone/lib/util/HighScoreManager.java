package com.mbfc.wordleclone.lib.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * Utility class for managing the high score.
 *
 * <p>This class reads and writes the high score to a file. By default, if a relative file name is
 * provided, it is stored inside the project's folder at "cli/src/main/resources". However, if an
 * absolute path is provided (e.g., during tests), it will be used directly.
 */
public class HighScoreManager {
  private final Path filePath;
  private int highScore;

  /**
   * Creates a new instance of HighScoreManager and loads the high score.
   *
   * @param configName the name of the configuration file (e.g., "highscore_endless_classic.txt" or
   *     an absolute path)
   */
  public HighScoreManager(String configName) {
    Path givenPath = Paths.get(configName);
    if (givenPath.isAbsolute()) {
      this.filePath = givenPath;
    } else {
      this.filePath = Paths.get("cli", "src", "main", "resources", configName);
    }
    this.highScore = 0;

    try {
      Files.createDirectories(filePath.getParent());
    } catch (IOException ignore) {
    }

    loadHighScore();
  }

  /**
   * Elegantly loads the high score from the configuration file.
   *
   * <p>If reading or parsing fails, the high score will remain at the default value (0).
   */
  private void loadHighScore() {
    try {
      String content = new String(Files.readAllBytes(filePath));
      highScore = Integer.parseInt(content.trim());
    } catch (Exception ignore) {
      highScore = 0;
    }
  }

  /**
   * Returns the current high score.
   *
   * @return the high score
   */
  public int getHighScore() {
    return highScore;
  }

  /**
   * Updates the high score if the current score is greater than the stored high score.
   *
   * @param currentScore the current score to compare with the high score
   */
  public void updateHighScore(int currentScore) {
    if (currentScore > highScore) {
      highScore = currentScore;
      saveHighScore();
    }
  }

  /**
   * Saves the high score to the configuration file.
   *
   * <p>If an error occurs during saving, the error will be printed to the error stream, indicating
   * potential data loss.
   */
  private void saveHighScore() {
    try {
      String scoreStr = String.valueOf(highScore);
      Files.write(
          filePath,
          scoreStr.getBytes(),
          StandardOpenOption.CREATE,
          StandardOpenOption.TRUNCATE_EXISTING);
    } catch (IOException e) {
      System.err.println("Error saving high score: " + e.getMessage());
    }
  }
}
