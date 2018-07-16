package org.bakasoft.framboyan.diff;

import java.util.Objects;

import org.bakasoft.framboyan.util.Inspector;

public class InstanceOfDiff implements Diff {

	private final Object value;
	
	private final Class<?> type;
	
	public InstanceOfDiff(Object value, Class<?> type) {
		Objects.requireNonNull(type);
		this.value = value;
		this.type = type;
	}
	
	@Override
	public boolean test() {
		if (value != null) {
			return type.isInstance(value);
		}
		
		return false;
	}

	@Override
	public String generateExpectMessage() {
		return String.format("Expected %s to be an instance of %s.", 
				Inspector.inspect(value),
				type);
	}

	@Override
	public String generateNotExpectMessage() {
		return String.format("Expected %s NOT to be an instance of %s.", 
				Inspector.inspect(value),
				type);
	}

	@Override
	public String generateActualValue() {
		return value != null ? value.getClass().toString() : "null";
	}

	@Override
	public String generateExpectedValue() {
		return type.toString();
	}

}
