package org.bakasoft.framboyan.util;

import java.io.IOException;

public class CodeWriter {

    private final Appendable output;
    private final String newLine;
    private final String tab;

    private int indentation;

    private boolean breakLine;

    public CodeWriter(Appendable output) {
        this(output, System.lineSeparator(), "  ");
    }

    public CodeWriter(Appendable output, String newLine, String tab) {
        this.output = output;
        this.newLine = newLine;
        this.tab = tab;
    }

    public String getNewLine() {
        return newLine;
    }

    public String getTab() {
        return tab;
    }

    public void indent(int delta) {
        indentation += delta;
    }

    public void breakLine() {
        try {
            if (breakLine) {
                output.append(newLine);
            } else {
                breakLine = true;
            }
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void write(char c) {
        try {
            if (breakLine) {
                breakLine = false;
                output.append(newLine);

                for (int i = 0; i < indentation; i++) {
                    output.append(tab);
                }
            }

            output.append(c);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void write(CharSequence code) {
        if (code != null) {
            for (int i = 0; i < code.length(); i++) {
                write(code.charAt(i));
            }
        }
    }

}
