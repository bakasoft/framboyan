package org.bakasoft.framboyan.expect;

import org.bakasoft.framboyan.test.TestCase;

import java.util.Arrays;

public class Expect_toBeLike extends TestCase {{
  pass("Expected match", () -> {
    expect(Arrays.asList(1, 2, 3)).toBeLike(Arrays.asList(1, 2, 3));
  });
  pass("Expected mismatch", () -> {
    expect(Arrays.asList(1, 2, 3)).not.toBeLike(Arrays.asList(10, 20, 30));
  });
  fail("Unexpected mismatch", ExpectError.class, () -> {
    expect(Arrays.asList(1, 2, 3)).toBeLike(Arrays.asList(10, 20, 30));
  });
  fail("Unexpected match", ExpectError.class, () -> {
    expect(Arrays.asList(1, 2, 3)).not.toBeLike(Arrays.asList(1, 2, 3));
  });
}}