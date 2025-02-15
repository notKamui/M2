/* DO NOT EDIT THIS FILE */
package com.td.either;

import java.util.*;
import java.util.stream.Collectors;

/**
 * A Java driver to read a dictionary
 * Some definitions come from https://degoes.net/articles/fp-glossary
 */
public class JavaDriver {

  private final Map<String, String> dictionary;

  public JavaDriver() {
    this.dictionary = new HashMap<>();

    this.dictionary.put("deterministic", "A deterministic procedure returns the same output for the same input");
    this.dictionary.put("kind", "A kind describes the structure of a type or type constructor");
    this.dictionary.put("nullsafe", "Prevent using `null` and thus NullPointerException");
    this.dictionary.put("typesafe", "Ensuring through the type system that no RuntimeException will be thrown");
    this.dictionary.put("FP", "Functional programming is a style of programming in which solutions are constructed by defining and applying (mathematical) functions");
  }

  /**
   * Find one definition from the dictionary
   * @param key the entry to the dictionary
   * @return the value associated, null if not found
   */
  String findOne(String key) {
    try {
      return dictionary.get(key);
    } catch (ClassCastException | NullPointerException exception) {
      return null;
    }
  }

  /**
   * Find multiple definitions from the dictionary
   * @param keys the entries to the dictionary
   * @return a list of the values associated, the value is null if not found
   */
  List<String> findMany(List<String> keys) {
    List<String> result = new LinkedList<>();
    keys.stream().map(this::findOne)
            .forEach(result::add);
    return result;
  }

  /**
   * Find all the definitions from the dictionary
   * @param max the max number to query
   * @return the values associated
   * @throws RequestSizeException if the request is over the maximum
   */
  List<String> findAll(int max) throws RequestSizeException {
    if (max > 2) throw new RequestSizeException();
    return dictionary.values().stream().sorted().limit(max).collect(Collectors.toList());
  }
}
