package org.bakasoft.framboyan.diff;

import org.bakasoft.framboyan.util.Toolbox;

public class StartWithStringDiff extends AbstractDiff {

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
				Toolbox.inspect(text),
				Toolbox.inspect(suffix));
	}

	@Override
	public String generateNotExpectMessage() {
		return String.format("Expected %s NOT to end with %s.", 
				Toolbox.inspect(text),
				Toolbox.inspect(suffix));
	}

	@Override
	public String generateActualValue() {
		String actualSuffix = Toolbox.lastPart(text, suffix.length());
		
		if (actualSuffix.length() < suffix.length()) {
			return Toolbox.repeat(' ', suffix.length() - actualSuffix.length()) + suffix;
		}
		
		return suffix;
	}

	@Override
	public String generateExpectedValue() {
		return suffix;
	}

}
