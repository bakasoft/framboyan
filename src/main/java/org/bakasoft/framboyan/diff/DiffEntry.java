package org.bakasoft.framboyan.diff;

import org.bakasoft.framboyan.util.CodeWriter;

public class DiffEntry extends DiffItem {

    private final Object key;
    private final DiffItem diffItem;

    public DiffEntry(Object key, DiffItem diffItem) {
        this.key = key;
        this.diffItem = diffItem;
    }

    @Override
    public void toString(CodeWriter writer) {
        writer.write(String.valueOf(key));
        writer.write(": ");
        diffItem.toString(writer);
    }
}
