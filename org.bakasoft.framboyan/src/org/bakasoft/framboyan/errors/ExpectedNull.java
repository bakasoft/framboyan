package org.bakasoft.framboyan.errors;

import org.bakasoft.framboyan.Debugger;

public class ExpectedNull extends AssertionError {

	private static final long serialVersionUID = 6998449910906425255L;

	public ExpectedNull(Object actual) {
		super(String.format("%s expected to be null.", 
				Debugger.inspect(actual)));
	}
}
