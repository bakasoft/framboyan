package org.bakasoft.framboyan.expect;

import org.bakasoft.framboyan.test.TestCase;

public class ExpectUtil_select extends TestCase {{
  fail("Unsupported actual value", ExpectError.class, () -> {
    ExpectUtil.select(new Object(), new Object[0], null, null);
  });
  fail("Unsupported expected value", ExpectError.class, () -> {
    ExpectUtil.select("", new Object[]{10}, null, null);
  });
}}