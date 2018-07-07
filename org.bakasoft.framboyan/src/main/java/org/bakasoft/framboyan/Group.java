package org.bakasoft.framboyan;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.bakasoft.framboyan.util.Toolbox;

public class Group implements Target {
	
	private final Group parent;
	private final Object subject;
	private final Console console;
	private final boolean pending;
	
	protected final ArrayList<Target> items;
	
	public Group(Group parent, Object subject, Console console, boolean pending) {
		this.parent = parent;
		this.subject = subject;
		this.console = console;
		this.pending = pending;
		this.items = new ArrayList<>();
		
		if (this.parent != null) {
			this.parent.items.add(this);
		}
	}

	public List<Target> getItems() {
		return Collections.unmodifiableList(items);
	}
	
	public Console getConsole() {
		return console;
	}
	
	@Override
	public Group getParent() {
		return parent;
	}

	@Override
	public Object getSubject() {
		return subject;
	}

	@Override
	public boolean isPending() {
		return pending || (parent != null && parent.isPending());
	}
	
	@Override
	public String toString() {
		return "Group: " + Toolbox.joinSubjects(this);
	}

}
