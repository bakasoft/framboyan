package org.bakasoft.framboyan.templates;

public class Template5<T1, T2, T3, T4, T5> {
	
	private final Template5Action<T1, T2, T3, T4, T5> action;

	public Template5(Template5Action<T1, T2, T3, T4, T5> action) {
		this.action = action;
	}
	
	public Template5<T1, T2, T3, T4, T5> test(T1 arg1, T2 arg2, T3 arg3, T4 arg4, T5 arg5) {
		action.run(arg1, arg2, arg3, arg4, arg5);
		return this;
	}
}
