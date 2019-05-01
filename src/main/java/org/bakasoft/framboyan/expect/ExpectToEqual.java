package org.bakasoft.framboyan.expect;

import java.util.Objects;

public class ExpectToEqual {
  public static void pass(Object actual, Object expected) {
    if (!Objects.equals(actual, expected)) {
      throw new ExpectError(
          "Expected %s to equal %s.",
          actual,
          expected);
    }
  }

  public static void fail(Object actual, Object expected) {
    if (Objects.equals(actual, expected)) {
      throw new ExpectError(
          "Expected %s NOT to equal %s.",
          actual,
          expected);
    }
  }
}
