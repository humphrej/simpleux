package com.specialprojectslab.simpleux.grid;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimestampTableCellRenderer extends DefaultTableCellRenderer {


    SimpleDateFormat f = new SimpleDateFormat("yy/dd/MM HH:mm:ss.SSS");

    public Component getTableCellRendererComponent(
            JTable table,
            Object value, boolean isSelected, boolean hasFocus,
            int row, int column) {
        if (value instanceof Date) {
            value = f.format(value);
        }
        return super.getTableCellRendererComponent(table, value, isSelected,
                hasFocus, row, column);
    }
}
