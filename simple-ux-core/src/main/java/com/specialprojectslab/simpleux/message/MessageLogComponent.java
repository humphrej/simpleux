package com.specialprojectslab.simpleux.message;

import com.google.inject.Inject;
import com.specialprojectslab.simpleux.app.IconRegistry;
import com.specialprojectslab.simpleux.app.SimpleUXComponent;
import com.specialprojectslab.simpleux.grid.ColumnMeta;
import com.specialprojectslab.simpleux.grid.SimpleUXTable;
import com.specialprojectslab.simpleux.grid.TimestampTableCellRenderer;
import org.flexdock.docking.DockingConstants;
import org.flexdock.view.View;
import org.jdesktop.swingx.JXStatusBar;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.specialprojectslab.simpleux.grid.ColumnMetaBuilder.newColumn;

public class MessageLogComponent implements SimpleUXComponent, MessageLog {

    public static final String PERSISTENT_ID = "MESSAGELOG";
    private static final String TITLE = "Message Log";
    private final View view;
    private final Icon icon;
    private final List<Message> messages = new ArrayList<Message>();
    private final SimpleUXTable table;
    private final MessageLogTableModel tm;


    @Inject
    public MessageLogComponent(IconRegistry iconRegistry) {
        view = new View(PERSISTENT_ID, TITLE);

        view.addAction(DockingConstants.CLOSE_ACTION);
        view.addAction(DockingConstants.PIN_ACTION);

        JPanel panel = new JPanel(new BorderLayout());
        tm = new MessageLogTableModel();
        table = new SimpleUXTable(tm, COLUMNS);
        table.setColumnControlVisible(true);

        panel.add(new JScrollPane(table));
        view.setContentPane(panel);

        icon = iconRegistry.getIcon("MESSAGE_LOG");
        view.setTabIcon(icon);

        this.onMessage(new Date(), "1", "2");
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

    private final ColumnMeta[] COLUMNS = {
            newColumn()
                    .title("Timestamp")
                    .minWidth(150)
                    .maxWidth(150)
                    .tableCellRenderer(new TimestampTableCellRenderer()).build(),
            newColumn()
                    .title("Component").
                    minWidth(200).build(),
            newColumn()
                    .title("Message").build()
    };

    @Override
    public void onMessage(final Date timestamp, final String component, final String message) {
        //tm.fireTableRowsInserted(messages.size()-1, messages.size()-1);
        SwingUtilities.invokeLater(new Runnable() {


            @Override
            public void run() {
                messages.add(new Message(timestamp, component, message));
                int index = messages.size() - 1;
                tm.fireTableRowsInserted(index, index);
            }
        });
    }

    private class MessageLogTableModel extends DefaultTableModel {

        @Override
        public Object getValueAt(int row, int column) {
            switch (column) {
                case 0:
                    return messages.get(row).getTimestamp();
                case 1:
                    return messages.get(row).getComponent();
                case 2:
                    return messages.get(row).getMessage();
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
            return messages.size();
        }

        @Override
        public String getColumnName(int column) {
            return COLUMNS[column].getTitle();
        }
    }
}
