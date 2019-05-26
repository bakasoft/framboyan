package org.bakasoft.framboyan.expect.logic;

import org.bakasoft.framboyan.expect.ExpectError;
import org.bakasoft.framboyan.expect.ExpectUtil;

import java.util.function.BiConsumer;
import java.util.regex.Pattern;

public class ExpectToMatch {
  private static void select(Object actual, Object pattern, Runnable positive, Runnable negative) {
    String str = ExpectUtil.string(actual);
    Pattern pat;

    if (pattern instanceof Pattern) {
      pat = (Pattern)pattern;
    }
    else {
      String patStr = ExpectUtil.string(pattern);

      pat = Pattern.compile(patStr);
    }

    if (pat.matcher(str).matches()) {
      if (positive != null) {
        positive.run();
      }
    }
    else if (negative != null) {
      negative.run();
    }
  }

  public static void pass(Object actual, Object pattern) {
    select(actual, pattern, null,
        () -> {
          throw new ExpectError("Expected %s to match %s.", actual, pattern);
        });
  }

  public static void fail(Object actual, Object pattern) {
    select(actual, pattern,
        () -> {
          throw new ExpectError("Expected %s NOT to match %s.", actual, pattern);
        }, null);
  }
}
