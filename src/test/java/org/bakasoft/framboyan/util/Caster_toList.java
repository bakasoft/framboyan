package org.bakasoft.framboyan.util;

import org.bakasoft.framboyan.test.FramboyanTest;

import java.util.*;
import java.util.stream.Stream;

public class Caster_toList extends FramboyanTest {{

  pass("Instances of List shouldn't be touched", () -> {
    ArrayList<String> list1 = new ArrayList<>();
    Vector<Number> list2 = new Vector<>();
    Stack<Exception> list3 = new Stack<>();

    expect(Caster.toList(list1)).toBe(list1);
    expect(Caster.toList(list2)).toBe(list2);
    expect(Caster.toList(list3)).toBe(list3);
  });

  pass("Null values shouldn't be touched", () -> {
    expect(Caster.toList(null)).toBeNull();
  });

  pass("Iterable objects should be converted to List", () -> {
    pass("Set", () -> {
      LinkedHashSet<String> s = new LinkedHashSet<>(Arrays.asList("A", "B", "C"));

      expect(Caster.toList(s)).toIterate("A", "B", "C");
    });
    pass("Collection", () -> {
      Collection<String> c = Collections.unmodifiableCollection(Arrays.asList("x", "y", "z"));

      expect(Caster.toList(c)).toIterate("x", "y", "z");
    });
  });

  pass("Stream objects should be converted to List", () -> {
    pass("BaseStream", () -> {
      expect(Caster.toList(Arrays.stream(new int[] {1, 2, 3}))).toIterate(1, 2, 3);
      expect(Caster.toList(Arrays.stream(new double[] {10.5, 20.5}))).toIterate(10.5, 20.5);
    });
    pass("Stream", () -> {
      expect(Caster.toList(Stream.of("i", "j", "k"))).toIterate("i", "j", "k");
    });
  });

  pass("Enumeration objects should be converted to List", () -> {
    Enumeration<?> e = Collections.enumeration(Arrays.asList(true, null, false));

    expect(Caster.toList(e)).toIterate(true, null, false);
  });

  pass("Array objects should be converted to List", () -> {
    int[] a = { 100, 200, 300 };

    expect(Caster.toList(a)).toIterate(100, 200, 300);
  });

  pass("Other values should be null", () -> {
    expect(Caster.toList(1)).toBeNull();
    expect(Caster.toList(true)).toBeNull();
    expect(Caster.toList(new Object())).toBeNull();
    expect(Caster.toList(new Exception())).toBeNull();
  });

}}
