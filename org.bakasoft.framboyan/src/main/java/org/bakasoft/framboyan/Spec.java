package org.bakasoft.framboyan;

import java.util.Collections;
import java.util.List;

public class Spec implements Target {

	private final Object subject;
	private final Action action;
	private final Mode mode;
	
	public Spec(Object subject, Action action, Mode mode) {
		this.subject = subject;
		this.action = action;
		this.mode = mode;
	}
	
	@Override
	public Object getDescription() {
		return subject;
	}
	
	@Override
	public boolean isPending() {
		return action == null || mode == Mode.PENDING;
	}

	@Override
	public boolean isFocused() {
		return mode == Mode.FOCUSED;
	}
	
	@Override
	public boolean isRoot() {
		// only subclasses are allowed to be root
		return getClass() != Spec.class;
	}

	@Override
	public List<Target> getTargets() {
		return Collections.emptyList();
	}

	public Action getAction() {
		return action;
	}
	
	@Override
	public String toString() {
		return "Spec: " + subject;
	}

	@Override
	public Node buildInto(Node parent) {
		return new Node(parent, this, subject, action, isPending(), isFocused(), null);
	}

}
