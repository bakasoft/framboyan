package org.bakasoft.framboyan.expect;

import java.util.function.BooleanSupplier;

public class ExpectToBeBoolean {
  private static boolean toBoolean(Object actual) {
    if (actual instanceof Boolean) {
      return (Boolean)actual;
    }
    else if (actual instanceof BooleanSupplier) {
      return ((BooleanSupplier)actual).getAsBoolean();
    }

    throw new ExpectError("Cannot determine the boolean value of %s", actual);
  }

  public static void shouldBeTrue(Object actual) {
    if (!toBoolean(actual)) {
      throw new ExpectError("Expected %s to be true.", actual);
    }
  }

  public static void shouldBeFalse(Object actual) {
    if (toBoolean(actual)) {
      throw new ExpectError("Expected %s to be false.", actual);
    }
  }
}
