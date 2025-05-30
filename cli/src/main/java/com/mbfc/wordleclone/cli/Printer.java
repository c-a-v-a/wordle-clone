package com.mbfc.wordleclone.cli;

import com.mbfc.wordleclone.lib.comparator.ComparatorResult;
import com.mbfc.wordleclone.lib.game.GameBoard;
import com.mbfc.wordleclone.lib.util.Pair;
import java.util.List;
import org.fusesource.jansi.Ansi;
import org.fusesource.jansi.AnsiConsole;

/** Utility class for rendering and printing game output with color-coded feedback. */
public class Printer {
  private static String resultToColor(ComparatorResult result) {
    return switch (result) {
      case CORRECT -> "green";
      case PARTIAL -> "yellow";
      case INCORRECT -> "red";
    };
  }

  /** Prints out the visual legend explaining what each color means in the context of the game. */
  public static void printColorCodeInfo() {
    String render = "";

    for (ComparatorResult result : ComparatorResult.values()) {
      render += renderSingle(result, result.getText());
    }

    System.out.println("COLOR CODE INFO:");
    System.out.println(Ansi.ansi().render(render));
  }

  private static String renderSingle(ComparatorResult result, String string) {
    String render = "@|";

    render += resultToColor(result);
    render += " ";
    render += string;
    render += "|@ ";

    return render;
  }

  private static String renderString(List<ComparatorResult> results, String string) {
    String render = "";

    for (int i = 0; i < string.length(); i++) {
      render += renderSingle(results.get(i), string.toUpperCase().substring(i, i + 1));
    }

    return render;
  }

  private static void println(Pair<List<ComparatorResult>, String> pair) {
    AnsiConsole.out().println(Ansi.ansi().render(renderString(pair.getLeft(), pair.getRight())));
  }

  /**
   * Prints the entire {@link GameBoard} with each guess color-coded.
   *
   * <p>This implementation regards only the game boards with {@code String} as guesses.
   *
   * @param board the game board for current game
   */
  public static void printBoard(GameBoard<String> board) {
    for (Pair<List<ComparatorResult>, String> pair : board) {
      println(pair);
    }
  }
}
