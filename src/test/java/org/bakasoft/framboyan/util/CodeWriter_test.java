package org.bakasoft.framboyan.util;

import org.bakasoft.framboyan.test.FramboyanTest;

import java.io.IOException;

public class CodeWriter_test extends FramboyanTest {{

  pass("Check default values", () -> {
    CodeWriter writer = new CodeWriter(new StringBuilder());

    expect(writer.getNewLine()).toEqual(System.lineSeparator());
    expect(writer.getTab()).toEqual("  ");
  });

  pass("Check indentation and line breaks behaviours", () -> {
    StringBuilder output = new StringBuilder();
    CodeWriter writer = new CodeWriter(output, "\n", "\t");

    writer.breakLine();
    writer.write("-");
    writer.breakLine();
    writer.indent(+1);
    writer.write("-");
    writer.breakLine();
    writer.indent(+1);
    writer.write("-");
    writer.breakLine();
    writer.indent(-1);
    writer.write("-");
    writer.breakLine();
    writer.breakLine();

    expect(output.toString()).toEqual("\n-\n\t-\n\t\t-\n\t-\n");
  });

  pass("Force writing errors", () -> {
    CodeWriter writer = new CodeWriter(new Appendable() {
      @Override
      public Appendable append(CharSequence csq) throws IOException { throw new IOException(); }

      @Override
      public Appendable append(CharSequence csq, int start, int end) throws IOException { throw new IOException(); }

      @Override
      public Appendable append(char c) throws IOException { throw new IOException(); }
    });

    fail(RuntimeException.class, () -> {
      writer.breakLine();
      writer.breakLine();
    });

    fail(RuntimeException.class, () -> {
      writer.write('c');
    });
  });

}}
