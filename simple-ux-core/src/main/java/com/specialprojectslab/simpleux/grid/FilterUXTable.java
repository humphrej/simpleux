package com.specialprojectslab.simpleux.grid;

import org.jdesktop.swingx.JXCollapsiblePane;
import org.jdesktop.swingx.action.BoundAction;
import org.jdesktop.swingx.plaf.UIManagerExt;
import org.jdesktop.swingx.table.ColumnControlButton;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.awt.*;
import java.util.Locale;

public class FilterUXTable extends JPanel {

    private final JXCollapsiblePane collapsiblePane;
    private final MySimpleUXTable table ;
    private final JScrollPane dataTableScrollPane;
    private final JComponent filterComponent ;

    /**
     * Identifier of toggle filter action, used in JXTable's <code>ActionMap</code>
     * .
     */
    public static final String TOGGLE_FILTER_ACTION_COMMAND = ColumnControlButton.COLUMN_CONTROL_MARKER
            + "toggleFilter";


    public FilterUXTable(TableModel tm, ColumnMeta[] columns, FilterUXStrategy filterUXStrategy) {
        super(new BorderLayout());
        this.collapsiblePane = new JXCollapsiblePane();
        UIManagerExt.addResourceBundle(
                "com.specialprojectslab.simpleux.grid.resources.Grid");

        table = new MySimpleUXTable(tm,columns);
        table.setColumnControlVisible(true);

        dataTableScrollPane = new JScrollPane(table);

        filterComponent = filterUXStrategy.makeFilter(table, dataTableScrollPane);
        collapsiblePane.add(filterComponent, BorderLayout.CENTER);

        add(collapsiblePane, BorderLayout.NORTH);
        add(dataTableScrollPane, BorderLayout.CENTER);
    }

    public class MySimpleUXTable extends SimpleUXTable {

        public MySimpleUXTable(TableModel tm, ColumnMeta[] columns) {
            super(tm, columns);
            init();
        }

        private void init() {
            initActionsAndBindings();
            updateLocaleState(getLocale());
        }

        private void initActionsAndBindings() {
            ActionMap map = getActionMap();
            map.put(TOGGLE_FILTER_ACTION_COMMAND, createFilterAction());

        }

        private Action createFilterAction() {
            BoundAction action = new BoundAction(null, TOGGLE_FILTER_ACTION_COMMAND);
            action.registerCallback(this, "toggleFilter");
            return action;
        }

        protected void updateLocaleState(Locale locale) {
            super.updateLocaleState(locale);
            updateLocaleActionState(TOGGLE_FILTER_ACTION_COMMAND, locale);
        }

        public void toggleFilter() {
            collapsiblePane.getActionMap().get(JXCollapsiblePane.TOGGLE_ACTION).actionPerformed(null);
        }

    }


}
