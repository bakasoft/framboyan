package org.bakasoft.framboyan;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import org.bakasoft.framboyan.expect.Expect;
import org.bakasoft.framboyan.templaters.Templater2x2;
import org.bakasoft.framboyan.templaters.Templater2x2Builder;
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

public class Suite implements Target {

	protected final Console console;
	
	private final ArrayList<Target> _targets;
	
	private final Stack<Group> _groupStack;
	
	public Suite(Console console) {
		this.console = console;
		this._groupStack = new Stack<>();
		this._targets = new ArrayList<>();
	}
	
	public Console getConsole() {
		return console;
	}
	
	// target methods
	
	@Override
	public Object getDescription() {
		return Target.getDescriptionFromClass(getClass(), null);
	}

	@Override
	public boolean isPending() {
		return _targets.isEmpty() || Target.getPendingFromClass(getClass(), false);
	}

	@Override
	public boolean isFocused() {
		return Target.getFocusedFromClass(getClass(), false);
	}
	
	@Override
	public boolean isRoot() {
		// only subclasses are allowed to be root
		return getClass() != Suite.class;
	}
	
	@Override
	public List<Target> getTargets() {
		return _targets;
	}
	
	@Override
	public Node buildInto(Node parent) {
		Node node = new Node(parent, this, getDescription(), null, isPending(), isFocused(), console);
		
		for (Target target : getTargets()) {
			Node subnode = target.buildInto(node);
			
			node.addNode(subnode);
		}
		
		return node;
	}
	
	/* framework */
	
	public Group describe(Object description, Runnable content, Mode mode) {
		Group parent = _groupStack.isEmpty() ? null : _groupStack.peek();
		
		Group group = new Group(description, mode);

		if (content != null) {
			_groupStack.push(group);
			
			content.run();
			
			_groupStack.pop();
		}
		
		if (parent != null) {
			parent.addTarget(group);
		}
		else {
			_targets.add(group);
		}
		
		return group;
	}
	
	public Group describe(Object description, Runnable content) {
		return describe(description, content, Mode.NORMAL);
	}
	
	public Group fdescribe(Object description, Runnable content) {
		return describe(description, content, Mode.FOCUSED);
	}
	
	public Group xdescribe(Object description, Runnable content) {
		return describe(description, content, Mode.PENDING);
	}
	
	public Group xdescribe(Object description) {
		return describe(description, null, Mode.PENDING);
	}
	
	public Spec it(Object description, Action action, Mode mode) {
		Group parent = _groupStack.isEmpty() ? null : _groupStack.peek();
		
		Spec spec = new Spec(description, action, mode);
		
		if (parent != null) {
			parent.addTarget(spec);
		}
		else {
			_targets.add(spec);
		}
		
		return spec;
	}
	
	public Spec it(Object description, Action action) {
		return it(description, action, Mode.NORMAL);
	}
	
	public Spec fit(Object description, Action action) {
		return it(description, action, Mode.FOCUSED);
	}
	
	public Spec xit(Object description) {
		return it(description, null, Mode.PENDING);
	}
	
	public Spec xit(Object description, Action action) {
		return it(description, null, Mode.PENDING);
	}
	
	// expect functions
	
	public Expect expect(Action action) {
		return new Expect(action);
	}
	
	public Expect expect(Object value) {
		return new Expect(value);
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
	
	// Templaters
	
	public <T1, T2, U1, U2> Templater2x2<T1, T2, U1, U2> templater(Templater2x2Builder<T1, T2, U1, U2> builder) {
		return new Templater2x2<>(builder);
	}
	
	// helper functions

	public void fail() {
		throw new AssertionError();
	}
	
	// TODO: add fail with message
	
}
