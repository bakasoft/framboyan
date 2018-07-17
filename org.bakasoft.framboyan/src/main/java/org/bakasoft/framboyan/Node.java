package org.bakasoft.framboyan;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

import org.bakasoft.framboyan.console.Console;
import org.bakasoft.framboyan.console.ConsoleEvent;

public class Node {

	private final Target target;
	
	private final Node parent;
	
	private final Object description;
	
	private final boolean pending;
	
	private final boolean focused;
	
	private final Console console;
	
	private final Action action;
	
	private final ArrayList<Node> children;
	
	public Node() {
		this.target = null;
		this.parent = null;
		this.description = null;
		this.pending = false;
		this.focused = false;
		this.console = null;
		this.action = null;
		this.children = new ArrayList<>();
	}
	
	public Node(Node parent, Target target, Object description, Action action, boolean pending, boolean focused, Console console) {
		this.target = target;
		this.parent = parent;
		this.description = description;
		this.pending = pending;
		this.focused = focused;
		this.action = action;
		this.console = console;
		this.children = new ArrayList<>();
	}
	
	public void addNode(Node node) {
		// check for the parent
		if (node.getParent() != this) {
			throw new RuntimeException("the kid is not my son");
		}
		
		children.add(node);
	}
	
	public List<Node> getChildren() {
		return Collections.unmodifiableList(children);
	}

	public Result run(boolean onlyFocused) {
		Console console = getConsole();
		boolean successful;
		boolean pending;
		Throwable error;
		
		if (action == null || isPending() || (onlyFocused && !isFocused())) {
			pending = true;
			successful = false;
			error = null;
		}
		else try {
			action.run();
			successful = true;
			pending = false;
			error = null;
		}
		catch (Exception | AssertionError e) {
			successful = false;
			pending = false;
			error = e;
		}
	
		ConsoleEvent[] output = console != null ? console.popEvents() : null;
		
		return new Result(successful, pending, error, output);
	}
	
	public boolean isPending() {
		return pending || (parent != null && parent.isPending());
	}
	
	public boolean isFocused() {
		return focused || (parent != null && parent.isFocused());
	}
	
	public Console getConsole() {
		if (console == null && parent != null) {
			return parent.getConsole();
		}

		return console;
	}
	
	public boolean containFocus() {
		if (focused) {
			return true;
		}
		
		return children.stream()
				.anyMatch(subnode -> subnode.containFocus());
	}
	
	public List<Node> findNodes(Predicate<Node> predicate) {
		ArrayList<Node> nodes = new ArrayList<>();
		
		find_nodes(this, predicate, nodes);
		
		return nodes;
	}

	private static void find_nodes(Node node, Predicate<Node> predicate, ArrayList<Node> nodes) {
		if (predicate.test(node)) {
			nodes.add(node);
		}
		
		for (Node subnode : node.children) {
			find_nodes(subnode, predicate, nodes);
		}
	}
	
	public String joinDescriptions() {
		return joinDescriptions(null);
	}
	
	public String joinDescriptions(Node root) {
		ArrayList<String> subjects = new ArrayList<>();
		Node current = this;
		
		while(current != root) {
			Object description = current.getDescription();
			
			if (description != null) {
				subjects.add(description.toString());	
			}
			
			current = current.getParent();
		}
		
		StringBuilder result = new StringBuilder();
		
		for (int i = subjects.size() - 1; i >= 0; i--) {
			if (result.length() > 0) {
				result.append(' ');
			}
			
			result.append(subjects.get(i));
		}
		
		return result.toString();
	}
	
	public boolean hasDescription() {
		return description != null && !"".equals(description);
	}
	
	public boolean hasAction() {
		return action != null;
	}
	
	public boolean hasChildren() {
		return children.size() > 0;
	}
	
	public boolean hasParent() {
		return parent != null;
	}

	public String getStringDescription() {
		return description != null ? description.toString() : null;
	}
	
	public Target getTarget() {
		return target;
	}
	
	public Node getParent() {
		return parent;
	}

	public Object getDescription() {
		return description;
	}

	public Action getAction() {
		return action;
	}
	
}
