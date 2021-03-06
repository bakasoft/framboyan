package org.bakasoft.framboyan.expect.logic;

import org.bakasoft.framboyan.expect.ExpectError;

public class ExpectToBeNull {
  public static void pass(Object actual) {
    if (actual != null) {
      throw new ExpectError("Expected %s to be null.", actual);
    }
  }

  public static void fail(Object actual) {
    if (actual == null) {
      throw new ExpectError("Expected %s to be not null.", actual);
    }
  }
}
