package org.bakasoft.framboyan.expect;

import org.bakasoft.framboyan.diff.Diff;
import org.bakasoft.framboyan.diff.DiffItem;

public class ExpectToBeLike {
  public static DiffItem diff(Object actual, Object expected) {
    Diff diff = new Diff();

    return diff.diff(expected, actual);
  }

  public static void pass(Object actual, Object expected) {
    DiffItem diffItem = diff(actual, expected);

    if (diffItem != null) {
      throw new ExpectError(diffItem.toString());
    }
  }

  public static void fail(Object actual, Object expected) {
    DiffItem diffItem = diff(actual, expected);

    if (diffItem == null) {
      throw new ExpectError(
          "Expected %s NOT to be like %s",
          actual,
          expected);
    }
  }
}
