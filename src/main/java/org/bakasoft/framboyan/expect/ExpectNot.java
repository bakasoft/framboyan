package org.bakasoft.framboyan.expect;

import org.bakasoft.framboyan.expect.logic.*;

public class ExpectNot {

  private final Object actual;

  public ExpectNot(Object actual) {
    this.actual = actual;
  }

  public void cast(Class<?> type) {
    ExpectCast.fail(actual, type);
  }

  public void get(Object key) {
    ExpectGet.fail(actual, key);
  }

  public void toBe(Object expected) {
    ExpectToBe.fail(actual, expected);
  }

  public void toEqual(Object expected) {
    ExpectToEqual.fail(actual, expected);
  }

  public void toBeLike(Object expected) {
    ExpectToBeLike.fail(actual, expected);
  }

  public void toBeEmpty() {
    ExpectToBeEmpty.fail(actual);
  }

  public void toBeNull() {
    ExpectToBeNull.fail(actual);
  }

  public void toStartWith(Object... items) {
    ExpectToStartWith.fail(actual, items);
  }

  public void toEndWith(Object... items) {
    ExpectToEndWith.fail(actual, items);
  }

  public void toMatch(Object pattern) {
    ExpectToMatch.fail(actual, pattern);
  }

  public void toContain(Object... expected) {
    ExpectToContain.fail(actual, expected);
  }

  public void toIterate(Object... expected) {
    ExpectToIterate.fail(actual, expected);
  }

  public void toBeInstanceOf(Class<?> type) {
    ExpectToBeInstanceOf.fail(actual, type);
  }

}
