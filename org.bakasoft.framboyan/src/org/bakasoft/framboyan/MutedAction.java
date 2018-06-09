package org.bakasoft.framboyan;

import java.io.PrintStream;

@FunctionalInterface
public interface MutedAction extends Action {
	
	@Override
	default void run(PrintStream out) throws Throwable {
		run();
	}
	
	void run() throws Throwable;
	
}
