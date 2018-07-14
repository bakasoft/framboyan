package org.bakasoft.framboyan.diff;

import java.util.regex.Pattern;

import org.bakasoft.framboyan.util.Toolbox;

public class MatchDiff extends AbstractDiff {

	private final String text;
	private final Pattern pattern;
	
	public MatchDiff(Object actual, Object pattern) {
		this.text = Toolbox.string(actual);
		this.pattern = Toolbox.pattern(pattern);
	}

	@Override
	public boolean test() {
		return pattern.matcher(text).matches();
	}

	@Override
	public String generateExpectMessage() {
		return String.format("Expected %s to match %s.", 
				Toolbox.inspect(text),
				Toolbox.inspect(pattern));
	}

	@Override
	public String generateNotExpectMessage() {
		return String.format("Expected %s to match %s.", 
				Toolbox.inspect(text),
				Toolbox.inspect(pattern));
	}

	@Override
	public String generateActualValue() {
		return null;
	}

	@Override
	public String generateExpectedValue() {
		return null;
	}
	
	// TODO add difference message

}
