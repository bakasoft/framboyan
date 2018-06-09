package org.bakasoft.framboyan;

import java.io.PrintStream;

@FunctionalInterface
public interface Action {
	
	void run(PrintStream out) throws Throwable;
	
}
