package org.bakasoft.framboyan.expect.logic;

import org.bakasoft.framboyan.expect.ExpectError;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class ExpectGet {

  private static Supplier<?> findReader(Object instance, Object key) {
    if (instance == null) {
      throw new ExpectError("Expected a non-null instance.");
    }
    else if (key == null) {
      throw new ExpectError("Expected a non-null key.");
    }
    else if (key instanceof Number && instance instanceof List) {
      Number index = (Number)key;
      List<?> list = (List<?>) instance;
      return () -> list.get(index.intValue());
    }
    else if (instance instanceof Map) {
      Map<?,?> map = (Map<?,?>) instance;

      if (map.containsKey(key)) {
        return () -> map.get(key);
      }
    }

    Class<?> instanceType = instance.getClass();

    try {
      BeanInfo beanInfo = Introspector.getBeanInfo(instanceType);
      PropertyDescriptor[] properties = beanInfo.getPropertyDescriptors();

      if (properties != null) {
        for (PropertyDescriptor property : properties) {
          if (key.equals(property.getName()) && property.getReadMethod() != null) {
            Method method = property.getReadMethod();

            return () -> {
              try {
                return method.invoke(instance);
              }
              catch (IllegalAccessException e) {
                throw new ExpectError(e, "Expected to have access to the method %s.", method);
              }
              catch (InvocationTargetException e) {
                throw new ExpectError(e, "Unexpected error invoking method %s.", method);
              }
            };
          }
        }
      }
    }
    catch (IntrospectionException e) {
      throw new ExpectError(e, "Expected %s to be a bean.", instanceType);
    }

    for (Field field : instanceType.getDeclaredFields()) {
      if (key.equals(field.getName())) {
        return () -> {
          try {
            return field.get(instance);
          } catch (IllegalAccessException e) {
            throw new ExpectError(e, "Expected to have access to the field %s.", field);
          }
        };
      }
    }

    return null;
  }

  public static <T> T pass(Object instance, Object key, Class<T> keyType) {
    Supplier<?> reader = findReader(instance, key);

    if (reader == null) {
      throw new ExpectError("Expected to get the value %s from the instance %s.", key, instance);
    }

    Object keyValue = reader.get();

    return ExpectCast.pass(keyValue, keyType);
  }

  public static void fail(Object instance, Object key) {
    Supplier<?> reader = findReader(instance, key);

    if (reader != null) {
      throw new ExpectError("Expected NOT to get the value %s from the instance %s.", key, instance);
    }
  }
}
