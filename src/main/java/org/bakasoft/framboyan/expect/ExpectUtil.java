package org.bakasoft.framboyan.expect;

import org.bakasoft.framboyan.util.Caster;

import java.util.List;
import java.util.function.BiConsumer;

public class ExpectUtil {
  public static void select(Object actual, Object[] expected, BiConsumer<List<?>, Object[]> listCheck, BiConsumer<String, String[]> stringCheck) {
    List<?> list = Caster.toList(actual);

    if (list != null) {
      listCheck.accept(list, expected);
    }
    else {
      String str = Caster.toString(actual);

      if (str == null) {
        throw new ExpectError("Cannot convert %s to list or string.", actual);
      }

      String[] parts = Caster.toStringArray(expected);

      if (parts == null) {
        throw new ExpectError("Cannot convert %s to a string list.", expected);
      }

      stringCheck.accept(str, parts);
    }
  }

  public static List<?> list(Object actual) {
    List<?> list = Caster.toList(actual);

    if (list == null) {
      throw new ExpectError("Expected %s to be a List.", actual);
    }

    return list;
  }

  public static String string(Object actual) {
    String str = Caster.toString(actual);

    if (str == null) {
      throw new ExpectError("Expected %s to be String.", actual);
    }

    return str;
  }
}
