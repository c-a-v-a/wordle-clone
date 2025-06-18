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
    StringBuilder render = new StringBuilder();

    for (ComparatorResult result : ComparatorResult.values()) {
      render.append(renderSingle(result, result.getText()));
    }

    System.out.println("COLOR CODE INFO:");
    System.out.println(Ansi.ansi().render(render.toString()));
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
    StringBuilder render = new StringBuilder();

    for (int i = 0; i < string.length(); i++) {
      render.append(renderSingle(results.get(i), string.toUpperCase().substring(i, i + 1)));
    }

    return render.toString();
  }

  private static void println(Pair<List<ComparatorResult>, String> pair) {
    AnsiConsole.out().println(Ansi.ansi().render(renderString(pair.left(), pair.right())));
  }

  /**
   * Prints the entire {@link GameBoard} with each guess color-coded.
   *
   * <p>This implementation regards only the game boards with {@code String} as guesses.
   *
   * @param board the game board for current game
   */
  public static <T> void printBoard(GameBoard<T> board) {
    if (board.getType() == String.class) {
      for (Pair<List<ComparatorResult>, String> pair : (GameBoard<String>) board) {
        println(pair);
      }
    }
  }
}
