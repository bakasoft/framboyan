package org.bakasoft.framboyan.test;

import org.bakasoft.framboyan.util.CodeWriter;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class StandardRunner {

  private static final String ALL_PASSED_EMOJI = "\u2728";
  private static final String PASSED_EMOJI = "\u2705";
  private static final String FAILED_EMOJI = "\u274C";
  private static final String UNKNOWN_EMOJI = "\u26D4";
  private static final String ALL_FAILED_EMOJI = "\u2620";

  private final CodeWriter writer;

  public StandardRunner(Appendable output) {
    writer = new CodeWriter(output);
  }

  public void run(String suiteName, List<Class<?>> testTypes) {
    TestResult suite = new TestResult(suiteName);

    for (Class<?> testType : testTypes) {
      try {
        Object testInstance = testType.getConstructor().newInstance();
        TestResult result;

        if (testInstance instanceof Testable) {
          result = ((Testable)testInstance).getResult();
        }
        else {
          result = new TestResult(testType);
        }

        result.pass();

        suite.addTest(result);
      }
      catch (Exception | AssertionError e) {
        TestResult result = new TestResult(testType);

        result.fail(e);

        suite.addTest(result);
      }
    }

    ArrayList<TestResult> errorDetails = new ArrayList<>();

    printResult(suite, errorDetails);

    for (TestResult test : errorDetails) {
      writer.breakLine();

      printError(test);
    }

    TestSummary summary = suite.getSummary();

    writer.breakLine();

    printSummary(summary);
  }

  public void printResult(TestResult result, List<TestResult> errorDetails) {
    String description = result.getDescription();
    TestSummary summary = result.getSummary();
    boolean focused = result.isFocused();

    if (result.getErrorCause() != null) {
      errorDetails.add(result);
    }

    writer.write(description);

    if (result.getErrorMessage() != null) {
      writer.write(": ");
      writer.write(result.getErrorMessage());
    }

    writer.write(' ');
    if (summary.getPassedCount() > 0 && summary.getUnknownCount() == 0 && summary.getFailedCount() == 0) {
      writer.write(PASSED_EMOJI);
    }
    else if (summary.getPassedCount() == 0 && summary.getUnknownCount() == 0 && summary.getFailedCount() > 0) {
      writer.write(FAILED_EMOJI);
    }
    else {
      writer.write(UNKNOWN_EMOJI);
    }

    writer.breakLine();

    writer.indent(+1);
    for (TestResult child : result.getResults()) {
      if (!focused || child.isFocused()) {
        printResult(child, errorDetails);
      }
    }
    writer.indent(-1);
  }

  public void printError(TestResult test) {
    Throwable error = test.getErrorCause();
    String message = test.getErrorMessage();

    writer.write(test.getDescription());
    writer.write(' ');
    writer.write(FAILED_EMOJI);
    writer.breakLine();

    writer.indent(+1);
    if (message != null && (error == null || !Objects.equals(message, error.getMessage()) && Objects.equals(message, error.getClass().getName()))) {
      writer.write(message);
      writer.breakLine();
    }

    if (error != null) {
      printError(error);
    }
    writer.indent(-1);
  }

  public void printError(Throwable e) {
    Throwable error;

    if (e instanceof InvocationTargetException) {
      error = ((InvocationTargetException)e).getTargetException();
    }
    else {
      error = e;
    }

    while (error != null) {
      String errorMessage = error.getMessage();
      String className = error.getClass().getName();

      if (Objects.equals(errorMessage, className)) {
        errorMessage = null;
      }

      if (errorMessage != null) {
        writer.write(error.getMessage());
        writer.breakLine();
        writer.indent(+1);
      }

      writer.write(className);
      writer.write(":");
      writer.breakLine();
      writer.indent(+1);
      for (StackTraceElement element : error.getStackTrace()) {
        writer.write(element.toString());
        writer.breakLine();
      }
      writer.indent(-1);

      if (errorMessage != null) {
        writer.indent(-1);
      }

      error = error.getCause();
    }
  }

  public void printSummary(TestSummary summary) {
    int passed = summary.getPassedCount();
    int unknown = summary.getUnknownCount();
    int failed = summary.getFailedCount();

    if (failed > 0) {
      if (passed == 0 && unknown == 0) {
        writer.write("All tests failed! " + ALL_FAILED_EMOJI);
        writer.breakLine();
      }
      else {
        writer.write("Some tests failed " + FAILED_EMOJI);
        writer.breakLine();
      }
    }
    else if (passed > 0 && failed == 0 && unknown == 0) {
      writer.write("All tests passed! " + ALL_PASSED_EMOJI);
      writer.breakLine();
    }
    else {
      writer.write("Tests are not conclusive " + UNKNOWN_EMOJI);
      writer.breakLine();
    }

    writer.indent(+1);
    writer.write(passed + " successful test(s)");
    writer.breakLine();
    writer.write(unknown + " pending test(s)");
    writer.breakLine();
    writer.write(failed + " failed test(s)");
    writer.breakLine();
    writer.indent(-1);
    writer.breakLine();
  }

}
