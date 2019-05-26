package org.bakasoft.framboyan.test;

import org.bakasoft.framboyan.test.annotations.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TestResult {

  private List<TestResult> results;

  private final String description;

  private Boolean pass;
  private String errorMessage;
  private Throwable errorCause;

  private TestSummary summary;
  private boolean focused;

  public TestResult(Class<?> type) {
    Test t = type.getAnnotation(Test.class);

    if (t != null && !t.value().isEmpty()) {
      this.description = t.value();
    }
    else {
      this.description = type.getSimpleName().replace('_', ' ');
    }
  }

  public TestResult(String description) {
    if (description == null) {
      throw new NullPointerException();
    }

    this.description = description;
  }

  public void pass() {
    this.pass = true;
    this.errorMessage = null;
    this.errorCause = null;
  }

  public void fail(Throwable e) {
    this.pass = false;
    this.errorMessage = null;
    this.errorCause = e;
  }

  public void fail(String message) {
    this.pass = false;
    this.errorMessage = message;
    this.errorCause = null;
  }

  public void addTest(TestResult result) {
    if (results == null) {
      results = new ArrayList<>();
    }

    results.add(result);
  }

  public List<TestResult> getResults() {
    return results != null ? results : Collections.emptyList();
  }

  public String getDescription() {
    return description;
  }

  public Boolean getPass() {
    return pass;
  }

  public Throwable getErrorCause() {
    return errorCause;
  }

  public String getErrorMessage() {
    if (errorMessage != null) {
      return errorMessage;
    }
    else if (errorCause != null) {
      return errorCause.getMessage();
    }

    return null;
  }

  public TestSummary getSummary() {
    if (summary == null) {
      int passed = 0;
      int unknown = 0;
      int failed = 0;

      if (results != null && results.size() > 0) {
        for (TestResult child : results) {
          TestSummary summary = child.getSummary();

          passed += summary.getPassedCount();
          unknown += summary.getUnknownCount();
          failed += summary.getFailedCount();
        }
      }
      else {
        if (pass == null) {
          unknown++;
        }
        else if (pass) {
          passed++;
        }
        else {
          failed++;
        }
      }

      summary = new TestSummary(passed, unknown, failed);
    }

    return summary;
  }

  public void setFocused(boolean focused) {
    this.focused = focused;
  }

  public boolean isFocused() {
    return focused || results != null && results.stream().anyMatch(TestResult::isFocused);
  }

}
