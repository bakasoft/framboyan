package org.bakasoft.framboyan.diff;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bakasoft.framboyan.util.Inspector;
import org.bakasoft.framboyan.util.Normalizer;

public class MatchDiff implements Diff {

	private final String text;
	private final Pattern pattern;
	
	public MatchDiff(Object actual, Object pattern) {
		this.text = Normalizer.toString(actual);
		this.pattern = Normalizer.toPattern(pattern);
	}

	@Override
	public boolean test() {
		return pattern.matcher(text).matches();
	}

	@Override
	public String generateExpectMessage() {
		return String.format("Expected %s to match %s.", 
				Inspector.inspect(text),
				Inspector.inspect(pattern));
	}

	@Override
	public String generateNotExpectMessage() {
		return String.format("Expected %s to match %s.", 
				Inspector.inspect(text),
				Inspector.inspect(pattern));
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
