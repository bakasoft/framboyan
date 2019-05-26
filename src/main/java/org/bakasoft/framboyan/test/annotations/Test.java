package org.bakasoft.framboyan.test.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Test {

  String value() default "";
  boolean ignore() default false;
  boolean focus() default false;

}
