package com.specialprojectslab.simpleux.grid;

import org.jdesktop.swingx.JXTable;
import org.jdesktop.swingx.table.TableColumnExt;

import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;

public class SimpleUXTable extends JXTable {


    private final ColumnMeta[] columnMetas;

    public SimpleUXTable(TableModel tm, ColumnMeta[] columns) {
        super(tm);
        this.columnMetas = columns;

        for (int i = 0; i < columns.length; i++) {

            TableColumnExt col = getColumnExt(i);
            col.setIdentifier(columns[i].getColumnId());

            int minWidth = columns[i].getMinWidth();
            if (minWidth > 0)
                col.setMinWidth(minWidth);

            int maxWidth = columns[i].getMaxWidth();
            if (maxWidth > 0)
                col.setMaxWidth(maxWidth);

            TableCellRenderer renderer = columns[i].getRenderer();
            if (renderer != null)
                col.setCellRenderer(renderer);
        }

    }


    public ColumnMeta[] getColumnMetas() {
        return columnMetas;
    }
}
