package org.bakasoft.framboyan.diff;

import org.bakasoft.framboyan.util.CodeWriter;

public class DiffMap extends DiffItem {

    private final DiffEntry[] entries;

    public DiffMap(DiffEntry[] entries) {
        this.entries = entries;
    }

    @Override
    public void toString(CodeWriter writer) {
        writer.write('{');
        writer.indent(+1);
        writer.breakLine();

        for (DiffEntry entry : entries) {
            entry.toString(writer);
            writer.breakLine();
        }

        writer.indent(-1);
        writer.write('}');
    }
}
