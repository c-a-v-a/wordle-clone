package com.mbfc.wordleclone.cli;

import com.mbfc.wordleclone.lib.comparator.ComparatorResult;
import com.mbfc.wordleclone.lib.game.GameBoard;
import com.mbfc.wordleclone.lib.json.Field;
import com.mbfc.wordleclone.lib.util.Pair;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import org.fusesource.jansi.Ansi;
import org.fusesource.jansi.AnsiConsole;

/** Utility class for rendering and printing game output with color-coded feedback. */
public class Printer {
  private static String resultToColor(ComparatorResult result) {
    return switch (result) {
      case CORRECT -> "green";
      case PARTIAL -> "yellow";
      case INCORRECT -> "red";
      case TOO_HIGH -> "cyan";
      case TOO_LOW -> "magenta";
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

  private static String renderMap(
      List<ComparatorResult> results, TreeMap<String, Field> map, String key) {
    List<ComparatorResult> resultsCopy = new ArrayList<>(results);
    TreeMap<String, Field> mapCopy = new TreeMap<>(map);

    StringBuilder render = new StringBuilder();
    int keyIndex = 0;

    if (key != null) {
      int i = 0;
      for (String mapKey : mapCopy.keySet()) {
        if (mapKey.equals(key)) {
          keyIndex = i;
          break;
        }

        i++;
      }
    }

    render.append(
        renderSingle(
            resultsCopy.get(keyIndex), key + ": " + mapCopy.get(key).getValue().toString()));

    resultsCopy.remove(keyIndex);
    mapCopy.remove(key);

    int i = 0;
    for (Map.Entry<String, Field> entry : mapCopy.entrySet()) {
      render.append(
          renderSingle(
              resultsCopy.get(i), entry.getKey() + ": " + entry.getValue().getValue().toString()));

      i++;
    }

    return render.toString();
  }

  private static void println(Pair<List<ComparatorResult>, String> pair) {
    AnsiConsole.out().println(Ansi.ansi().render(renderString(pair.left(), pair.right())));
  }

  private static void printlnMap(
      Pair<List<ComparatorResult>, TreeMap<String, Field>> pair, String key) {
    AnsiConsole.out().println(Ansi.ansi().render(renderMap(pair.left(), pair.right(), key)));
  }

  /**
   * Prints the entire {@link GameBoard} with each guess color-coded.
   *
   * @param board the game board for current game
   */
  public static <T> void printBoard(GameBoard<T> board, String key) {
    if (board.getType() == String.class) {
      for (Pair<List<ComparatorResult>, String> pair : (GameBoard<String>) board) {
        println(pair);
      }
    } else if (board.getType() == TreeMap.class) {
      for (Pair<List<ComparatorResult>, TreeMap<String, Field>> pair :
          (GameBoard<TreeMap<String, Field>>) board) {
        printlnMap(pair, key);
      }
    }
  }
}
