package org.bakasoft.framboyan.diff;

import java.util.List;

import org.bakasoft.framboyan.util.JSON;
import org.bakasoft.framboyan.util.Normalizer;
import org.bakasoft.framboyan.util.Inspector;

public class EmptyDiff implements Diff {

	private final Object value;
	
	public EmptyDiff(Object value) {
		this.value = Normalizer.normalize(value);
	}

	@Override
	public boolean test() {
		if (value instanceof String) {
			return ((String)value).isEmpty();
		}
		else if (value instanceof List) {
			return ((List<?>)value).isEmpty();
		}
		
		return value == null;
	}

	@Override
	public String generateExpectMessage() {
		return String.format("Expected %s to be empty.", 
				Inspector.inspect(value));
	}

	@Override
	public String generateNotExpectMessage() {
		return String.format("Expected %s NOT to be empty.", 
				Inspector.inspect(value));
	}

	@Override
	public String generateActualValue() {
		return JSON.stringify(value);
	}

	@Override
	public String generateExpectedValue() {
		if (value instanceof String) {
			return JSON.stringify("");
		}
		else if (value instanceof List) {
			return JSON.stringify(new Object[0]); 
		}
		
		return JSON.stringify(null);
	}

}
