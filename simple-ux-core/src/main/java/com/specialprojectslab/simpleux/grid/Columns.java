package com.specialprojectslab.simpleux.grid;

import com.google.common.base.Function;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.specialprojectslab.simpleux.boot.BootStage;

public class Columns {
    public static Object[] getColumnNames(ColumnMeta[] columnMetas) {

        Object[] columns = new Object[columnMetas.length];
        for( int i=0;i<columnMetas.length;i++) {
            columns[i] = columnMetas[i].getTitle();
        }
        return columns;
    }
}
