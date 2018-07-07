package org.bakasoft.framboyan;

import java.util.Stack;

import org.bakasoft.framboyan.expects.PositiveExpect;
import org.bakasoft.framboyan.templates.Template1;
import org.bakasoft.framboyan.templates.Template1Action;
import org.bakasoft.framboyan.templates.Template2;
import org.bakasoft.framboyan.templates.Template2Action;
import org.bakasoft.framboyan.templates.Template3;
import org.bakasoft.framboyan.templates.Template3Action;
import org.bakasoft.framboyan.templates.Template4;
import org.bakasoft.framboyan.templates.Template4Action;
import org.bakasoft.framboyan.templates.Template5;
import org.bakasoft.framboyan.templates.Template5Action;

public class Suite {

	protected final Console console;
	
	private final Stack<Group> stack;
	
	public Suite(Console console, Group root) {
		this.console = console;
		this.stack = new Stack<>();
		this.stack.push(root);
	}
	
	public void describe(Object subject, Runnable content, boolean pending) {
		Group parent = stack.peek();
		
		Group group = new Group(parent, subject, console, pending);

		if (content != null) {
			stack.push(group);
			
			content.run();
			
			stack.pop();
		}
	}
	
	public void describe(Object subject, Runnable content) {
		describe(subject, content, false);
	}
	
	public void xdescribe(Object subject, Runnable content) {
		describe(subject, content, true);
	}
	
	public void xdescribe(Object subject) {
		describe(subject, null, true);
	}
	
	public void it(String name, Action action) {
		Group parent = stack.peek();
		
		new Spec(parent, name, action);
	}
	
	public void xit(String name) {
		it(name, null);
	}
	
	public void xit(String name, Action action) {
		it(name, null);
	}
	
	// expect functions
	
	public PositiveExpect expect(Action action) {
		return new PositiveExpect(action);
	}
	
	public PositiveExpect expect(Object value) {
		return new PositiveExpect(value);
	}
	
	// Templates 1, 2, 3, 4 and 5
	
	public <T1> Template1<T1> template(Template1Action<T1> action) {
		return new Template1<>(action);
	}
	
	public <T1, T2> Template2<T1, T2> template(Template2Action<T1, T2> action) {
		return new Template2<>(action);
	}
	
	public <T1, T2, T3> Template3<T1, T2, T3> template(Template3Action<T1, T2, T3> action) {
		return new Template3<>(action);
	}
	
	public <T1, T2, T3, T4> Template4<T1, T2, T3, T4> template(Template4Action<T1, T2, T3, T4> action) {
		return new Template4<>(action);
	}
	
	public <T1, T2, T3, T4, T5> Template5<T1, T2, T3, T4, T5> template(Template5Action<T1, T2, T3, T4, T5> action) {
		return new Template5<>(action);
	}
	
	// helper functions

	public void fail() {
		throw new AssertionError();
	}
	
}
