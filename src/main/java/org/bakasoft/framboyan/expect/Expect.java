package org.bakasoft.framboyan.expect;

public class Expect {

  private final Object actual;
  private final boolean positive;

  public final Expect not;

  public Expect(Object actual) {
    this(actual, null);
  }

  private Expect(Object actual, Expect not) {
    this.actual = actual;
    if (not != null) {
      this.not = not;
      this.positive = false;
    }
    else {
      this.not = new Expect(actual, this);
      this.positive = true;
    }
  }

  public void toBe(Object expected) {
    if (positive) {
      ExpectToBe.pass(actual, expected);
    }
    else {
      ExpectToBe.fail(actual, expected);
    }
  }

  public void toEqual(Object expected) {
    if (positive) {
      ExpectToEqual.pass(actual, expected);
    }
    else {
      ExpectToEqual.fail(actual, expected);
    }
  }

  public void toBeLike(Object expected) {
    if (positive) {
      ExpectToBeLike.pass(actual, expected);
    }
    else {
      ExpectToBeLike.fail(actual, expected);
    }
  }

  public void toBeEmpty() {
    if (positive) {
      ExpectToBeEmpty.pass(actual);
    }
    else {
      ExpectToBeEmpty.fail(actual);
    }
  }

  public void toBeTrue() {
    if (positive) {
      ExpectToBeBoolean.shouldBeTrue(actual);
    }
    else {
      ExpectToBeBoolean.shouldBeFalse(actual);
    }
  }

  public void toBeFalse() {
    if (positive) {
      ExpectToBeBoolean.shouldBeFalse(actual);
    }
    else {
      ExpectToBeBoolean.shouldBeTrue(actual);
    }
  }

  public void toBeNull() {
    if (positive) {
      ExpectToBeNull.pass(actual);
    }
    else {
      ExpectToBeNull.fail(actual);
    }
  }

  public void toStartWith(Object... items) {
    if (positive) {
      ExpectToStartWith.pass(actual, items);
    }
    else {
      ExpectToStartWith.fail(actual, items);
    }
  }

  public void toEndWith(Object... items) {
    if (positive) {
      ExpectToEndWith.pass(actual, items);
    }
    else {
      ExpectToEndWith.fail(actual, items);
    }
  }

  public void toMatch(Object pattern) {
    if (positive) {
      ExpectToMatch.pass(actual, pattern);
    }
    else {
      ExpectToMatch.fail(actual, pattern);
    }
  }

  public void toContain(Object... expected) {
    if (positive) {
      ExpectToContain.pass(actual, expected);
    }
    else  {
      ExpectToContain.fail(actual, expected);
    }
  }

  public void toIterate(Object... expected) {
    if (positive) {
      ExpectToIterate.pass(actual, expected);
    }
    else  {
      ExpectToIterate.fail(actual, expected);
    }
  }

}
