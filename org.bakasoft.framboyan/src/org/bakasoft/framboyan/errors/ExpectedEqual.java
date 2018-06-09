package org.bakasoft.framboyan.errors;

import org.bakasoft.framboyan.Debugger;

public class ExpectedEqual extends AssertionError {

	private static final long serialVersionUID = 3352553614382530916L;

	public ExpectedEqual(Object actual, Object expected) {
		super(String.format("%s expected to be %s.", 
				Debugger.inspect(actual),
				Debugger.inspect(expected)));
	}
	
}
