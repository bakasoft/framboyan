package org.bakasoft.framboyan.util;

import org.bakasoft.framboyan.test.FramboyanTest;

import java.io.StringWriter;

public class Caster_toString extends FramboyanTest {{
  pass("String objects shouldn't be touched", () -> {
    String str = "This is a String";

    expect(Caster.toString(str)).toBe(str);
  });

  pass("Character values should be converted to String", () -> {
    expect(Caster.toString('C')).toEqual("C");
  });

  pass("CharSequences should be converted to String", () -> {
    pass("StringBuilder", () -> {
      expect(Caster.toString(new StringBuilder(":)"))).toEqual(":)");
    });
    pass("StringBuffer", () -> {
      expect(Caster.toString(new StringBuffer(":D"))).toEqual(":D");
    });
  });

  pass("StringWriter values should be converted to String", () -> {
    StringWriter writer = new StringWriter();
    writer.write(":3");
    expect(Caster.toString(writer)).toEqual(":3");
  });

  pass("Null values shouldn't be touched", () -> {
    expect(Caster.toString(null)).toBeNull();
  });

  pass("Other values should be null", () -> {
    expect(Caster.toString(1)).toBeNull();
    expect(Caster.toString(true)).toBeNull();
    expect(Caster.toString(new Object())).toBeNull();
    expect(Caster.toString(new Exception())).toBeNull();
  });
}}
