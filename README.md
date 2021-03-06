# Framboyan

Framboyan is a test framework for Java focused in simplicity, developer experience and following ideals:

- Writing and running tests should be simple and straightforward.
- Debugging messages should be clear and descriptive.
- The framework dependencies should be zero.

# Example

**example/Example.java**

```java
package example;

import org.bakasoft.framboyan.test.TestCase;
import org.bakasoft.framboyan.util.consumers.C4;
import org.bakasoft.framboyan.util.functions.F2;

import java.net.URI;

public class Example extends TestCase {{
  pass("`create(String)` method", () -> {
    pass("should accept valid URIs", () -> {
      URI.create("http://www.github.com/bakasoft");
    });
    fail("should throw an error with invalid URIs", IllegalArgumentException.class, () -> {
      URI.create("file://");
    });
  });
  pass("`toASCIIString()` method", () -> {
    xpass("should return the URI as a US-ASCII string"); // this test is not implemented
  });
  pass("should resolve URIs", () -> { // this test is failing
    F2<String, String> f = (absolute, relative) ->
        URI.create(absolute).resolve(relative).toString();
    //             Absolute                 Relative           Result
    expect(f.apply("file://Users"         , "."     )).toEqual("file://Users");
    expect(f.apply("file://Users/bakasoft", "folder")).toEqual("file://Users/bakasoft/folder");
  });
  pass("should identify the main components", () -> {
    C4<String, String, String, String> c = (input, scheme, authority, path) -> {
      URI uri = URI.create(input);

      expect(uri.getScheme()).toEqual(scheme);
      expect(uri.getAuthority()).toEqual(authority);
      expect(uri.getPath()).toEqual(path);
    };
    //       URI                                   Scheme   Authority             Path
    c.accept("http://github.com/bakasoft"        , "http" , "github.com"        , "/bakasoft");
    c.accept("https://bakasoft.github.io/gramat/", "https", "bakasoft.github.io", "/gramat/");
  });
}}
```

**example/RunExample.java**

```java
package example;

import org.bakasoft.framboyan.test.TestRunner;

public class RunExample {
  public static void main(String[] args) {
    // Run all tests found in `example` package
    TestRunner.run("example");
  }
}
```

**Output:**

```
example ⛔
  Example ⛔
    `create(String)` method ✅
      should accept valid URIs ✅
      should throw an error with invalid URIs ✅
    `toASCIIString()` method ⛔
      should return the URI as a US-ASCII string ⛔
    should resolve URIs: Expected "file://Users/folder" to equal "file://Users/bakasoft/folder". ❌
    should identify the main components ✅

should resolve URIs ❌
  Expected "file://Users/folder" to equal "file://Users/bakasoft/folder".
    org.bakasoft.framboyan.expect.ExpectError:
      org.bakasoft.framboyan.expect.ExpectToEqual.pass(ExpectToEqual.java:8)
      org.bakasoft.framboyan.expect.Expect.toEqual(Expect.java:37)
      example.Example.lambda$new$5(Example.java:26)
      org.bakasoft.framboyan.test.TestCase.lambda$process_pass$0(FramboyanTest.java:66)
      org.bakasoft.framboyan.test.TestCase.process(FramboyanTest.java:51)
      org.bakasoft.framboyan.test.TestCase.process_pass(FramboyanTest.java:64)
      org.bakasoft.framboyan.test.TestCase.pass(FramboyanTest.java:141)
      example.Example.<init>(Example.java:21)
      sun.reflect.NativeConstructorAccessorImpl.newInstance0(Native Method)
      sun.reflect.NativeConstructorAccessorImpl.newInstance(NativeConstructorAccessorImpl.java:62)
      sun.reflect.DelegatingConstructorAccessorImpl.newInstance(DelegatingConstructorAccessorImpl.java:45)
      java.lang.reflect.Constructor.newInstance(Constructor.java:423)
      org.bakasoft.framboyan.test.StandardRunner.run(StandardRunner.java:31)
      org.bakasoft.framboyan.test.Framboyan.run(Framboyan.java:24)
      example.RunExample.main(RunExample.java:7)

Some tests failed ❌
  3 successful test(s)
  1 pending test(s)
  1 failed test(s)
```

# Using it

[See Source Dependencies](https://blog.gradle.org/introducing-source-dependencies)

# Gradle Tasks

Build library:

```sh
./gradlew build
```

Run tests:

```sh
./gradlew test
```

Generate JAR:

```sh
./gradlew jar
```
