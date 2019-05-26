package org.bakasoft.framboyan.expect.logic;

import org.bakasoft.framboyan.expect.ExpectError;
import org.bakasoft.framboyan.expect.ExpectUtil;
import org.bakasoft.framboyan.util.Caster;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class ExpectToIterate {
  public static void pass(Object actual, Object[] expected) {
    List<?> actualList = ExpectUtil.list(actual);
    int minLength = Math.min(actualList.size(), expected.length);

    for (int index = 0; index < minLength; index++) {
      Object actualItem = actualList.get(index);
      Object expectedItem = expected[index];

      if (!Objects.equals(actualItem, expectedItem)) {
        throw new ExpectError(
            "Expected %s to be %s at index %s.",
            actualItem,
            expectedItem,
            index);
      }
    }

    if (actualList.size() != expected.length) {
      throw new ExpectError("Expected %s length to be %s.", actualList, expected.length);
    }
  }

  public static void fail(Object actual, Object[] expected) {
    List<?> actualList = ExpectUtil.list(actual);
    int minLength = Math.min(actualList.size(), expected.length);

    if (actualList.size() == expected.length) {
      boolean equal = true;

      for (int i = 0; i < minLength; i++) {
        Object actualItem = actualList.get(i);
        Object expectedItem = expected[i];

        if (!Objects.equals(actualItem, expectedItem)) {
          equal = false;
          break;
        }
      }

      if (equal) {
        throw new ExpectError(
            "Expected %s to be different.", actual);
      }
    }
  }
}
