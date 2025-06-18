package com.mbfc.wordleclone.lib.util;

/**
 * A generic class representing a pair of objects.
 *
 * <p>The types of stored objects may be different.
 *
 * @param <T> the type of the left object
 * @param <U> the type of the right object
 * @param left Left object of the pair.
 * @param right Right object of the pair.
 */
public record Pair<T, U>(T left, U right) {}
