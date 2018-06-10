package org.bakasoft.framboyan;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

public class Spec {

	private static final String DEFAULT_CHARSET = "UTF-8";
	
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
		String output = printer(out -> {
			try {
				if (action != null) {
					action.run(out);
					successful.set(true);
				} 
				else {
					pending.set(true);
				}
			}
			catch (Throwable e) {
				error.set(e);
			}
		});
		
		return new Result(successful.get(), pending.get(), error.get(), output);
	}

	public String getName() {
		return name;
	}

	public Action getAction() {
		return action;
	}
	
	private static String printer(Consumer<PrintStream> action) {
		try(ByteArrayOutputStream buffer = new ByteArrayOutputStream()) {
			try(PrintStream printStream = new PrintStream(buffer, true, DEFAULT_CHARSET)) {
				action.accept(printStream);	
			}

			return buffer.toString(DEFAULT_CHARSET);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
