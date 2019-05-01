package org.bakasoft.framboyan.test;

import org.bakasoft.framboyan.expect.Expect;

import java.util.Stack;
import java.util.function.Consumer;

abstract public class FramboyanTest implements Testable {

  private final char MODE_NORMAL = '_';
  private final char MODE_IGNORE = 'x';
  private final char MODE_FOCUS = 'f';

  private final TestResult root;
  private final Stack<TestResult> results;

  private int count;

  public FramboyanTest() {
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

  private void process(String description, char mode, Consumer<TestResult> consumer) {
    TestResult parent;

    if (results.empty()) {
      parent = root;
    }
    else {
      parent = results.peek();
    }

    TestResult child = new TestResult(description);

    parent.addTest(child);

    if (mode != MODE_IGNORE) {
      results.push(child);

      try {
        consumer.accept(child);
      } finally {
        results.pop();
        count++;
      }
    }

    if (mode == MODE_FOCUS) {
      child.setFocused(true);
    }
  }

  private void process_pass(String description, char mode, TestAction action) {
    process(description != null ? description : "Test #" + (count+1), mode, child -> {
      try {
        action.run();

        child.pass();
      }
      catch (AssertionError | Exception e) {
        child.fail(e);
      }
    });
  }

  private void process_fail(String description, char mode, Class<?> errorType, TestAction action) {
    // TODO improve message
    process(description != null ? description : "Should throw " + errorType.getSimpleName(), mode, child -> {
      try {
        action.run();

        // TODO improve message
        child.fail("Expected an error: " + errorType);
      }
      catch (AssertionError e) {
        child.fail(e);
      }
      catch (Exception e) {
        if (errorType.isInstance(e)) {
          child.pass();
        }
        else {
          // TODO improve message
          child.fail("Expected an error of the type: " + errorType);
        }
      }
    });
  }

  protected void fpass(TestAction action) {
    process_pass(null, MODE_FOCUS, action);
  }

  protected void fpass(String description, TestAction action) {
    process_pass(description, MODE_FOCUS, action);
  }

  protected void ffail(Class<? extends Throwable> errorType, TestAction action) {
    process_fail(null, MODE_FOCUS, errorType, action);
  }

  protected void ffail(String description, Class<? extends Throwable> errorType, TestAction action) {
    process_fail(description, MODE_FOCUS, errorType, action);
  }

  protected void xpass(TestAction action) {
    process_pass(null, MODE_IGNORE, action);
  }

  protected void xpass(String description, TestAction action) {
    process_pass(description, MODE_IGNORE, action);
  }

  protected void xpass(String description) {
    process_pass(description, MODE_IGNORE, () -> {});
  }

  protected void xfail(Class<? extends Throwable> errorType, TestAction action) {
    process_fail(null, MODE_IGNORE, errorType, action);
  }

  protected void xfail(String description, Class<? extends Throwable> errorType, TestAction action) {
    process_fail(description, MODE_IGNORE, errorType, action);
  }

  protected void pass(TestAction action) {
    process_pass(null, MODE_NORMAL, action);
  }

  protected void pass(String description, TestAction action) {
    process_pass(description, MODE_NORMAL, action);
  }

  protected void fail(Class<? extends Throwable> errorType, TestAction action) {
    process_fail(null, MODE_NORMAL, errorType, action);
  }

  protected void fail(String description, Class<? extends Throwable> errorType, TestAction action) {
    process_fail(description, MODE_NORMAL, errorType, action);
  }

}
