package org.bakasoft.framboyan.expect;

import org.bakasoft.framboyan.test.TestCase;

public class ExpectUtil_list extends TestCase {{
  pass("Convert to list", () -> {
    expect(ExpectUtil.list(new Object[]{1,2,3})).not.toBeNull();
  });
  fail("Error on unsupported values", ExpectError.class, () -> {
    ExpectUtil.list(new Object());
  });
}}