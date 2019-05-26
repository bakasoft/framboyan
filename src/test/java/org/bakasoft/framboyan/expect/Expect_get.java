package org.bakasoft.framboyan.expect;

import org.bakasoft.framboyan.test.TestCase;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Expect_get extends TestCase {

  public class DummyObject {
    public String field1 = "1";
    private String field2 = "2";

    public String getField3() { return "3"; }
    public String getField4() { throw new RuntimeException(); }
  }

  {
    pass("Lists", () -> {
      List<?> list = Arrays.asList("a", "b", "c");

      String value = expect(list).get(1, String.class);

      expect(value).toEqual("b");
    });

    pass("Maps", () -> {
      Map<String, Number> map = new HashMap<>();

      map.put("width", 3.5);

      Number value = expect(map).get("width", Number.class);

      expect(value).toEqual(3.5);
    });

    pass("Fields", () -> {
      pass("public", () -> {
        expect(expect(new DummyObject()).get("field1")).toEqual("1");
      });
      fail("missing", Exception.class, () -> {
        expect(new DummyObject()).get("fieldX");
      });
      fail("private", ExpectError.class, () -> {
        expect(new DummyObject()).get("field2");
      });
    });

    pass("Getters", () -> {
      pass("public", () -> {
        String value = expect(new DummyObject()).get("field3", String.class);

        expect(value).toBe("3");
      });
      fail("with errors", ExpectError.class, () -> {
        expect(new DummyObject()).get("field4");
      });
    });

    pass("Negations", () -> {
      pass("expected missing keys", () -> {
        expect(new DummyObject()).not.get("??");
      });
      fail("unexpected missing keys", Exception.class, () -> {
        expect(new DummyObject()).not.get("field1");
      });
    });

    pass("Other errors", () -> {
      fail("Null instance", ExpectError.class, () -> {
        expect(null).get("field1");
      });
      fail("Null key", ExpectError.class, () -> {
        expect(new DummyObject()).get(null);
      });
    });
  }
}
