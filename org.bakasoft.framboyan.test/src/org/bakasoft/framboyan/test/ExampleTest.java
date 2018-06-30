package org.bakasoft.framboyan.test;

import org.bakasoft.framboyan.Framboyan;

public class ExampleTest extends Framboyan {{

	describe("This is a test suite", () -> {
		it("This is a simple spec", () -> {
			int count = 0;

			for (int i = 0; i < 10; i++) {
				console.log("Index: %s", i);
				count++;
			}

			expect(count).toBe(10);
		});
		
		it("This spec has a template (and fails)", () -> {
			template((Integer dividend, Integer divisor, Integer result) -> {
				console.log("Testing: ", dividend, " / ", divisor, " = ", result);
				expect(dividend / divisor).toBe(result);
			})
			.test(10, 5, 2)
			.test(8, 4, 2)
			.test(6, 3, 2)
			.test(4, 0, null);
		});
	});
	
	describe("This is another test suite", () -> {
		it("This spec checks for an error", () -> {
			expect(() -> {
				Math.floorMod(10, 0);
			}).toThrow(ArithmeticException.class);
		});
		
		xit("This is a pending spec so is not executed", () -> {
			// TODO: complete this test
		});
	});
	
}}
