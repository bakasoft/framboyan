package org.bakasoft.framboyan.expect;

import org.bakasoft.framboyan.test.TestCase;

public class Expect_toEqual extends TestCase {{
  pass("Expected match", () -> {
    expect("a").toEqual("a");
  });
  pass("Expected mismatch", () -> {
    expect("a").not.toEqual("z");
  });
  fail("Unexpected mismatch", ExpectError.class, () -> {
    expect("a").toEqual("z");
  });
  fail("Unexpected match", ExpectError.class, () -> {
    expect("a").not.toEqual("a");
  });
}}