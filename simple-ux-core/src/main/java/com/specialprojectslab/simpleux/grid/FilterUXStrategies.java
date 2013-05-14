package com.specialprojectslab.simpleux.grid;

import javax.swing.*;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.PatternSyntaxException;

public class FilterUXStrategies {

    private FilterUXStrategies() {
    }

    public static FilterUXStrategy simpleTextBoxFilter() {


        return new FilterUXStrategy() {
            @Override
            public JComponent makeFilter(SimpleUXTable dataTable, JScrollPane dataTableScrollPane) {
                final JTextField lookupField = new JTextField();
                final TableRowSorter<TableModel> sorter =
                        new TableRowSorter<TableModel>(dataTable.getModel());

                lookupField.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {
                        try {
                            String lookupFieldText = lookupField.getText();
                            if (lookupFieldText.length() == 0)
                                sorter.setRowFilter(null);
                            else
                                sorter.setRowFilter(RowFilter.regexFilter(lookupFieldText));
                        } catch (PatternSyntaxException pse) {
                            System.err.println("Incorrect pattern syntax");
                        }
                    }
                });
                dataTable.setRowSorter(sorter);
                return lookupField;
            }
        };

    }

    public static FilterUXStrategy autoFilter() {
        return new AutoFilterUXStrategy() ;
    }

    public static FilterUXStrategy nonsenseFilter() {
        return new FilterUXStrategy() {
            @Override
            public JComponent makeFilter(SimpleUXTable dataTable, JScrollPane dataTableScrollPane) {
                return new JButton("Nonsense Filter!");
            }
        };
    }


}
