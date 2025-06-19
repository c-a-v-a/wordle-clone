package com.mbfc.wordleclone.lib.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/** HighScoreManager handles loading and saving the high score to a file. */
public class HighScoreManager {
  private final Path filePath;
  private int highScore;

  /**
   * [Constructor Heading] Initializes a new HighScoreManager and loads the stored high score.
   *
   * <p>given a filename, when the manager is constructed, then highScore is set to 0 and
   * loadHighScore() is called.
   *
   * @param filename the name of the file storing the high score.
   */
  public HighScoreManager(String filename) {
    filePath = Paths.get(filename);
    highScore = 0;
    loadHighScore();
  }

  /**
   * [loadHighScore Heading] Loads the high score from the file if it exists.
   *
   * <p>given a valid file path, when loadHighScore() is invoked, then the high score is parsed from
   * the file, or set to 0 on error.
   */
  private void loadHighScore() {
    if (Files.exists(filePath)) {
      try {
        String content = new String(Files.readAllBytes(filePath));
        highScore = Integer.parseInt(content.trim());
      } catch (Exception e) {
        System.out.println("Failed to load high score. Defaulting to 0.");
        highScore = 0;
      }
    }
  }

  /**
   * [getHighScore Heading] Returns the current high score.
   *
   * <p>given the current state, when getHighScore() is called, then it returns the highScore value.
   *
   * @return the current high score.
   */
  public int getHighScore() {
    return highScore;
  }

  /**
   * [updateHighScore Heading] Updates and saves the high score if the current score is higher.
   *
   * <p>given a current score, when updateHighScore(currentScore) is invoked, then if currentScore
   * exceeds highScore, highScore is updated and saved.
   *
   * @param currentScore the new score to compare with the stored high score.
   */
  public void updateHighScore(int currentScore) {
    if (currentScore > highScore) {
      highScore = currentScore;
      saveHighScore();
    }
  }

  /**
   * [saveHighScore Heading] Saves the current high score to the file.
   *
   * <p>given an updated high score, when saveHighScore() is called, then the high score is written
   * to the file.
   */
  private void saveHighScore() {
    try {
      Files.write(
          filePath,
          String.valueOf(highScore).getBytes(),
          StandardOpenOption.CREATE,
          StandardOpenOption.TRUNCATE_EXISTING);
    } catch (IOException e) {
      System.out.println("Error saving high score: " + e.getMessage());
    }
  }
}
