package org.bakasoft.framboyan.diff;

import org.bakasoft.framboyan.util.CodeWriter;

abstract public class DiffItem {

    abstract public void toString(CodeWriter writer);

    @Override
    public String toString() {
        StringBuilder output = new StringBuilder();
        CodeWriter writer = new CodeWriter(output);

        toString(writer);

        return output.toString();
    }
}
