package com.specialprojectslab.simpleux.demo;

import com.google.common.base.Preconditions;
import com.google.inject.Inject;
import com.specialprojectslab.simpleux.app.IconRegistry;
import com.specialprojectslab.simpleux.app.SimpleUXComponent;
import com.specialprojectslab.simpleux.grid.ColumnMeta;
import com.specialprojectslab.simpleux.grid.FilterUXStrategies;
import com.specialprojectslab.simpleux.grid.FilterUXTable;
import org.flexdock.docking.DockingConstants;
import org.flexdock.view.View;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.math.BigInteger;
import java.security.SecureRandom;

import static com.specialprojectslab.simpleux.grid.ColumnMetaBuilder.newColumn;

public class FilterTableDemoComponent implements SimpleUXComponent {

    private static final String TITLE="Filter Table Component";
    public static final String PERSISTENT_ID = "FILTERDEMOCOMPONENT";
    private final View view  ;
    private final Icon icon;

    private final FilterUXTable tablePanel;
    private final DataTableModel tableModel ;

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

    @Inject
    public FilterTableDemoComponent(IconRegistry iconRegistry) {
        view = new View(PERSISTENT_ID,TITLE);

        view.addAction(DockingConstants.CLOSE_ACTION);
        view.addAction(DockingConstants.PIN_ACTION);

        tableModel = new DataTableModel(COLUMNS);
        //tablePanel = new FilterUXTable(tableModel,COLUMNS, FilterUXStrategies.nonsenseFilter());
        //tablePanel = new FilterUXTable(tableModel,COLUMNS, FilterUXStrategies.simpleTextBoxFilter());
          tablePanel = new FilterUXTable(tableModel,COLUMNS, FilterUXStrategies.autoFilter());


        view.setContentPane(tablePanel);

        icon = iconRegistry.getIcon("FILTER_DEMO_COMPONENT");
        view.setTabIcon(icon);

    }

    @Override
    public String getName() {
        return TITLE;
    }

    @Override
    public Icon getIcon() {
        return icon;
    }

    @Override
    public Component getComponent() {
        return view;
    }

    @Override
    public String getPersistentId() {
        return PERSISTENT_ID;
    }

    @Override
    public void start() {
    }

    @Override
    public void stop() {
    }



    private static class DataTableModel extends DefaultTableModel {

        private static final int ROWS = 30;

        private final String[][] model ;
        private final ColumnMeta[] columns;

        public DataTableModel(ColumnMeta[] columns) {
            super();
            Preconditions.checkNotNull(columns);
            this.columns = columns ;

            model =new String[ROWS][columns.length];
            SecureRandom random = new SecureRandom();
            for (int j = 0; j < ROWS; j++) {
                for (int i = 0; i < columns.length; i++) {
                    model[j][i] = new BigInteger(130, random).toString(32);
                }
            }
        }

        public int getColumnCount() {
            return columns == null ? 0 : columns.length;
        }

        public int getRowCount() {
            return ROWS;
        }

        public Object getValueAt(int row, int col) {
            return model[row][col];
        }

        @Override
        public String getColumnName(int column) {
            return columns[column].getTitle();
        }
    }
}
