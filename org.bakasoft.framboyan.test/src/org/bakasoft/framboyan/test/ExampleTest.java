package org.bakasoft.framboyan.test;

import java.net.URI;
import java.net.URL;

import org.bakasoft.framboyan.Framboyan;

public class ExampleTest extends Framboyan {{

	describe(URI.class, () -> {
		describe("`create(String)` method", () -> {
			it("should pass with valid URIs", () -> {
				URI.create("http://www.github.com/bakasoft");
			});
			it("should throw an error with invalid URIs", () -> {
				expect(() -> {
					URI.create("file://");
				}).toThrow(IllegalArgumentException.class);
			});
		});
		
		describe("`toASCIIString()` method", () -> {
			xit("should return the URI as a US-ASCII string"); // TODO: add spec
		});
		
		it("should resolve URIs", () -> { // TODO: fix this test
			template((String absolute, String relative, String result) -> {
				console.log("Resolving '%s' using '%s'...", relative, absolute);
				
				URI uri = URI.create(absolute);
				
				expect(uri.resolve(relative).toString()).toBe(result);
			})
			//     Absolute                Relative   Result
			.test("file://Users"         , "."      , "file://Users")
			.test("file://Users/bakasoft", "folder" , "file://Users/bakasoft/folder")
			;
		});
		
		it("should identify the main components", () -> {
			template((String input, String scheme, String authority, String path) -> {
				console.log("Creating URI: ", input);
				
				URI uri = URI.create(input);
				
				expect(uri.getScheme()).toBe(scheme);
				expect(uri.getAuthority()).toBe(authority);
				expect(uri.getPath()).toBe(path);
			})
			//     URI                                  Scheme   Authority             Path
			.test("http://github.com/bakasoft"        , "http" , "github.com"        , "/bakasoft")
			.test("https://bakasoft.github.io/gramat/", "https", "bakasoft.github.io", "/gramat/")
			;
		});
	});
	
	xdescribe(URL.class, () -> { // TODO: fix URL tests
		describe("(String) constructor", () -> {
			it("should pass with valid URLs", () -> {
				new URL("");
			});
		});
	});
	
}}
