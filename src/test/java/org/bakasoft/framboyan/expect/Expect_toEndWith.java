package org.bakasoft.framboyan.expect;

import org.bakasoft.framboyan.test.TestCase;

public class Expect_toEndWith extends TestCase {{
  pass("String should end with prefix", () -> {
    expect("image.jpg").toEndWith(".jpg");
  });
  pass("String should not end with prefix", () -> {
    expect("image.jpg").not.toEndWith(".png");
  });
  fail("Unexpected match", ExpectError.class, () -> {
    expect("image.gif").not.toEndWith(".gif");
  });
  fail("Unexpected mismatch", ExpectError.class, () -> {
    expect("image.gif").toEndWith(".jpg");
  });
}}