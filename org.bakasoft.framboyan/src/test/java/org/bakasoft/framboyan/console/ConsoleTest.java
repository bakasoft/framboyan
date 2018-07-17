package org.bakasoft.framboyan.console;

import org.bakasoft.framboyan.Framboyan;
import org.bakasoft.framboyan.annotation.Describe;
import org.bakasoft.framboyan.console.ConsoleLog.Type;

@Describe("class Console")
public class ConsoleTest extends Framboyan {{
	
	describe("methods log(...), error(...), info(...), and warn(...)", () -> {
		it ("should create the expected console events", () -> {
			Console console = new Console();
			Object[] items = { "a", "b", "c" };
			
			console.log(items);
			console.error(items);
			console.info(items);
			console.warn(items);
			
			expect(console.getEvents().length).toBe(4);
			
			template((Integer index, Type type) -> {
				ConsoleEvent event = console.getEvents()[index];
				
				expect(event).toBeInstanceOf(ConsoleLog.class);
				
				ConsoleLog log = (ConsoleLog)event;
				
				expect(log.getItems()).toBe(items);
				expect(log.getType()).toBe(type);
			})
			.test(0, Type.LOG)
			.test(1, Type.ERROR)
			.test(2, Type.INFO)
			.test(3, Type.WARN)
			;
			
		});
	});
	
	describe("method getEvents()", () -> {
		it("should return them in the same order", () -> {
			Console console = new Console();
			
			console.log("test1");
			console.log("test2");
			
			ConsoleEvent[] events = console.getEvents();
			
			expect(events).not.toBeEmpty();
			expect(((ConsoleLog)events[0]).getItems()[0]).toBe("test1");
			expect(((ConsoleLog)events[1]).getItems()[0]).toBe("test2");
		});
	});
	
	describe("method clear()", () -> {
		it("should remove appened events", () -> {
			Console console = new Console();
			
			console.log("test");
			
			expect(console.getEvents()).not.toBeEmpty();
			
			console.clear();
			
			expect(console.getEvents()).toBeEmpty();
		});
	});
	
	describe("method popEvents()", () -> {
		it("should remove appened events and return them in the same order", () -> {
			Console console = new Console();
			
			console.log("test1");
			console.log("test2");
			
			expect(console.getEvents()).not.toBeEmpty();
			
			ConsoleEvent[] events = console.popEvents();
			
			expect(console.getEvents()).toBeEmpty();
			
			expect(events).not.toBeEmpty();
			expect(((ConsoleLog)events[0]).getItems()[0]).toBe("test1");
			expect(((ConsoleLog)events[1]).getItems()[0]).toBe("test2");
		});
	});

}}