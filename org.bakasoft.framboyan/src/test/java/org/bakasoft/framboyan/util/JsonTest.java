package org.bakasoft.framboyan.util;

import java.util.List;
import java.util.Map;

import org.bakasoft.framboyan.Framboyan;
import org.bakasoft.framboyan.annotation.Describe;

@Describe("class JSON")
public class JsonTest extends Framboyan {{

	describe("method parse()", () -> {
		it ("should parse `null` literal", () -> {
			expect(JSON.parse("null")).toBeNull();
		});
		it ("should parse `true` literal", () -> {
			expect(JSON.parse("true")).toBeTrue();
		});
		it ("should parse `false` literal", () -> {
			expect(JSON.parse("false")).toBeFalse();
		});
		it ("should parse numbers", () -> {
			double delta = 0.00001;
			expect(JSON.parse("0")).toBeCloseTo(0, delta);
			expect(JSON.parse("-0")).toBeCloseTo(-0, delta);
			expect(JSON.parse("1")).toBeCloseTo(1, delta);
			expect(JSON.parse("-1")).toBeCloseTo(-1, delta);
			expect(JSON.parse("0.1")).toBeCloseTo(0.1, delta);
			expect(JSON.parse("-0.1")).toBeCloseTo(-0.1, delta);
			expect(JSON.parse("12.34")).toBeCloseTo(12.34, delta);
			expect(JSON.parse("-12.34")).toBeCloseTo(-12.34, delta);
			expect(JSON.parse("1e1")).toBeCloseTo(1e1, delta);
			expect(JSON.parse("-1e1")).toBeCloseTo(-1e1, delta);
			expect(JSON.parse("1.1e1")).toBeCloseTo(1.1e1, delta);
			expect(JSON.parse("-1.1e1")).toBeCloseTo(-1.1e1, delta);
			expect(JSON.parse("12.12e+2")).toBeCloseTo(12.12e2, delta);
			expect(JSON.parse("-12.12e+2")).toBeCloseTo(-12.12e2, delta);
			expect(JSON.parse("12.12e-3")).toBeCloseTo(12.12e-3, delta);
			expect(JSON.parse("-12.12e-3")).toBeCloseTo(-12.12e-3, delta);
		});
		it ("should parse strings", () -> {
			expect(JSON.parse("\"\"")).toBe("");
			expect(JSON.parse("\"abc\"")).toBe("abc");
			expect(JSON.parse("\"abc\\r\\nxyz\"")).toBe("abc\r\nxyz");
			expect(JSON.parse("\"\\\"\\\\\\/\\b\\f\\n\\r\\t\\u1234\"")).toBe("\"\\/\b\f\n\r\t\u1234");
		});
		it ("should parse arrays", () -> {
			expect(JSON.parse("[]")).toBeEmpty();
			expect(JSON.parse("[0]")).toBe(new Object[] {0});
			expect(JSON.parse("[0 , 1 ]")).toBe(0, 1);
			expect(JSON.parse("[0 , 1, \"a\"]")).toBe(0, 1, "a");
		});
		it ("should parse maps", () -> {
			String json = ""
					+ "{ \"str\": \"\", \"num\": 10\r\n"
					+ ",\"boolT\": true, \"boolF\":false,\"null\":\r\n"
					+ "null, \"array\": [1, \"a\", \r\n"
					+ "true, false, null, {}, []] }";
			
			console.log(json);
			
			Object obj = JSON.parse(json);
			
			expect(obj).toBeInstanceOf(Map.class);
			
			Map<?, ?> map = (Map<?, ?>)obj;
			
			expect(map.get("str")).toBe("");
			expect(map.get("num")).toBe(10);
			expect(map.get("boolT")).toBeTrue();
			expect(map.get("boolF")).toBeFalse();
			expect(map.get("null")).toBeNull();
			expect(map.get("array")).toBeInstanceOf(List.class);
		});
	});
	
}}
