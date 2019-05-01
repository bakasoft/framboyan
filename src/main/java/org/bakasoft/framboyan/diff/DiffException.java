package org.bakasoft.framboyan.diff;

public class DiffException extends RuntimeException {

    private final DiffItem diffItem;

    public DiffException(DiffItem diffItem) {
        super(diffItem.toString());
        this.diffItem = diffItem;
    }

}
