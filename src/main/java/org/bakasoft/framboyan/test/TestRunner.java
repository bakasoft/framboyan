package org.bakasoft.framboyan.test;

import org.bakasoft.framboyan.test.annotations.Test;
import org.bakasoft.framboyan.util.ReflectionHelper;

import java.lang.reflect.Modifier;
import java.util.List;
import java.util.stream.Collectors;

public interface TestRunner {

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

      // check for focused tests
      List<Class<?>> focused = testTypes.stream()
          .filter(type -> {
            Test t = type.getAnnotation(Test.class);
            return t != null && t.focus();
          })
          .collect(Collectors.toList());

      if (focused.isEmpty()) {
        runner.run(packageName, testTypes);
      }
      else {
        runner.run(packageName, focused);
      }
    }
  }

}
