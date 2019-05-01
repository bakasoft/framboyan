package org.bakasoft.framboyan.diff;

import org.bakasoft.framboyan.util.CodeWriter;

public class DiffList extends DiffItem {

    private final int index;
    private final DiffItem diffItem;

    public DiffList(int index, DiffItem diffItem) {
        this.index = index;
        this.diffItem = diffItem;
    }

    @Override
    public void toString(CodeWriter writer) {
        writer.write('[');
        writer.write(String.valueOf(index));
        writer.write("]: ");
        diffItem.toString(writer);
    }
}
