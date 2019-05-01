package org.bakasoft.framboyan.util;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class ReflectionHelper {
  public static Class<?> loadType(String typeName) {
    try {
      return Class.forName(typeName);
    }
    catch (ClassNotFoundException e) {
      throw new RuntimeException(e);
    }
  }
  private static Path toPath(URL url) {
    try {
      return Paths.get(url.toURI());
    }
    catch(URISyntaxException e) {
      throw new RuntimeException(e);
    }
  }
  private static Stream<Path> walk(Path root) {
    try {
      return Files.walk(root);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
  private static List<Path> listRoots() {
    Enumeration<URL> resources;

    try {
      resources = ClassLoader.getSystemResources("");
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    ArrayList<Path> roots = new ArrayList<>();

    while (resources.hasMoreElements()) {
      URL url = resources.nextElement();
      Path root = toPath(url);

      roots.add(root);
    }

    return roots;
  }
  public static List<Class<?>> collectClasses(Predicate<String> nameCondition, Predicate<Class<?>> typeCondition) {
    ArrayList<Class<?>> result = new ArrayList<>();
    List<Path> roots = listRoots();

    for (Path root : roots) {
      walk(root)
          .filter(Files::isRegularFile)
          .filter(path -> path.toString().toLowerCase().endsWith(".class"))
          .map(root::relativize)
          .map(Path::toString)
          .map(path -> path
              .substring(0, path.length() - 6)
              .replace('/', '.'))
          .filter(nameCondition)
          .map(ReflectionHelper::loadType)
          .filter(typeCondition)
          .forEach(result::add);
    }

    return result;
  }
}
