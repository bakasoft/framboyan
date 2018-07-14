package org.bakasoft.framboyan.diff;

import java.util.function.Supplier;

abstract public class WrappedDiff extends AbstractDiff {

	private final AbstractDiff impl;
	
	public WrappedDiff(Supplier<AbstractDiff> impl) {
		this.impl = impl.get();
	}
	
	@Override
	public boolean test() {
		return impl.test();
	}

	@Override
	public String generateExpectMessage() {
		return impl.generateExpectMessage();
	}

	@Override
	public String generateNotExpectMessage() {
		return impl.generateNotExpectMessage();
	}

	@Override
	public String generateActualValue() {
		return impl.generateActualValue();
	}

	@Override
	public String generateExpectedValue() {
		return impl.generateExpectedValue();
	}

}