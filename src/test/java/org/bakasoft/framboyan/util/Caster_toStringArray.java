package org.bakasoft.framboyan.util;

import org.bakasoft.framboyan.test.FramboyanTest;

public class Caster_toStringArray extends FramboyanTest {{

  pass("Null values shouldn't be touched", () -> {
    expect(Caster.toStringArray(null))
        .toBeNull();
  });

  pass("Null items shouldn't be touched", () -> {
    expect(Caster.toStringArray(new Object[] {"a", null, 'z'}))
        .toIterate("a", null, "z");
  });

  pass("Supported String items converts to String array", () -> {
    expect(Caster.toStringArray(new Object[] {"a", new StringBuilder("?"), 'z'}))
        .toIterate("a", "?", "z");
  });

  pass("Unsupported String items generate null result", () -> {
    expect(Caster.toStringArray(new Object[] {"a", 10, 'z'}))
        .toBeNull();
  });

}}