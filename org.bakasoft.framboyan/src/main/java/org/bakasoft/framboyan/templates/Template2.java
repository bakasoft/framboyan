package org.bakasoft.framboyan.templates;

public class Template2<T1, T2> {
	
	private final Template2Action<T1, T2> action;

	public Template2(Template2Action<T1, T2> action) {
		this.action = action;
	}
	
	public Template2<T1, T2> test(T1 arg1, T2 arg2) {
		action.run(arg1, arg2);
		return this;
	}
}
