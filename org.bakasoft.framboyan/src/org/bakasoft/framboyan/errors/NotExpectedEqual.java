package org.bakasoft.framboyan.errors;

import org.bakasoft.framboyan.Debugger;

public class NotExpectedEqual extends AssertionError {

	private static final long serialVersionUID = 7431741681180355254L;

	public NotExpectedEqual(Object actual, Object expected) {
		super(String.format("%s not expected to be %s.", 
				Debugger.inspect(actual),
				Debugger.inspect(expected)));
	}
}
