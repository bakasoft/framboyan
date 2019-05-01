package org.bakasoft.framboyan.expect;

import org.bakasoft.framboyan.inspect.Inspector;

public class ExpectError extends AssertionError {

  private static String generate_message(String format, Object[] args) {
    for (int i = 0; i < args.length; i++) {
      args[i] = Inspector.inspect(args[i]);
    }

    return String.format(format, args);
  }

  ExpectError(String format, Object... args) {
    super(generate_message(format, args));
  }

}
