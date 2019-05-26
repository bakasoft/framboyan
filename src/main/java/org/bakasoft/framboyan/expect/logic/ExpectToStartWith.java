package org.bakasoft.framboyan.expect.logic;

import org.bakasoft.framboyan.expect.ExpectError;
import org.bakasoft.framboyan.expect.ExpectUtil;

import java.util.Arrays;
import java.util.List;

public class ExpectToStartWith {

  public static void pass(Object actual, Object[] expected) {
    ExpectUtil.select(actual, expected, ExpectToStartWith::passList, ExpectToStartWith::passString);
  }

  public static void fail(Object actual, Object[] expected) {
    ExpectUtil.select(actual, expected, ExpectToStartWith::failList, ExpectToStartWith::failString);
  }

  private static boolean anyMatch(String actual, String[] suffixes) {
    return Arrays.stream(suffixes).anyMatch(actual::startsWith);
  }

  private static void passString(String actual, String[] suffixes) {
    if (!anyMatch(actual, suffixes)) {
      throw new ExpectError("Expected %s to start with any of %s", actual, suffixes);
    }
  }

  private static void failString(String actual, String[] suffixes) {
    if (anyMatch(actual, suffixes)) {
      throw new ExpectError("Expected %s to NOT start with any of %s", actual, suffixes);
    }
  }

  private static void passList(List<?> actual, Object[] items) {
    throw new UnsupportedOperationException(); // TODO implement this expectation
  }

  private static void failList(List<?> actual, Object[] items) {
    throw new UnsupportedOperationException(); // TODO implement this expectation
  }

}
