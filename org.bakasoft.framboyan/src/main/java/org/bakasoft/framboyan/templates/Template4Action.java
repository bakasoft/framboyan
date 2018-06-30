package org.bakasoft.framboyan.templates;

@FunctionalInterface
public interface Template4Action<T1, T2, T3, T4> {

	void run(T1 arg1, T2 arg2, T3 arg3, T4 arg4);
	
}
