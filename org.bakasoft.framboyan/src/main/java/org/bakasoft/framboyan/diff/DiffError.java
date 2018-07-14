package org.bakasoft.framboyan.diff;

public class DiffError extends AssertionError {

	private static final long serialVersionUID = 2971157415588186878L;

	private String difference;
	
	public DiffError(String shortMessage, String difference) {
		super(shortMessage);
		this.difference = difference;
	}
	
	public String getDifference() {
		return difference;
	}
	
}
