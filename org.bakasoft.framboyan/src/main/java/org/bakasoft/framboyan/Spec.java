package org.bakasoft.framboyan;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class Spec {

	private final String name;
	private final Action action;

	public Spec(String name, Action action) {
		this.name = name;
		this.action = action;
	}

	public Result execute() {
		AtomicBoolean successful = new AtomicBoolean(false);
		AtomicBoolean pending = new AtomicBoolean(false);
		AtomicReference<Throwable> error = new AtomicReference<>(null);
		
		try {
			if (action != null) {
				action.run();
				successful.set(true);
			} 
			else {
				pending.set(true);
			}
		}
		catch (Throwable e) {
			error.set(e);
		}
		
		String output = Framboyan.console.consume();
		
		return new Result(successful.get(), pending.get(), error.get(), output);
	}

	public String getName() {
		return name;
	}

	public Action getAction() {
		return action;
	}

}
