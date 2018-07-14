package org.bakasoft.framboyan.diff;

public class ContainStringDiff extends AbstractDiff {

	private final String text;
	private final String part;
	
	public ContainStringDiff(String text, String part) {
		this.text = text;
		this.part = part;
	}

	@Override
	public boolean test() {
		return text.contains(part);
	}

	@Override
	public String generateExpectMessage() {
		return String.format("Expected %s to contain %s.", text, part);
	}

	@Override
	public String generateNotExpectMessage() {
		return String.format("Expected %s to NOT contain %s.", text, part);
	}

	@Override
	public String generateActualValue() {
		return null;
	}

	@Override
	public String generateExpectedValue() {
		return null;
	}
	
	// TODO add detailed message

}
