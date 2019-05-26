package org.bakasoft.framboyan.expect;

import org.bakasoft.framboyan.test.TestCase;

public class Expect_toBeNull extends TestCase {{
  pass("Expected match", () -> {
    expect(null).toBeNull();
  });
  pass("Expected mismatch", () -> {
    expect(0).not.toBeNull();
  });
  fail("Unexpected mismatch", ExpectError.class, () -> {
    expect(1).toBeNull();
  });
  fail("Unexpected match", ExpectError.class, () -> {
    expect(null).not.toBeNull();
  });
}}