package org.bakasoft.framboyan.test;

public class TestSummary {

  private final int passed;
  private final int unknown;
  private final int failed;

  public TestSummary(int passed, int unknown, int failed) {
    this.passed = passed;
    this.unknown = unknown;
    this.failed = failed;
  }

  public int getPassedCount() {
    return passed;
  }

  public int getUnknownCount() {
    return unknown;
  }

  public int getFailedCount() {
    return failed;
  }

}
