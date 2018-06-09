package org.bakasoft.framboyan.errors;

import org.bakasoft.framboyan.Debugger;

public class NotExpectedNull extends AssertionError {

	private static final long serialVersionUID = -4056471393584563717L;

	public NotExpectedNull(Object actual) {
		super(String.format("%s not expected to be null.", 
				Debugger.inspect(actual)));
	}
	
}
