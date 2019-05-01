package org.bakasoft.framboyan.diff;

import org.bakasoft.framboyan.util.CodeWriter;

public class DiffSize extends DiffItem {

    private final int expectedSize;
    private final int actualSize;

    public DiffSize(int expectedSize, int actualSize) {
        this.expectedSize = expectedSize;
        this.actualSize = actualSize;
    }

    @Override
    public void toString(CodeWriter writer) {
        writer.write("Expected size " + expectedSize + " instead of " + actualSize);
    }
}
