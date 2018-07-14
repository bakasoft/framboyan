package org.bakasoft.framboyan.diff;

import java.math.BigDecimal;

import org.bakasoft.framboyan.util.Toolbox;

public class CloseToDiff extends AbstractDiff {

	private final BigDecimal actual;
	private final BigDecimal expected;
	private final BigDecimal delta;
	
	public CloseToDiff(Object actual, Object expected, Object delta) {
		this.actual = Toolbox.number(actual);
		this.expected = Toolbox.number(expected);
		this.delta = Toolbox.number(delta);
	}

	private BigDecimal getActualDelta() {
		return expected
				.subtract(actual)
				.abs();
	}
	
	@Override
	public boolean test() {
		return getActualDelta().compareTo(delta) <= 0;
	}

	@Override
	public String generateExpectMessage() {
		return String.format("Expected %s to be close to %s with a delta of %s instead of %s.", // TODO: you can do it better!
				actual, expected, delta, getActualDelta());
	}

	@Override
	public String generateNotExpectMessage() {
		return String.format("Expected %s NOT to be close to %s with a delta of %s instead of %s.", // TODO: you can do it better!
				actual, expected, delta, getActualDelta());
	}

	@Override
	public String generateActualValue() {
		return "{ value: " + actual + ", delta: " + getActualDelta() + " }";
	}

	@Override
	public String generateExpectedValue() {
		return "{ value:" + expected + ", delta: " + delta + " }";
	}

}
