package com.mbfc.wordleclone.lib.game;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.mbfc.wordleclone.lib.comparator.ComparatorResult;
import com.mbfc.wordleclone.lib.util.Pair;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/** Test suite for the {@link GameBoard} class. */
public class GameBoardTest {
  private GameBoard<String> board;

  @BeforeEach
  void setUp() {
    board = new GameBoard<>(String.class);
  }

  /** Test if new board is empty. */
  @Test
  void isEmpty_newGameBoard_true() {
    // given

    // when
    boolean isEmpty = board.isEmpty();

    // then
    assertTrue(isEmpty);
  }

  /** Test if elements are added to the board. */
  @Test
  void isEmpty_afterAdd_false() {
    // given
    board.add(new ArrayList<>(Arrays.asList(ComparatorResult.CORRECT)), "s");

    // when
    boolean isEmpty = board.isEmpty();

    // then
    assertTrue(!isEmpty);
  }

  /** Test that getting last element from empty board throws exception. */
  @Test
  void getLast_emptyGameBoard_throwsIndexOutOfBoundsException() throws IndexOutOfBoundsException {
    // given

    // when
    Exception exception =
        assertThrows(
            IndexOutOfBoundsException.class,
            () -> {
              board.getLast();
            });

    // then
  }

  /** Test that getting last element does get you most recently added element. */
  @Test
  void getLast_afterAdd_addedElement() throws IndexOutOfBoundsException {
    // given
    board.add(new ArrayList<>(Arrays.asList(ComparatorResult.CORRECT)), "s");

    // when
    var pair = board.getLast();

    // then
    assertEquals(1, pair.getLeft().size());
    assertEquals(ComparatorResult.CORRECT, pair.getLeft().get(0));
    assertEquals("s", pair.getRight());
  }

  /** Test that iterator doesn't have next on empty board. */
  @Test
  void iterator_emptyGameBoard_noNext() {
    // given

    // when
    Iterator<Pair<List<ComparatorResult>, String>> it = board.iterator();

    // then
    assertTrue(!it.hasNext());
  }

  /** Test that iterator has next element after add. */
  @Test
  void iterator_gameBoardWithElements_hasNext() {
    // given
    board.add(new ArrayList<>(Arrays.asList(ComparatorResult.CORRECT)), "s");
    // when
    Iterator<Pair<List<ComparatorResult>, String>> it = board.iterator();

    // then
    assertTrue(it.hasNext());
    assertEquals("s", it.next().getRight());
    assertTrue(!it.hasNext());
  }

  /** Test that iterator throws exception if next is called and there are no elements left. */
  @Test
  void iterator_emptyNext_throwNoSuchElementException() throws NoSuchElementException {
    // given

    // when
    Iterator<Pair<List<ComparatorResult>, String>> it = board.iterator();

    // then
    Exception exception = assertThrows(NoSuchElementException.class, it::next);
  }
}
