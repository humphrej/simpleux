package com.specialprojectslab.simpleux.grid;

import org.junit.Test;

import javax.swing.table.TableCellRenderer;

import static com.specialprojectslab.simpleux.grid.ColumnMetaBuilder.newColumn;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class ColumnMetaBuilderTest {


    @Test
    public void shouldBuildObject() throws Exception {

        TableCellRenderer renderer = new TimestampTableCellRenderer();
        ColumnMeta actual = newColumn()
                .id("ID1")
                .minWidth(0)
                .maxWidth(100)
                .tableCellRenderer(renderer)
                .title("TITLE").build();

        assertThat((String)actual.getColumnId(), is("ID1"));
        assertThat(actual.getTitle(), is("TITLE"));
        assertThat(actual.getMinWidth(), is(0));
        assertThat(actual.getMaxWidth(), is(100));
        assertThat(actual.getRenderer(), is(renderer));
    }
}
