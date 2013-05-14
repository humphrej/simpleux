package com.specialprojectslab.simpleux.about;

import com.google.common.collect.Maps;
import com.specialprojectslab.simpleux.grid.ColumnMeta;
import com.specialprojectslab.simpleux.grid.FilterUXTable;
import com.specialprojectslab.simpleux.grid.SimpleUXTable;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static com.specialprojectslab.simpleux.grid.ColumnMetaBuilder.newColumn;
import static com.specialprojectslab.simpleux.grid.FilterUXStrategies.simpleTextBoxFilter;


public class SystemPropertiesPanel extends JPanel
{

    private final Map<String,String> properties ;
    private final List<String> keys;
    private final PropertiesTableModel tm;
    private final FilterUXTable table;


    public SystemPropertiesPanel() {
        super(new BorderLayout());

        properties = Maps.fromProperties(System.getProperties());
        keys = new ArrayList<String>(properties.keySet());
        Collections.sort(keys);


        tm = new PropertiesTableModel();
        table = new FilterUXTable(tm, COLUMNS, simpleTextBoxFilter());
        //table.gett.setColumnControlVisible(true);

        add(new JScrollPane(table));
    }


    private final ColumnMeta[] COLUMNS = {
            newColumn().title("Name").minWidth(200).maxWidth(200).build(),
            newColumn().title("Value").build()
    };


    private class PropertiesTableModel extends DefaultTableModel
    {

        @Override
        public Object getValueAt(int row, int column) {
            switch (column) {
                case 0:
                    return keys.get(row);
                case 1:
                    String k = keys.get(row);
                    return properties.get(k);
                default:
                    return null;
            }
        }

        @Override
        public int getColumnCount() {
            return COLUMNS.length;
        }

        @Override
        public int getRowCount() {
            return keys.size();
        }

        @Override
        public String getColumnName(int column) {
            return COLUMNS[column].getTitle();
        }
    }


}
