package org.bakasoft.framboyan.expect.logic;

import org.bakasoft.framboyan.expect.ExpectError;

public class ExpectToBe {
  public static void pass(Object actual, Object expected) {
    if (actual != expected) {
      throw new ExpectError(
          "Expected %s to be %s.",
          actual,
          expected);
    }
  }

  public static void fail(Object actual, Object expected) {
    if (actual == expected) {
      throw new ExpectError(
          "Expected %s NOT to be %s.",
          actual,
          expected);
    }
  }
}
