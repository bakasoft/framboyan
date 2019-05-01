package org.bakasoft.framboyan.util;

import java.io.StringWriter;
import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.BaseStream;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Caster {

  public static List<?> toList(Object value) {
    if (value instanceof List) {
      return (List<?>) value;
    }
    else if (value instanceof Stream) {
      return ((Stream<?>)value).collect(Collectors.toList());
    }
    else if (value instanceof Enumeration) {
      return Collections.list((Enumeration<?>)value);
    }
    else if (value instanceof Iterable) {
      ArrayList<Object> items = new ArrayList<>();
      ((Iterable<?>)value).forEach(items::add);
      return items;
    }
    else if (value instanceof BaseStream) {
      Iterator<?> iterator = ((BaseStream<?, ?>)value).iterator();
      ArrayList<Object> items = new ArrayList<>();
      iterator.forEachRemaining(items::add);
      return items;
    }
    else if (value != null && value.getClass().isArray()) {
      int length = Array.getLength(value);
      ArrayList<Object> items = new ArrayList<>(length);
      for (int i = 0; i < length; i++) {
        Object item = Array.get(value, i);
        items.add(item);
      }
      return items;
    }

    return null;
  }

  public static String toString(Object value) {
    if (value instanceof String) {
      return (String)value;
    }
    else if (value instanceof Character) {
      return String.valueOf(value);
    }
    else if (value instanceof CharSequence || value instanceof StringWriter) {
      return value.toString();
    }

    return null;
  }

  public static String[] toStringArray(Object[] values) {
    if (values == null) {
      return null;
    }

    String[] array = new String[values.length];

    for (int i = 0; i < array.length; i++) {
      if (values[i] == null) {
        array[i] = null;
      }
      else {
        String value = toString(values[i]);

        if (value == null) {
          return null;
        }

        array[i] = value;
      }
    }

    return array;
  }


}
