package org.bakasoft.framboyan.expect;

import org.bakasoft.framboyan.test.TestCase;

import java.util.regex.Pattern;

public class Expect_toMatch extends TestCase {{
  pass("Expected string to match string pattern", () -> {
    expect("aaa").toMatch("a+");
  });
  pass("Expected string to match pattern instance", () -> {
    expect("aaa").toMatch(Pattern.compile("a+"));
  });
  pass("Expected string NOT to match some pattern", () -> {
    expect("abc").not.toMatch("a+");
  });
  fail("Unexpected string matching pattern", ExpectError.class, () -> {
    expect("aaa").not.toMatch("a+");
  });
  fail("Unexpected string not matching pattern", ExpectError.class, () -> {
    expect("bbb").toMatch("a+");
  });
}}