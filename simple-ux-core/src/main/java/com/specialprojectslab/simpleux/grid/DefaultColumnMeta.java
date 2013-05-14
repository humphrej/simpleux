package com.specialprojectslab.simpleux.grid;

import javax.swing.table.TableCellRenderer;

class DefaultColumnMeta implements ColumnMeta {

    private final String title;
    private final String id;
    private final int minWidth;
    private final int maxWidth;
    private final TableCellRenderer renderer;

    public DefaultColumnMeta(String id, String title, int minWidth, int maxWidth, TableCellRenderer renderer) {
        this.id = id;
        this.title = title;
        this.minWidth = minWidth;
        this.maxWidth = maxWidth;
        this.renderer = renderer;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public int getMinWidth() {
        return minWidth;
    }

    @Override
    public int getMaxWidth() {
        return maxWidth;
    }

    @Override
    public TableCellRenderer getRenderer() {
        return renderer;
    }

    @Override
    public String getColumnId() {
        return id;
    }

    public static DefaultColumnMeta cm(String id, String title, int minWidth, int maxWidth, TableCellRenderer renderer) {
        return new DefaultColumnMeta(id, title, minWidth, maxWidth, renderer);
    }

    public static DefaultColumnMeta cm(String id, String title, int minWidth, int maxWidth) {
        return new DefaultColumnMeta(id, title, minWidth, maxWidth, null);
    }


    public static DefaultColumnMeta cm(String id, String title) {
        return new DefaultColumnMeta(id, title, 0, 0, null);
    }

}
