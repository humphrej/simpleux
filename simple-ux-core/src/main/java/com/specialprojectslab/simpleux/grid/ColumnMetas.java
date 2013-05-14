package com.specialprojectslab.simpleux.grid;

public interface ColumnMetas<C> extends Iterable<ColumnMeta> {

    ColumnMeta<C> getColumnMeta(C columnName);
}
