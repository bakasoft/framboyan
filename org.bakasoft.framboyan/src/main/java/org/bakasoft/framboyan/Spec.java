package org.bakasoft.framboyan;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

import org.bakasoft.framboyan.util.Toolbox;

public class Spec implements Target {

	private final Group parent;
	private final Object subject;
	private final Action action;

	public Spec(Group parent, Object subject, Action action) {
		this.parent = Objects.requireNonNull(parent, "parent group must not be null");
		this.subject = subject;
		this.action = action;
		
		this.parent.items.add(this);
	}

	public Result execute() {
		AtomicBoolean successful = new AtomicBoolean(false);
		AtomicBoolean pending = new AtomicBoolean(false);
		AtomicReference<Throwable> error = new AtomicReference<>(null);
		
		try {
			if (isPending()) {
				pending.set(true);
			}
			else if (action != null) {
				action.run();
				successful.set(true);
			}
		}
		catch (Throwable e) {
			error.set(e);
		}
		
		String output = parent.getConsole().consume();
		
		return new Result(successful.get(), pending.get(), error.get(), output);
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
		return action == null || (parent != null && parent.isPending());
	}

	public Action getAction() {
		return action;
	}
	
	@Override
	public String toString() {
		return "Spec: " + Toolbox.joinSubjects(this);
	}

}
