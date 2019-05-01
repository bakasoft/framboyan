package org.bakasoft.framboyan.diff;

import org.bakasoft.framboyan.util.CodeWriter;

public class DiffValue extends DiffItem {

    private final Object expected;
    private final Object actual;

    public DiffValue(Object expected, Object actual){
        this.expected = expected;
        this.actual = actual;
    }

    @Override
    public void toString(CodeWriter writer) {
        writer.write("Expected value " + expected + " instead of " + actual);
    }
}
