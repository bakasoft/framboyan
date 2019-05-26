package org.bakasoft.framboyan.expect;

import org.bakasoft.framboyan.test.TestCase;

public class ExpectUtil_string extends TestCase {{
  pass("Convert to string", () -> {
    expect(ExpectUtil.string('X')).not.toBeNull();
  });
  fail("Error on unsupported values", ExpectError.class, () -> {
    ExpectUtil.string(new Object());
  });
}}