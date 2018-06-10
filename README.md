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

		it("This is a spec without output (passes)", () -> {
			int test = 0;

			for (int i = 0; i < 10; i++) {
				test++;
			}

			expect(test).toBe(10);
		});

		it("This is a pending spec");

		it("This is a spec WITH output (fails)", (out) -> {
			int test = 0;

			for (int i = 0; i < 5; i++) {
				int prev = test;

				test++;

				out.println("Previous value: " + prev + ", current: " + test);
			}

			expect(test).toBe(10);
		});

		it("This spec expects an error", () -> {
			expect(() -> {
				String test = null;

				if (Math.random() < 0) {
					test = "";
				}

				test = test.toString();
			}).toThrow(NullPointerException.class);
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
  ✅ This is a spec without output (passes)
  ⚠️ This is a pending spec

  ❌ This is an spec WITH output (fails)
Previous value: 0, current: 1
Previous value: 1, current: 2
Previous value: 2, current: 3
Previous value: 3, current: 4
Previous value: 4, current: 5

org.bakasoft.framboyan.errors.ExpectedEqual: 5 expected to be 10.
	at org.bakasoft.framboyan.expects.PositiveExpect.toBe(PositiveExpect.java:28)
	at ExampleTest.lambda$2(ExampleTest.java:30)
	at org.bakasoft.framboyan.Spec.lambda$0(Spec.java:28)
	at org.bakasoft.framboyan.Spec.printer(Spec.java:56)
	at org.bakasoft.framboyan.Spec.execute(Spec.java:25)
	at org.bakasoft.framboyan.Runner.run(Runner.java:20)
	at org.bakasoft.framboyan.Framboyan.run(Framboyan.java:82)
	at org.bakasoft.framboyan.Framboyan.run(Framboyan.java:78)
	at Main.main(Main.java:7)

  ✅ This spec expects an error

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
