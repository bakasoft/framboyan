package org.bakasoft.framboyan;

import java.util.ArrayList;
import java.util.List;

public class Group implements Target {
	
	private final Object subject;
	private final Mode mode;
	private final ArrayList<Target> targets;
	
	public Group(Object subject, Mode mode) {
		this.subject = subject;
		this.mode = mode;
		this.targets = new ArrayList<>();
	}

	public List<Target> getTargets() {
		return targets;
	}
	
	public void addTarget(Target target) {
		targets.add(target);
	}
	
	@Override
	public Object getDescription() {
		return subject;
	}

	@Override
	public boolean isPending() {
		return targets.isEmpty() || mode == Mode.PENDING;
	}

	@Override
	public boolean isFocused() {
		return mode == Mode.FOCUSED;
	}
	
	@Override
	public boolean isRoot() {
		// only subclasses are allowed to be root
		return getClass() != Group.class;
	}
	
	@Override
	public String toString() {
		return "Group: " + subject;
	}

	@Override
	public Node buildInto(Node parent) {
		Node node = new Node(parent, this, subject, null, isPending(), isFocused(), null);
		
		for (Target target : targets) {
			Node subnode = target.buildInto(node);
			
			node.addNode(subnode);
		}
		
		return node;
	}

}
