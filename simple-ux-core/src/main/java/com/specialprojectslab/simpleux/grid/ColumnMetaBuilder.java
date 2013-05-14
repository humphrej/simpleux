package com.specialprojectslab.simpleux.grid;

import javax.swing.table.TableCellRenderer;

public class ColumnMetaBuilder {

    private TableCellRenderer renderer;
    private int minWidth;
    private int maxWidth;
    private String id;
    private String title;

    public ColumnMetaBuilder tableCellRenderer(TableCellRenderer renderer) {
        this.renderer = renderer;
        return this;
    }

    public ColumnMetaBuilder minWidth(int minWidth) {
        this.minWidth = minWidth;
        return this;
    }

    public ColumnMetaBuilder maxWidth(int maxWidth) {
        this.maxWidth = maxWidth ;
        return this;
    }

    public ColumnMetaBuilder title(String title) {
        this.title = title ;
        return this;
    }

    public ColumnMetaBuilder id(String id) {
        this.id = id ;
        return this;
    }

    public ColumnMeta build() {
        return new DefaultColumnMeta(id,title, minWidth, maxWidth, renderer);
    }


    public static ColumnMetaBuilder newColumn() {
        return new ColumnMetaBuilder();
    }
}
