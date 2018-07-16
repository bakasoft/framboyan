package org.bakasoft.framboyan.templates;

@FunctionalInterface
public interface Template2Action<T1, T2> {

	void run(T1 arg1, T2 arg2);

}
