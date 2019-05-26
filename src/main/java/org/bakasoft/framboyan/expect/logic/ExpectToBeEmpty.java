package org.bakasoft.framboyan.expect.logic;

import org.bakasoft.framboyan.expect.ExpectError;
import org.bakasoft.framboyan.util.Caster;

import java.util.List;
import java.util.Map;

public class ExpectToBeEmpty {
  private static boolean isEmpty(Object actual) {
    if (actual == null) {
      return true;
    }

    List<?> actualList = Caster.toList(actual);

    if (actualList != null) {
      return actualList.isEmpty();
    }

    String actualString = Caster.toString(actual);

    if (actualString != null) {
      return actualString.isEmpty();
    }
    else if (actual instanceof Map) {
      return ((Map)actual).isEmpty();
    }

    throw new ExpectError("Cannot determine if %s is empty.", actual);
  }

  public static void pass(Object actual) {
    if (!isEmpty(actual)) {
      throw new ExpectError("Expected %s to be empty.", actual);
    }
  }

  public static void fail(Object actual) {
    if (isEmpty(actual)) {
      throw new ExpectError("Expected %s NOT to be empty.", actual);
    }
  }
}
