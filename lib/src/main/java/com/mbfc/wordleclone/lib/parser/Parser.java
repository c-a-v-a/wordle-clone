package com.mbfc.wordleclone.lib.parser;

import java.io.IOException;
import java.util.List;

/**
 * Provides generic operations for parsing a file into a list of objects.
 *
 * <p>Implementation of this interface is responsible for parsing both resource files, as well as
 * files on the system.
 *
 * @param <T> the type of object to parse each entry into
 */
public interface Parser<T> {

  /**
   * Parses the given resource file and returns a list of objects of type {@code T}.
   *
   * @param resourcePath the path to the resource file to parse
   * @return a list of parsed objects
   * @throws IOException if an I/O error occurs
   */
  public abstract List<T> parseResource(String resourcePath) throws IOException;

  /**
   * Parses the given file and returns a list of objects of type {@code T}.
   *
   * @param filePath the path to the file to parse
   * @return a list of parsed objects
   * @throws IOException if an I/O error occurs
   */
  public abstract List<T> parseFile(String filePath) throws IOException;
}
