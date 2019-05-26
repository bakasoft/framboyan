package org.bakasoft.framboyan.expect;

import org.bakasoft.framboyan.test.TestCase;

import java.util.Arrays;

public class Expect_toIterate extends TestCase {{
  pass("Expected list iterating items", () -> {
    expect(Arrays.asList(1, 2, 3)).toIterate(1, 2, 3);
  });
  pass("Expected list not iterating items", () -> {
    expect(Arrays.asList(1, 2, 3)).not.toIterate(10, 20, 30);
  });
  fail("Unexpected list iterating items (different items)", ExpectError.class, () -> {
    expect(Arrays.asList(1, 2, 3)).toIterate(10, 20, 30);
  });
  fail("Unexpected list iterating items (different length)", ExpectError.class, () -> {
    expect(Arrays.asList(1, 2, 3)).toIterate(1, 2, 3, 4);
  });
  fail("Unexpected list not iterating items", ExpectError.class, () -> {
    expect(Arrays.asList(1, 2, 3)).not.toIterate(1, 2, 3);
  });
}}