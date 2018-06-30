package org.bakasoft.framboyan.templates;

public class Template1<T1> {
	
	private final Template1Action<T1> action;

	public Template1(Template1Action<T1> action) {
		this.action = action;
	}
	
	public Template1<T1> test(T1 arg1) {
		action.run(arg1);
		return this;
	}
}
