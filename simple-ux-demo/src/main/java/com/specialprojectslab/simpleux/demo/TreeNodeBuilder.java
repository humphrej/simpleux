package com.specialprojectslab.simpleux.demo;

import com.google.common.collect.Table;
import com.specialprojectslab.simpleux.grid.ColumnMetas;
import com.specialprojectslab.simpleux.util.MultiKey;

import javax.swing.tree.MutableTreeNode;
import java.util.List;

public class TreeNodeBuilder<R,C,V> {

    private final Table<R,C,V> table;

    public TreeNodeBuilder(ColumnMetas<C> columns, Table<R,C,V> table, List<C> columnIndex) {
        this.table = table ;
    }

    public MutableTreeNode build() {


        // sort the table by columnIndices
        // iterate over the table comparing the values
        // add a node as app
        return null ;
    }


    private MultiKey<V> buildKey(Table<R,C,V> table, List<C> columnIndex, R row) {
        V[] values = (V[]) new Object[columnIndex.size()];
        for (int i = 0; i < values.length; i++) {
            values[i] = table.get(row,columnIndex.get(i));

        }
        return new MultiKey<V>(values);
    }

    private int compare(MultiKey<V> key, MultiKey<V> level) {

        for( int i=0;i<level.size();i++) {
            // continue
        }
        return 0;
    }


    private interface Summarizer
    {
        // some columns can be aggregated
        // so you need to return a model of summaries given a aggregation key
    }
}
