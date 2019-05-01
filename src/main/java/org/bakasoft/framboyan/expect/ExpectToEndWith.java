package org.bakasoft.framboyan.expect;

import java.util.Arrays;
import java.util.List;

public class ExpectToEndWith {

  public static void pass(Object actual, Object[] expected) {
    ExpectUtil.select(actual, expected, ExpectToEndWith::passList, ExpectToEndWith::passString);
  }

  public static void fail(Object actual, Object[] expected) {
    ExpectUtil.select(actual, expected, ExpectToEndWith::failList, ExpectToEndWith::failString);
  }

  private static boolean anyMatch(String actual, String[] suffixes) {
    return Arrays.stream(suffixes).anyMatch(actual::endsWith);
  }

  private static void passString(String actual, String[] suffixes) {
    if (!anyMatch(actual, suffixes)) {
      throw new ExpectError("Expected %s to end with any of %s", actual, suffixes);
    }
  }

  private static void failString(String actual, String[] suffixes) {
    if (anyMatch(actual, suffixes)) {
      throw new ExpectError("Expected %s to NOT end with any of %s", actual, suffixes);
    }
  }

  private static void passList(List<?> actual, Object[] items) {
    throw new UnsupportedOperationException(); // TODO implement this expectation
  }

  private static void failList(List<?> actual, Object[] items) {
    throw new UnsupportedOperationException(); // TODO implement this expectation
  }


}
