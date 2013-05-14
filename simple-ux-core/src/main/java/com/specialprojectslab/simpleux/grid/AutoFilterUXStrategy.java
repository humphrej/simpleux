package com.specialprojectslab.simpleux.grid;

import org.jdesktop.swingx.JXTable;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.util.ArrayList;
import java.util.regex.PatternSyntaxException;

public class AutoFilterUXStrategy implements FilterUXStrategy {
    @Override
    public JComponent makeFilter(SimpleUXTable dataTable, JScrollPane dataTableScrollPane) {

        // set up sorter and add to dataTable
        TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(dataTable.getModel());
        dataTable.setRowSorter(sorter);
        dataTable.setColumnControlVisible(true);


        // create headerTable using the dataTable columns
        HeaderJXTable headerTable =  new HeaderJXTable(sorter, dataTable.getColumnMetas());
        GridUtilities.synchronizeColumns(dataTable, headerTable);

        JScrollPane headerScrollPane = new JScrollPane(headerTable,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);

        // synchronise the horizontal scroll bar
        headerScrollPane.getHorizontalScrollBar().setModel(dataTableScrollPane.getHorizontalScrollBar().getModel());

        headerScrollPane.setPreferredSize(new Dimension(dataTable.getWidth(),
                (int) headerTable.getPreferredSize().getHeight() + 40));


        headerScrollPane.setCorner(ScrollPaneConstants.UPPER_RIGHT_CORNER, new JButton());
        headerScrollPane.setViewportView(headerTable);

        return headerScrollPane;
    }



    static class HeaderJXTable extends JXTable
    {
        private final TableModel headerModel ;
        private final TableRowSorter<TableModel> sorter;
        private final ColumnMeta[] columnMetas;

        private HeaderJXTable(TableRowSorter<TableModel> sorter, ColumnMeta[] columnMetas) {
            this.columnMetas = columnMetas;
            this.sorter = sorter ;

            headerModel = new FilterTableModel();
            setModel(headerModel);

            setHorizontalScrollEnabled(true);
            setColumnControlVisible(false);

            getTableHeader().setReorderingAllowed(false);
            getTableHeader().setResizingAllowed(false);
            headerModel.addTableModelListener(new TableModelListener() {
                @Override
                public void tableChanged(TableModelEvent e) {
                    System.out.println("Row " + e.getLastRow() + " Column " + e.getColumn());
                    updateRowFilter();
                }
            });


        }

        private void updateRowFilter() {

            try {
                java.util.List filters = new ArrayList();
                for (int i = 0; i < headerModel.getColumnCount(); i++) {
                    String val = String.valueOf(headerModel.getValueAt(0, i));
                    if (val.length() == 0)
                        continue;
                    if (val.equals("null"))
                        continue;
                    System.out.println("Adding filter val:" + val + " for column " + i);
                    filters.add(RowFilter.regexFilter(val,i));
                }
                sorter.setRowFilter(RowFilter.andFilter(filters));

            } catch (PatternSyntaxException pse) {
                System.err.println("Incorrect pattern syntax");
            }
        }






    class FilterTableModel extends DefaultTableModel {

        private Object[] filters ;


        public FilterTableModel() {
            filters = new Object[columnMetas.length];
        }

        @Override
        public int getRowCount() {
            return 1;
        }

        @Override
        public int getColumnCount() {
            return columnMetas == null ? 0 : columnMetas.length;
        }

        @Override
        public String getColumnName(int columnIndex) {
            return columnMetas[columnIndex].getTitle();
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            return  columnIndex < filters.length ? filters[columnIndex] : null;
        }

        @Override
        public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
            filters[columnIndex] = aValue;
            fireTableCellUpdated(rowIndex, columnIndex);
        }
    }
    }
}
