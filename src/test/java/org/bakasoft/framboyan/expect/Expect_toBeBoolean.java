package org.bakasoft.framboyan.expect;

import org.bakasoft.framboyan.test.TestCase;

import java.util.function.BooleanSupplier;

public class Expect_toBeBoolean extends TestCase {{
  pass("Expected true", () -> {
    expect(true).toBeTrue();
  });
  pass("Expected false", () -> {
    expect(false).toBeFalse();
  });
  fail("Unexpected true", ExpectError.class, () -> {
    expect(true).toBeFalse();
  });
  fail("Unexpected false", ExpectError.class, () -> {
    expect(false).toBeTrue();
  });
  pass("Using BooleanSupplier", () -> {
    expect(((BooleanSupplier)() -> false)).toBeFalse();
    expect(((BooleanSupplier)() -> true)).toBeTrue();
  });
  pass ("Not valid boolean", () -> {
    fail("null", ExpectError.class, () -> {
      expect(null).toBeTrue();
    });
    fail("0", ExpectError.class, () -> {
      expect(0).toBeFalse();
    });
    fail("1", ExpectError.class, () -> {
      expect(1).toBeTrue();
    });
    fail("Object", ExpectError.class, () -> {
      expect(new Object()).toBeFalse();
    });
  });
}}