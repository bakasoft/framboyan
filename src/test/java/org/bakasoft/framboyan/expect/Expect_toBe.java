package org.bakasoft.framboyan.expect;

import org.bakasoft.framboyan.test.TestCase;

public class Expect_toBe extends TestCase {{
  Object obj1 = new Object();
  Object obj2 = new Object();

  pass("Expected equality", () -> {
    expect(obj1).toBe(obj1);
  });
  pass("Expected difference", () -> {
    expect(obj1).not.toBe(obj2);
  });
  fail("Unexpected difference", ExpectError.class, () -> {
    expect(obj1).toBe(obj2);
  });
  fail("Unexpected equality", ExpectError.class, () -> {
    expect(obj1).not.toBe(obj1);
  });
}}