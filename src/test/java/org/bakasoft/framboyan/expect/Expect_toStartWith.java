package org.bakasoft.framboyan.expect;

import org.bakasoft.framboyan.test.TestCase;

public class Expect_toStartWith extends TestCase {{
  pass("String should start with prefix", () -> {
    expect("-10").toStartWith("-");
  });
  pass("String should not start with prefix", () -> {
    expect("+10").not.toStartWith("-");
  });
  fail("Unexpected match", ExpectError.class, () -> {
    expect("-10").not.toStartWith("-");
  });
  fail("Unexpected mismatch", ExpectError.class, () -> {
    expect("+10").toStartWith("-");
  });
}}