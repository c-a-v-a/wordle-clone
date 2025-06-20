package com.mbfc.wordleclone.lib.game;

import java.util.Arrays;
import java.util.Optional;

/**
 * An enumeration representing the available game modes.
 *
 * <p>This enum defines several constants used to differentiate between game modes. Each mode is
 * associated with a unique option (a String) used for mode selection. The defined modes are:
 *
 * <ul>
 *   <li>{@code SIMPLE} - the standard mode.
 *   <li>{@code ENDLESS} - endless mode without strict round boundaries.
 *   <li>{@code ZEN_CLASSIC} - a Zen variant of the classic mode.
 *   <li>{@code RANDOM} - mode with randomly generated targets.
 *   <li>{@code ENDLESS_RANDOM} - endless mode with random targets.
 *   <li>{@code ZEN_RANDOM} - a Zen variant with random targets.
 * </ul>
 *
 * This enum provides the method {@link #getOption()} to obtain the associated selection option and
 * the static method {@link #fromOption(String)} to look up a game mode based on a given option.
 *
 * @see java.util.Optional
 */
public enum GameMode {
  SIMPLE("1"),
  ENDLESS("2"),
  ZEN_CLASSIC("3"),
  RANDOM("4"),
  ENDLESS_RANDOM("5"),
  ZEN_RANDOM("6");

  private final String option;

  /**
   * Constructs a game mode with the specified selection option.
   *
   * @param option the selection option associated with this game mode
   */
  GameMode(String option) {
    this.option = option;
  }

  /**
   * Returns the option associated with this game mode.
   *
   * @return the option as a {@code String}
   */
  public String getOption() {
    return option;
  }

  /**
   * Searches for a game mode corresponding to the specified option.
   *
   * <p>This method iterates over all defined game modes and returns an {@link Optional} that
   * contains the matching {@code GameMode} if any mode's option is equal to the provided parameter.
   * If no matching game mode is found, {@link Optional#empty()} is returned.
   *
   * @param option the selection option to search for
   * @return an {@link Optional} containing the corresponding {@code GameMode} if found, or {@link
   *     Optional#empty()} otherwise
   */
  public static Optional<GameMode> fromOption(String option) {
    return Arrays.stream(values()).filter(mode -> mode.getOption().equals(option)).findFirst();
  }
}
