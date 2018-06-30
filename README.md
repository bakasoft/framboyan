# Framboyan

Framboyan is a simple Java test framework inspired in `jasmine` or `mocha` and focused in simplicity and developer experience.

# Ideals

- Writing and running tests should be simple and straightforward.
- Debugging messages should be clear and descriptive.
- The framework dependencies should tend to zero.

# Example

**ExampleTest.java**

```java
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
```

**Main.java**

```java
import org.bakasoft.framboyan.Framboyan;

public class Main {

	public static void main(String[] args) {
		Framboyan.add(ExampleTest.class);
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
