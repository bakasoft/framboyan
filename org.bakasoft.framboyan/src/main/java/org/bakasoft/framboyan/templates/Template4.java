package org.bakasoft.framboyan.templates;

public class Template4<T1, T2, T3, T4> {
	
	private final Template4Action<T1, T2, T3, T4> action;

	public Template4(Template4Action<T1, T2, T3, T4> action) {
		this.action = action;
	}
	
	public Template4<T1, T2, T3, T4> test(T1 arg1, T2 arg2, T3 arg3, T4 arg4) {
		action.run(arg1, arg2, arg3, arg4);
		return this;
	}
}
