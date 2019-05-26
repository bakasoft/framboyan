package org.bakasoft.framboyan.expect;

import org.bakasoft.framboyan.test.TestCase;

import java.util.*;

public class Expect_toBeEmpty extends TestCase {{
  pass("Null is considered empty", () -> {
    expect(null).toBeEmpty();
  });
  pass("Check list support", () -> {
    // more detailed tests found in Caster_toList
    expect(new ArrayList<>()).toBeEmpty();
    expect(new Object[0]).toBeEmpty();
    expect(Arrays.stream(new Object[0])).toBeEmpty();
    expect(Collections.enumeration(new ArrayList<>())).toBeEmpty();
  });
  pass("Check string support", () -> {
    // more detailed tests found in Caster_toString
    expect("").toBeEmpty();
    expect(new StringBuilder()).toBeEmpty();
  });
  pass("Check map support", () -> {
    expect(new HashMap<>()).toBeEmpty();
    expect(new LinkedHashMap<>()).toBeEmpty();
  });
  pass("Expected not empty", () -> {
    expect(Arrays.asList(1, 2, 3)).not.toBeEmpty();
  });
  fail("Unsupported values", ExpectError.class, () -> {
    expect(new Object()).toBeEmpty();
  });
  fail("Unexpected not empty", ExpectError.class, () -> {
    expect(Arrays.asList(1, 2, 3)).toBeEmpty();
  });
  fail("Unexpected empty", ExpectError.class, () -> {
    expect(new ArrayList<>()).not.toBeEmpty();
  });
}}