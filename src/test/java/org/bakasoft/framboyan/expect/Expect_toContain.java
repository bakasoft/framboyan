package org.bakasoft.framboyan.expect;

import org.bakasoft.framboyan.test.TestCase;

import java.util.Arrays;

public class Expect_toContain extends TestCase {{
  pass("Expected list containing items", () -> {
    expect(Arrays.asList(1, 2, 3, 4, 5)).toContain(1, 3, 5);
  });
  pass("Expected string containing parts", () -> {
    expect("abcde").toContain("ab", "cd", "e");
  });
  pass("Expected list not containing items", () -> {
    expect(Arrays.asList(1, 2, 3)).not.toContain(4, 5);
  });
  pass("Expected string not containing parts", () -> {
    expect("abcde").not.toContain("f", "g", "ABC");
  });
  fail("Unexpected list containing items", ExpectError.class, () -> {
    expect(Arrays.asList(1, 2, 3)).toContain(4, 5);
  });
  fail("Unexpected string containing parts", ExpectError.class, () -> {
    expect("abcde").toContain("f", "g");
  });
  fail("Unexpected list not containing items", ExpectError.class, () -> {
    expect(Arrays.asList(1, 2, 3)).not.toContain(1);
  });
  fail("Unexpected string not containing parts", ExpectError.class, () -> {
    expect("abc").not.toContain("a");
  });
}}