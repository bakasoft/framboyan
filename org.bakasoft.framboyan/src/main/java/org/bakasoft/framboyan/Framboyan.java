package org.bakasoft.framboyan;

import org.bakasoft.framboyan.console.Console;

abstract public class Framboyan extends Suite {
	
	public Framboyan() {
		super(new Console());
	}
	
}
