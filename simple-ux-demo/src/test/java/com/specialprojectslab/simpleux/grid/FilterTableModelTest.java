package com.specialprojectslab.simpleux.grid;

import org.junit.Test;

import static com.specialprojectslab.simpleux.grid.ColumnMetaBuilder.newColumn;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class FilterTableModelTest {

    private final ColumnMeta[] COLUMNS = {
            newColumn()
                    .title("AA")
                    .build(),
            newColumn()
                    .title("BB")
                    .build(),
            newColumn()
                    .title("CC")
                    .build(),
            newColumn()
                    .title("DD")
                    .build()
    };

    @Test
    public void shouldGetValueAt() throws Exception {
/*
        AutoFilterUXStrategy.FilterTableModel ut = new AutoFilterUXStrategy.FilterTableModel(COLUMNS);

        assertThat(ut.getColumnCount(), is(4));

        assertThat(ut.getColumnName(0), is(COLUMNS[0].getTitle()));

        assertThat(ut.getValueAt(0,0), is(nullValue()));

        ut.setValueAt("FILTER", 0,0);

        assertThat((String)ut.getValueAt(0,0), is("FILTER"));*/


    }
}
