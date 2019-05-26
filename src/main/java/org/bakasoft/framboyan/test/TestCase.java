package org.bakasoft.framboyan.test;

import org.bakasoft.framboyan.expect.Expect;

import java.util.Stack;
import java.util.function.Consumer;

abstract public class TestCase implements Testable {

  private static int count;

  private final TestResult root;
  private final Stack<TestResult> results;

  public TestCase() {
    root = new TestResult(getClass());
    results = new Stack<>();
  }

  protected Expect expect(Object value) {
    return new Expect(value);
  }

  @Override
  public TestResult getResult() {
    return root;
  }

  private void process(String description, boolean run, Consumer<TestResult> consumer) {
    TestResult parent;

    if (results.empty()) {
      parent = root;
    }
    else {
      parent = results.peek();
    }

    TestResult child = new TestResult(description);

    parent.addTest(child);

    if (run) {
      results.push(child);

      try {
        consumer.accept(child);
      } finally {
        results.pop();
        count++;
      }
    }
  }

  private void process_pass(String description, boolean run, TestAction action) {
    process(description != null ? description : "Test #" + (count+1), run, child -> {
      try {
        action.run();

        child.pass();
      }
      catch (AssertionError | Exception e) {
        child.fail(e);
      }
    });
  }

  private void process_fail(String description, boolean run, Class<?> errorType, TestAction action) {
    // TODO improve message
    process(description != null ? description : "Test #" + (count+1), run, child -> {
      try {
        action.run();

        // TODO improve message
        child.fail("Expected an error: " + errorType);
      }
      catch (AssertionError | Exception e) {
        if (errorType.isInstance(e)) {
          child.pass();
        }
        else if (e instanceof AssertionError) {
          child.fail(e);
        }
        else {
          // TODO improve message
          child.fail("Expected an error of the type " + errorType + " instead of " + e.getClass());
        }
      }
    });
  }

  protected void fail(String message) {
    throw new AssertionError(message);
  }

  protected void xpass(TestAction action) {
    process_pass(null, false, action);
  }

  protected void xpass(String description, TestAction action) {
    process_pass(description, false, action);
  }

  protected void xpass(String description) {
    process_pass(description, false, () -> {});
  }

  protected void xfail(Class<? extends Throwable> errorType, TestAction action) {
    process_fail(null, false, errorType, action);
  }

  protected void xfail(String description, Class<? extends Throwable> errorType, TestAction action) {
    process_fail(description, false, errorType, action);
  }

  protected void pass(TestAction action) {
    process_pass(null, true, action);
  }

  protected void pass(String description, TestAction action) {
    process_pass(description, true, action);
  }

  protected void fail(String description, TestAction action) {
    process_fail(description, true, Exception.class, action);
  }

  protected void fail(Class<? extends Throwable> errorType, TestAction action) {
    process_fail(null, true, errorType, action);
  }

  protected void fail(String description, Class<? extends Throwable> errorType, TestAction action) {
    process_fail(description, true, errorType, action);
  }

}
