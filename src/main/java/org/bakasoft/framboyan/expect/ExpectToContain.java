package org.bakasoft.framboyan.expect;

import java.util.List;
import java.util.function.Consumer;

public class ExpectToContain {

  public static void pass(Object actual, Object... expected) {
    ExpectUtil.select(actual, expected, ExpectToContain::passList, ExpectToContain::passString);
  }

  public static void fail(Object actual, Object... expected) {
    ExpectUtil.select(actual, expected, ExpectToContain::failList, ExpectToContain::failString);
  }

  // list mode

  private static boolean findFirstMissing(List<?> actual, Object[] items, Consumer<Object> missing) {
    for (Object item : items) {
      if (!actual.contains(item)) {
        if (missing != null) {
          missing.accept(item);
        }
        return true;
      }
    }

    return false;
  }

  private static void passList(List<?> actual, Object[] expected) {
    findFirstMissing(actual, expected, missing -> {
      throw new ExpectError("Expected %s to contain %s.", actual, missing);
    });
  }

  private static void failList(List<?> actual, Object[] expected) {
    if (!findFirstMissing(actual, expected, null)) {
      throw new ExpectError("Expected %s to NOT contain %s.", actual, expected);
    }
  }

  // string mode

  private static String findFirstMissing(String actual, String[] parts) {
    for (String part : parts) {
      if (part != null && !actual.contains(part)) {
        return part;
      }
    }

    return null;
  }

  private static void passString(String actual, String[] expected) {
    String missing = findFirstMissing(actual, expected);

    if (missing != null) {
      throw new ExpectError("Expected %s to contain %s.", actual, missing);
    }
  }

  private static void failString(String actual, String[] expected) {
    String missing = findFirstMissing(actual, expected);

    if (missing == null) {
      throw new ExpectError("Expected %s to NOT contain %s.", actual, expected);
    }
  }

}
