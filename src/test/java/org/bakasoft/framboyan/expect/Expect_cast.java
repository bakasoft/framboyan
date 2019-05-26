package org.bakasoft.framboyan.expect;

import org.bakasoft.framboyan.test.TestCase;
import org.bakasoft.framboyan.test.annotations.Test;

public class Expect_cast extends TestCase {{

  pass("Valid cast", () -> {
    Double value = expect(1.0).cast(Double.class);

    expect(value).toEqual(1.0);
  });
  pass("Null to nullable", () -> {
    Object value = expect(null).cast(Object.class);

    expect(value).toBeNull();
  });
  pass("Exepcted mismatch", () -> {
    expect(1L).not.cast(Integer.class);
  });
  fail("Missing type", ExpectError.class, () -> {
    expect(0).cast(null);
  });
  fail("Null to primitive", ExpectError.class, () -> {
    expect(null).cast(int.class);
  });
  fail("Invalid class", ExpectError.class, () -> {
    expect("").cast(Boolean.class);
  });
  fail("Unexpected cast", ExpectError.class, () -> {
    expect(true).not.cast(Boolean.class);
  });

}}
