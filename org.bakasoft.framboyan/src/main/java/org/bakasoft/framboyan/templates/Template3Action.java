package org.bakasoft.framboyan.templates;

@FunctionalInterface
public interface Template3Action<T1, T2, T3> {

	void run(T1 arg1, T2 arg2, T3 arg3);
	
}
