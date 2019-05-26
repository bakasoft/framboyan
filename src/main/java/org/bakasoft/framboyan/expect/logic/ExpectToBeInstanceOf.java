package org.bakasoft.framboyan.expect.logic;

import org.bakasoft.framboyan.expect.ExpectError;

public class ExpectToBeInstanceOf {
  public static void pass(Object instance, Class<?> type) {
    if (!type.isInstance(instance)) {
      throw new ExpectError(
          "Expected %s to be instance of %s.",
          instance,
          type);
    }
  }

  public static void fail(Object instance, Class<?> type) {
    if (type.isInstance(instance)) {
      throw new ExpectError(
          "Expected %s NOT to be instance of %s.",
          instance,
          type);
    }
  }
}
