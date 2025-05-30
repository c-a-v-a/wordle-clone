package com.mbfc.wordleclone.cli;

import com.mbfc.wordleclone.lib.comparator.ComparatorResult;
import com.mbfc.wordleclone.lib.game.GameBoard;
import java.util.ArrayList;
import org.fusesource.jansi.Ansi;
import org.fusesource.jansi.AnsiConsole;

/** Hello world. */
public class App {
  /** Program entry point. */
  public static void main(String[] args) {
    ArrayList<ComparatorResult> results = new ArrayList<>();
    results.add(ComparatorResult.CORRECT);
    results.add(ComparatorResult.PARTIAL);
    results.add(ComparatorResult.INCORRECT);
    GameBoard<String> board = new GameBoard<>();

    board.add(results, "abc");
    board.add(results, "def");
    board.add(results, "efg");

    AnsiConsole.systemInstall();

    AnsiConsole.out().println(Ansi.ansi().eraseScreen().cursor(1, 1));

    Printer.printColorCodeInfo();
    Printer.printBoard(board);

    AnsiConsole.systemUninstall();
  }
}
