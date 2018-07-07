# Framboyan

Framboyan is a simple Java test framework inspired in `jasmine` or `mocha` and focused in simplicity and developer experience.

# Ideals

- Writing and running tests should be simple and straightforward.
- Debugging messages should be clear and descriptive.
- The framework dependencies should tend to zero.

# Example

**ExampleTest.java**

```java
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
```

**Main.java**

```java
import org.bakasoft.framboyan.Framboyan;

public class Main {

	public static void main(String[] args) {
		Framboyan.run();
	}

}
```

**Output:**

```

⚙️ This is a test suite...
  ✅ This is a simple spec

  ❌ This spec has a template (and fails)

Testing: 10 / 5 = 2
Testing: 8 / 4 = 2
Testing: 6 / 3 = 2
Testing: 4 / 0 = null

java.lang.ArithmeticException: / by zero
	at org.bakasoft.framboyan.test.ExampleTest.lambda$3(ExampleTest.java:22)
	at org.bakasoft.framboyan.templates.Template3.test(Template3.java:12)
	at org.bakasoft.framboyan.test.ExampleTest.lambda$2(ExampleTest.java:27)
	at org.bakasoft.framboyan.Spec.execute(Spec.java:23)
	at org.bakasoft.framboyan.Runner.run(Runner.java:20)
	at org.bakasoft.framboyan.Framboyan.run(Framboyan.java:95)
	at org.bakasoft.framboyan.Framboyan.run(Framboyan.java:91)
	at org.bakasoft.framboyan.test.Main.main(Main.java:9)


⚙️ This is another test suite...
  ✅ This spec checks for an error
  ⚠️ This is a pending spec so is not executed

FAILED
4 test(s), 2 passed, 1 pending, 1 failed ⚠️
```

# Using it

To use this framework using [JitPack](https://jitpack.io/), add following lines to your `build.gradle` file.

```
repositories {
	...
	maven { url 'https://jitpack.io' }
}

...

dependencies {
	...
	implementation 'com.github.bakasoft:framboyan:v1.0'
}
```

# Build

Starting from the repository root, go to the project directory:

```sh
cd ./org.bakasoft.framboyan
```

Build library:

```sh
./gradlew build
```

Generate JAR in `./build/libs/framboyan-v0.1.jar`:

```sh
./gradlew jar
```
