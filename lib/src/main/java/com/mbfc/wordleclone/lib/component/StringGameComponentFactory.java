package com.mbfc.wordleclone.lib.component;

import com.mbfc.wordleclone.lib.comparator.Comparator;
import com.mbfc.wordleclone.lib.comparator.StringComparator;
import com.mbfc.wordleclone.lib.game.GameFactory;
import com.mbfc.wordleclone.lib.game.SimpleGame;
import com.mbfc.wordleclone.lib.menu.WordListManager;
import com.mbfc.wordleclone.lib.parser.SimpleStringParser;
import java.util.List;

/**
 * Concrete implementation of GameComponentFactory for String type.
 */
public class StringGameComponentFactory implements GameComponentFactory<String> {

    @Override
    public WordListManager<String> createWordListManager() {
        // Use SimpleStringParser to create a WordListManager instance.
        return new WordListManager<>(new SimpleStringParser());
    }

    @Override
    public Comparator<String> createComparator() {
        return new StringComparator();
    }

    @Override
    public com.mbfc.wordleclone.lib.parser.Parser<String> createParser() {
        return new SimpleStringParser();
    }

    @Override
    public GameFactory<String, List<String>> createGameFactory() {
        return (comp, guessList, maxTries) -> new SimpleGame(comp, guessList, maxTries);
    }

    @Override
    public String getTypeName() {
        return "String";
    }
}
