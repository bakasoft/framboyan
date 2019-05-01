package org.bakasoft.framboyan.util;

import java.util.function.Function;

@FunctionalInterface
public interface Normalizer {

  Object apply(Object value);

}
