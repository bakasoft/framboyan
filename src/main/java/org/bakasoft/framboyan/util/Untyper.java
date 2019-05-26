package org.bakasoft.framboyan.util;

import org.bakasoft.beat.Beat;
import org.bakasoft.beat.BeatProperty;
import org.bakasoft.beat.BeatType;

import java.util.*;
import java.util.stream.Collectors;

public class Untyper implements Normalizer {

  @Override
  public Object apply(Object value) {
    return untype(value);
  }

  public static Object untype(Object value) {
    return untype(value, new HashMap<>());
  }

  private static Object untype(Object value, HashMap<Object, Object> cache) {
    if (value == null || value instanceof String || value instanceof Number || value instanceof Boolean) {
      return value;
    }
    else if (cache.containsKey(value)) {
      return cache.get(value);
    }

    Object result;

    String str = Caster.toString(value);

    if (str != null) {
      result = str;

      cache.put(value, str);
    }
    else {
      List<?> list = Caster.toList(value);

      if (list != null) {
        result = untypeList(value, list, cache);
      } else if (value instanceof Map) {
        result = untypeMap(value, (Map<?, ?>) value, cache);
      } else {
        result = untypeObject(value, cache);
      }
    }

    return result;
  }

  private static Object untypeObject(Object obj, HashMap<Object,Object> cache) {
    LinkedHashMap<String, Object> result = new LinkedHashMap<>();

    cache.put(obj, result);

    result.put("@id", Integer.toHexString(obj.hashCode()));
    result.put("@type", obj.getClass().getName());

    BeatType type = Beat.loadType(obj.getClass());

    for (BeatProperty property : type.getProperties()) {
      String key = property.getName();
      Object value = property.get(obj);
      Object untypedValue = untype(value, cache);

      result.put(key, untypedValue);
    }

    return result;
  }

  private static List<?> untypeList(Object instance, List<?> list, HashMap<Object,Object> cache) {
    ArrayList<Object> result = new ArrayList<>(list.size());

    cache.put(instance, result);

    for (Object item : list) {
      Object untypedItem = untype(item, cache);

      result.add(untypedItem);
    }

    return result;
  }

  private static Map<?,?> untypeMap(Object instance, Map<?, ?> map, HashMap<Object, Object> cache) {
    LinkedHashMap<Object, Object> result = new LinkedHashMap<>();

    cache.put(instance, result);

    map.forEach((key, value) -> {
      Object untypedValue = untype(value, cache);

      result.put(key, untypedValue);
    });

    return result;
  }

}
