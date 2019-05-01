package org.bakasoft.framboyan.test;

import org.bakasoft.framboyan.util.ReflectionHelper;

import java.lang.reflect.Modifier;
import java.util.*;

public class Framboyan {

  public static void run(String... packageNames) {
    StandardRunner runner = new StandardRunner(System.out);

    for (String packageName : packageNames) {
      String packagePrefix = packageName + ".";

      List<Class<?>> testTypes = ReflectionHelper
          .collectClasses(
              className -> className.startsWith(packagePrefix) || className.equals(packageName),
              type ->
                  Testable.class.isAssignableFrom(type)
                      && !Modifier.isAbstract(type.getModifiers())
                      && !type.isInterface());

      runner.run(packageName, testTypes);
    }
  }

}
