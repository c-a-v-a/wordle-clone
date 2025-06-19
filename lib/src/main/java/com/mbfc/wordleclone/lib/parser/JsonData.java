package com.mbfc.wordleclone.lib.parser;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.HashMap;
import java.util.List;

/**
 * Represents the structure of JSON data used by the parser.
 *
 * <p>For structure info see {@link JsonParser}.
 */
public class JsonData {

  @JsonProperty("key")
  private String key;

  @JsonProperty("fields")
  private HashMap<String, String> fields;

  @JsonProperty("data")
  private List<HashMap<String, String>> data;

  public String getKey() {
    return key;
  }

  public HashMap<String, String> getFields() {
    return fields;
  }

  public List<HashMap<String, String>> getData() {
    return data;
  }
}
