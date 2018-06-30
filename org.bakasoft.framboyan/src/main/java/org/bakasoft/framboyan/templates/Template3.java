package org.bakasoft.framboyan.templates;

public class Template3<T1, T2, T3> {
	
	private final Template3Action<T1, T2, T3> action;

	public Template3(Template3Action<T1, T2, T3> action) {
		this.action = action;
	}
	
	public Template3<T1, T2, T3> test(T1 arg1, T2 arg2, T3 arg3) {
		action.run(arg1, arg2, arg3);
		return this;
	}
}
