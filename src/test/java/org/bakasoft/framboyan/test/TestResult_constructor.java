package org.bakasoft.framboyan.test;

public class TestResult_constructor extends TestCase {{
  pass("should generate description from class argument", () -> {
    class CustomTest_1 {}

    TestResult result = new TestResult(CustomTest_1.class);

    expect(result.getDescription()).toEqual("CustomTest 1");
  });
  pass(() -> {
    class CustomTest_1 {}

    TestResult result = new TestResult(CustomTest_1.class);

    expect(result.getDescription()).toEqual("CustomTest 1");
  });
}}
