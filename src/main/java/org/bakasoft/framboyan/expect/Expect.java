package org.bakasoft.framboyan.expect;

import org.bakasoft.framboyan.expect.logic.*;

public class Expect {

  private final Object actual;
  public final ExpectNot not;

  public Expect(Object actual) {
    this.actual = actual;
    this.not = new ExpectNot(actual);
  }

  public <T> T cast(Class<T> type) {
    return ExpectCast.pass(actual, type);
  }

  public Object get(String key) {
    return ExpectGet.pass(actual, key, Object.class);
  }

  public <T> T get(Object key, Class<T> type) {
    return ExpectGet.pass(actual, key, type);
  }

  public Expect toBe(Object expected) {
    ExpectToBe.pass(actual, expected);
    return this;
  }

  public Expect toEqual(Object expected) {
    ExpectToEqual.pass(actual, expected);
    return this;
  }

  public Expect toBeLike(Object expected) {
    ExpectToBeLike.pass(actual, expected);
    return this;
  }

  public Expect toBeEmpty() {
    ExpectToBeEmpty.pass(actual);
    return this;
  }

  public Expect toBeTrue() {
    ExpectToBeBoolean.shouldBeTrue(actual);
    return this;
  }

  public Expect toBeFalse() {
    ExpectToBeBoolean.shouldBeFalse(actual);
    return this;
  }

  public Expect toBeNull() {
    ExpectToBeNull.pass(actual);
    return this;
  }

  public Expect toStartWith(Object... items) {
    ExpectToStartWith.pass(actual, items);
    return this;
  }

  public Expect toEndWith(Object... items) {
    ExpectToEndWith.pass(actual, items);
    return this;
  }

  public Expect toMatch(Object pattern) {
    ExpectToMatch.pass(actual, pattern);
    return this;
  }

  public Expect toContain(Object... expected) {
    ExpectToContain.pass(actual, expected);
    return this;
  }

  public Expect toIterate(Object... expected) {
    ExpectToIterate.pass(actual, expected);
    return this;
  }

  public Expect toBeInstanceOf(Class<?> type) {
    ExpectToBeInstanceOf.pass(actual, type);
    return this;
  }

}
