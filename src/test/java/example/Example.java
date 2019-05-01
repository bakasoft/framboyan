package example;

import org.bakasoft.framboyan.test.FramboyanTest;
import org.bakasoft.framboyan.util.consumers.C4;
import org.bakasoft.framboyan.util.functions.F2;

import java.net.URI;

public class Example extends FramboyanTest {{
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
