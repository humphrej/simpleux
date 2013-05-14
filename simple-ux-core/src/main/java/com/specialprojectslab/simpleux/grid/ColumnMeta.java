package com.specialprojectslab.simpleux.grid;

import javax.swing.table.TableCellRenderer;

public interface ColumnMeta<C> {
    String getTitle();

    int getMinWidth();

    int getMaxWidth();

    TableCellRenderer getRenderer();

    C getColumnId();
}
