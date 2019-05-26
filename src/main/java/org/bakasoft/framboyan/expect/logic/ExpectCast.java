package org.bakasoft.framboyan.expect.logic;

import org.bakasoft.framboyan.expect.ExpectError;

public class ExpectCast {

  public static <T> T pass(Object actual, Class<T> type) {
    if (type == null) {
      throw new ExpectError("Expected a non-null type.");
    }
    else if (actual == null) {
      if (type.isPrimitive()) {
        throw new ExpectError("Expected a non-null value since the type is primitive.");
      }

      return null;
    }
    else if (type.isInstance(actual)) {
      return type.cast(actual);
    }
    else {
      throw new ExpectError("Expected %s to be casted %s", actual, type);
    }
  }

  public static void fail(Object actual, Class<?> type) {
    if (type.isInstance(actual)) {
      throw new ExpectError("Expected %s NOT to be casted %s", actual, type);
    }
  }

}
