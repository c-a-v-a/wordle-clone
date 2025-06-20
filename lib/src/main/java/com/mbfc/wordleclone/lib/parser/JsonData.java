package com.mbfc.wordleclone.lib.parser;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.util.TreeMap;

/**
 * Represents the structure of JSON data used by the parser.
 *
 * <p>For structure info see {@link JsonParser}.
 */
public class JsonData {

  @JsonProperty("key")
  private String key;

  @JsonProperty("fields")
  private TreeMap<String, String> fields;

  @JsonProperty("data")
  private List<TreeMap<String, String>> data;

  public String getKey() {
    return key;
  }

  public TreeMap<String, String> getFields() {
    return fields;
  }

  public List<TreeMap<String, String>> getData() {
    return data;
  }
}
