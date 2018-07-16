package org.bakasoft.framboyan;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.UnaryOperator;

import org.bakasoft.framboyan.annotation.Describe;
import org.bakasoft.framboyan.annotation.Focused;
import org.bakasoft.framboyan.annotation.Pending;
import org.bakasoft.framboyan.expect.Expect;

@Describe("Suite class")
public class SuiteTest extends Framboyan {{
	
	@Describe("Dummy Suite")
	@Pending
	@Focused
	class DummySuite extends Suite {

		public DummySuite() {
			super(new Console());
		}
		
	};
	
	UnaryOperator<Suite> populate = (Suite suite) -> {
		suite.describe("Child group", () -> {
			suite.it("Child it", () -> {
				// pass
			});
		});
		return suite;
	};

	
	describe("getConsole() method", () -> {
		it("should take it from the constructor", () -> {
			Console console = new Console();
			
			expect(new Suite(console).getConsole()).toEqual(console);
			expect(new Suite(null).getConsole()).toBeNull();
		});
	});
	
	describe("getDescription() method", () -> {
		it ("should be null when there is no annotation", () -> {
			expect(new Suite(null).getDescription()).toBeNull();
		});	
		it ("should take it from the class annotation when is present", () -> {
			expect(new DummySuite().getDescription()).toBe("Dummy Suite");
		});	
	});
	
	describe("isPending() method", () -> {
		it ("should be always `true` when the targets are empty", () -> {
			expect(new Suite(null).isPending()).toBeTrue();
		});
		it ("should be `true` when the class is annotated and has targets", () -> {
			expect(populate.apply(new DummySuite()).isPending()).toBeTrue();
		});
		it ("should be `false` when the class is not annotated and has targets", () -> {
			expect(populate.apply(new Suite(null)).isPending()).toBeFalse();
		});
	});
	
	describe("isFocused() method", () -> {
		it ("should be `false` when the class is not annotated", () -> {
			expect(new Suite(null).isFocused()).toBeFalse();
		});
		it ("should be `true` when the class is annotated", () -> {
			expect(new DummySuite().isFocused()).toBeTrue();
		});
	});
	
	// TODO: if you use `xit` here the test is ignored and is not shown as pending
	
	describe("isRoot() method", () -> {
		it ("should detect subclasses", () -> {
			expect(new Suite(null).isRoot()).toBeFalse();
			expect(new DummySuite().isRoot()).toBeTrue();
		});
	});
	
	describe("getTargets() method", () -> {
		it ("should return the targets created be `describe()` and `it()` methods", () -> {
			Suite suite = new Suite(null);
			
			suite.describe("test1", () -> { });
			suite.it("test2", () -> { });
			
			expect(suite.getTargets().size()).toBe(2);
		});
	});
	
	describe("buildInto(Node) method", () -> {
		it ("should create the `Node` based on the suite", () -> {
			Suite suite = new DummySuite();
			
			suite.describe("test1", () -> { });
			suite.it("test2", () -> { });
			
			Node node = suite.buildInto(null);
			
			expect(node.getAction()).toBeNull();
			expect(node.getChildren()).not.toBeNull();;
			expect(node.getChildren().size()).toBe(2);
			expect(node.getConsole()).toBe(suite.getConsole());
			expect(node.getDescription()).toBe("Dummy Suite");
			expect(node.getParent()).toBeNull();
			expect(node.getTarget()).toBe(suite);
		});
	});
	
	describe("describe(...), it(...) and variations", () -> {
		it ("should add the corresponding targets", () -> {
			Suite suite = new Suite(null);
			
			suite.describe("group1", () -> {
				suite.it("group1_it", () -> {});
			});
			suite.it("it1", () -> {});
			suite.xdescribe("group2", () -> {
				suite.it("group2_it", () -> {});
			});
			suite.xit("it2", () -> {});
			suite.fdescribe("group3", () -> {
				suite.it("group3_it", () -> {});
			});
			suite.fit("it3", () -> {});
			
			expect(suite.getTargets().size()).toBe(6);
			
			template((Integer index, String name, Boolean pending, Boolean focused, Class<?> type) -> {
				Target target = suite.getTargets().get(index);
				
				expect(target.getDescription()).toBe(name);
				expect(target.isPending()).toBe(pending);
				expect(target.isFocused()).toBe(focused);
				expect(target).toBeInstanceOf(type);
			})
			.test(0, "group1", false, false, Group.class)
			.test(1, "it1"   , false, false, Spec.class )
			.test(2, "group2", true , false, Group.class)
			.test(3, "it2"   , true , false, Spec.class )
			.test(4, "group3", false, true , Group.class)
			.test(5, "it3"   , false, true , Spec.class )
			;
		});
	});
	
	describe("expect(...) methods", () -> {
		it ("should return the Expect object with the given value", () -> {
			Suite suite = new Suite(null);
			Object obj = new Object();
			Expect instance = suite.expect(obj);
			
			expect(instance.getValue()).toEqual(obj);
		});
		it ("should return the Expect object with the given action", () -> {
			Suite suite = new Suite(null);
			Expect instance = suite.expect(() -> {});
			
			expect(instance.getValue()).toBeInstanceOf(Action.class);
		});
	});
	
	describe("template(...) methods", () -> {
		it ("should support templates with 1 parameter", () -> {
			Suite suite = new Suite(null);
			AtomicInteger counter = new AtomicInteger(0);
			
			suite
			.template((Integer arg1) -> counter.addAndGet(arg1))
			.test(1)
			.test(2)
			.test(3);
			
			expect(counter).toBe(6);
		});
		it ("should support templates with 2 parameter", () -> {
			Suite suite = new Suite(null);
			AtomicInteger counter = new AtomicInteger(0);
			
			suite
			.template((Integer arg1, Integer arg2) -> {
				counter.addAndGet(arg1);
				counter.addAndGet(arg2);
			})
			.test(1, 4)
			.test(2, 5)
			.test(3, 6);
			
			expect(counter).toBe(21);
		});
		it ("should support templates with 3 parameter", () -> {
			Suite suite = new Suite(null);
			AtomicInteger counter = new AtomicInteger(0);
			
			suite
			.template((Integer arg1, Integer arg2, Integer arg3) -> {
				counter.addAndGet(arg1);
				counter.addAndGet(arg2);
				counter.addAndGet(arg3);
			})
			.test(1, 4, 7)
			.test(2, 5, 8)
			.test(3, 6, 9);
			
			expect(counter).toBe(45);
		});
		it ("should support templates with 4 parameter", () -> {
			Suite suite = new Suite(null);
			AtomicInteger counter = new AtomicInteger(0);
			
			suite
			.template((Integer arg1, Integer arg2, Integer arg3, Integer arg4) -> {
				counter.addAndGet(arg1);
				counter.addAndGet(arg2);
				counter.addAndGet(arg3);
				counter.addAndGet(arg4);
			})
			.test(1, 4, 7, 10)
			.test(2, 5, 8, 11)
			.test(3, 6, 9, 12);
			
			expect(counter).toBe(78);
		});
		it ("should support templates with 5 parameter", () -> {
			Suite suite = new Suite(null);
			AtomicInteger counter = new AtomicInteger(0);
			
			suite
			.template((Integer arg1, Integer arg2, Integer arg3, Integer arg4, Integer arg5) -> {
				counter.addAndGet(arg1);
				counter.addAndGet(arg2);
				counter.addAndGet(arg3);
				counter.addAndGet(arg4);
				counter.addAndGet(arg5);
			})
			.test(1, 4, 7, 10, 13)
			.test(2, 5, 8, 11, 14)
			.test(3, 6, 9, 12, 15);
			
			expect(counter).toBe(120);
		});
	});
	
	describe("templater(...) methods", () -> {
		it ("should support templaters 2x2", () -> {
			Suite suite = new Suite(null);
			AtomicInteger counter = new AtomicInteger(0);
			
			suite.templater((Integer a1, Integer a2) -> {
				return suite.template((Integer b1, Integer b2) -> {
					counter.addAndGet(a1);
					counter.addAndGet(a2);
					counter.addAndGet(b1);
					counter.addAndGet(b2);
				});
			})
			.using(1, 2)
			.test(3, 4);
			
			expect(counter).toBe(10);
		});
	});
	
	describe("fail() method", () -> {
		it ("should generate an assertion error", () -> {
			Suite suite = new Suite(null);
			AssertionError error;
			
			try {
				suite.fail();
				error = null;
			}
			catch(AssertionError e) {
				error = e;
			}
			
			expect(error).not.toBeNull();
		});
	});
	
}}