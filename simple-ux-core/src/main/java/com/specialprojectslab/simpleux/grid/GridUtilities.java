package com.specialprojectslab.simpleux.grid;

import org.jdesktop.swingx.JXTable;
import org.jdesktop.swingx.event.TableColumnModelExtListener;
import org.jdesktop.swingx.table.TableColumnExt;
import org.jdesktop.swingx.table.TableColumnModelExt;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.TableColumnModelEvent;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.List;

public class GridUtilities {

    public static void synchronizeColumns(final JXTable master, final JXTable... slaves) {
        TableColumnModel cm = master.getColumnModel();
        cm.addColumnModelListener(new ColumnModelSynchronizer(slaves));
    }


    private static class ColumnModelSynchronizer implements TableColumnModelExtListener {

        private final JXTable[] tables;

        private ColumnModelSynchronizer(JXTable[] tables) {
            this.tables = tables;
        }

        @Override
        public void columnAdded(TableColumnModelEvent e) {
            syncColumnOrder(e);
        }

        private void syncColumnOrder(TableColumnModelEvent e) {
            TableColumnModelExt sourceColumnModel = (TableColumnModelExt) e.getSource();

            List<TableColumn> cos =  sourceColumnModel.getColumns(false);
            List<Object> ids = new ArrayList<Object>();
            for( TableColumn tc : cos ){
                ids.add(tc.getIdentifier());
            }

            for( JXTable ot : tables) {
                ot.setColumnSequence(ids.toArray());
            }
        }

        @Override
        public void columnRemoved(TableColumnModelEvent e) {
            syncColumnOrder(e);
        }

        @Override
        public void columnMoved(TableColumnModelEvent e) {
            syncColumnOrder(e);
        }

        @Override
        public void columnMarginChanged(ChangeEvent e) {
        }

        @Override
        public void columnSelectionChanged(ListSelectionEvent e) {
        }

        @Override
        public void columnPropertyChange(PropertyChangeEvent e) {
            TableColumnExt col = (TableColumnExt) e.getSource();
            int sourceColIndex = col.getModelIndex();
            Object sourceColIdentifier = col.getIdentifier();

            if ( e.getPropertyName().equals("visible")) {
                for( JTable ot : tables) {
                    TableColumnModelExt otherModel = (TableColumnModelExt) ot.getColumnModel();
                    if ( sourceColIndex < otherModel.getColumnCount(true))   {
                        TableColumnExt c = (TableColumnExt) otherModel.getColumnExt(sourceColIdentifier);
                        c.setVisible((Boolean) e.getNewValue());
                    }
                }
            } else if (e.getPropertyName().equals("width")) {
                for( JTable ot : tables) {
                    TableColumnModelExt otherModel = (TableColumnModelExt) ot.getColumnModel();
                    int w = (Integer) e.getNewValue();
                    if ( sourceColIndex < otherModel.getColumnCount(true))   {
                        TableColumnExt c = (TableColumnExt) otherModel.getColumnExt(sourceColIdentifier);
                        c.setPreferredWidth(w);
                    }

                }
            }
        }
    }










}
