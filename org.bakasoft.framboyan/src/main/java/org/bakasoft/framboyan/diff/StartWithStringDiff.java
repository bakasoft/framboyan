package org.bakasoft.framboyan.diff;

import org.bakasoft.framboyan.util.Inspector;
import org.bakasoft.framboyan.util.Strings;

public class StartWithStringDiff implements Diff {

	private final String text;
	private final String suffix;

	public StartWithStringDiff(String text, String suffix) {
		this.text = text;
		this.suffix = suffix;
	}

	@Override
	public boolean test() {
		return text.endsWith(suffix);
	}

	@Override
	public String generateExpectMessage() {
		return String.format("Expected %s to end with %s.", 
				Inspector.inspect(text),
				Inspector.inspect(suffix));
	}

	@Override
	public String generateNotExpectMessage() {
		return String.format("Expected %s NOT to end with %s.", 
				Inspector.inspect(text),
				Inspector.inspect(suffix));
	}

	@Override
	public String generateActualValue() {
		String actualSuffix = Strings.lastPart(text, suffix.length());
		
		if (actualSuffix.length() < suffix.length()) {
			return Strings.repeat(' ', suffix.length() - actualSuffix.length()) + suffix;
		}
		
		return suffix;
	}

	@Override
	public String generateExpectedValue() {
		return suffix;
	}

}
