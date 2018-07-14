package org.bakasoft.framboyan.diff;

import org.bakasoft.framboyan.util.JSON;
import org.bakasoft.framboyan.util.Inspector;

abstract public class CompareGenericDiff<T> extends AbstractDiff {

	abstract protected int compare();
	
	protected final T left;
	protected final T right;

	private  final String sign;
	
	public CompareGenericDiff(T left, T right, String sign) {
		this.left = left;
		this.right = right;
		this.sign = sign;
	}
	
	@Override
	public boolean test() {
		int diff = compare();
		
		switch(sign) {
		case "<":
			return diff < 0;
		case ">":
			return diff > 0;
		default:
			throw new RuntimeException();
		}
	}

	@Override
	public String generateExpectMessage() {
		switch(sign) {
		case "<":
			return String.format("", 
					Inspector.inspect(left), 
					Inspector.inspect(right));
		case ">":
			return String.format("", 
					Inspector.inspect(left), 
					Inspector.inspect(right));
		default:
			throw new RuntimeException();
		}
	}

	@Override
	public String generateNotExpectMessage() {
		switch(sign) {
		case "<":
			return String.format("", 
					Inspector.inspect(left), 
					Inspector.inspect(right));
		case ">":
			return String.format("", 
					Inspector.inspect(left), 
					Inspector.inspect(right));
		default:
			throw new RuntimeException();
		}
	}

	@Override
	public String generateActualValue() {
		return JSON.stringify(left);
	}

	@Override
	public String generateExpectedValue() {
		return JSON.stringify(right);
	}
	
	// TODO add diff message
	
}
