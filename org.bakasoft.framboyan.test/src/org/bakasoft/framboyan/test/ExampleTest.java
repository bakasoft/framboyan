package org.bakasoft.framboyan.test;

import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import org.bakasoft.framboyan.Framboyan;

public class ExampleTest extends Framboyan {{

	describe("Pattern.compile() method", () -> {
		it("should compile valid expressions", () -> {
			Pattern.compile("x+y?z*");
			Pattern.compile("(.){1,3}x(a|b|c)");
		});
		
		it("should generate an error with invalid expressions", () -> {
			template((String expression) -> {
				expect(() -> {
					Pattern.compile(expression);
				}).toThrow(PatternSyntaxException.class);
			})
			.test("abc)")
			.test("[test\\]");
		});
		
		xit("should compile expressions with flags", () -> {
			// TODO: complete this test
		});
	});
	
}}
