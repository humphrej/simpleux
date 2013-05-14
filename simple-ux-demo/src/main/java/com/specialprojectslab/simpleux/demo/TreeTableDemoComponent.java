package com.specialprojectslab.simpleux.demo;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableSortedSet;
import com.google.common.collect.Ordering;
import com.google.inject.Inject;
import com.specialprojectslab.simpleux.app.IconRegistry;
import com.specialprojectslab.simpleux.app.SimpleUXComponent;
import com.specialprojectslab.simpleux.grid.ColumnMeta;
import com.sun.istack.internal.Nullable;
import org.flexdock.docking.DockingConstants;
import org.flexdock.view.View;
import org.jdesktop.swingx.JXTreeTable;
import org.jdesktop.swingx.treetable.DefaultTreeTableModel;
import org.jdesktop.swingx.treetable.TreeTableModel;
import org.jdesktop.swingx.treetable.TreeTableNode;

import javax.swing.*;
import java.awt.*;
import java.util.Collection;
import java.util.Date;

import static com.specialprojectslab.simpleux.grid.ColumnMetaBuilder.newColumn;

public class TreeTableDemoComponent implements SimpleUXComponent {

    private static final String TITLE = "Tree Table Component";
    public static final String PERSISTENT_ID = "TREEDEMOCOMPONENT";
    private final View view;
    private final Icon icon;

    private TreeTableModel treeTableModel = new SalesTreeTableModel();
    private final JXTreeTable treeTable = new JXTreeTable(treeTableModel);


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
    public TreeTableDemoComponent(IconRegistry iconRegistry) {
        view = new View(PERSISTENT_ID, TITLE);                                    

        view.addAction(DockingConstants.CLOSE_ACTION);
        view.addAction(DockingConstants.PIN_ACTION);


        view.setContentPane(new JButton("OK"));

        icon = iconRegistry.getIcon("TREE_DEMO_COMPONENT");
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


    private static class SalesData {
        private final String city;
        private final String store;
        private final String product;
        private final int quantity;
        private final boolean direct;

        private SalesData(String city, String store, String product, int quantity, boolean direct) {
            this.city = city;
            this.store = store;
            this.product = product;
            this.quantity = quantity;
            this.direct = direct;
        }

        public String getCity() {
            return city;
        }

        public String getStore() {
            return store;
        }

        public String getProduct() {
            return product;
        }

        public int getQuantity() {
            return quantity;
        }

        public boolean isDirect() {
            return direct;
        }
    }

    private static SalesData[] SALES_DATA = new SalesData[]{
            new SalesData("New York", "Macys", "Socks", 10000, true),
            new SalesData("New York", "Macys", "Socks", 500, false),
            new SalesData("New York", "Macys", "Suits", 600, true),
            new SalesData("New York", "Macys", "Watches", 100, true),
            new SalesData("Zurich", "Globus", "Socks", 10, true),
            new SalesData("London", "Harrods", "Socks", 99, true),
            new SalesData("London", "Harrods", "Socks", 300, true)
    };



     public static <T> void splitData(Collection<SalesData> salesData ) {

        int[] keys = {0,1,2};

        Function<SalesData,String> cityFunc = new Function<SalesData, String>() {
            @Override
            public String apply(@Nullable SalesData input) {
                return input.getCity();
            }
        };

         Function<SalesData,String> productFunc = new Function<SalesData, String>() {
             @Override
             public String apply(@Nullable SalesData input) {
                 return input.getProduct();
             }
         };


        Ordering<SalesData> upOrdering = Ordering.natural().onResultOf(cityFunc)
                .compound(Ordering.natural().onResultOf(productFunc));

        ImmutableSortedSet<SalesData> upOrderedStages = ImmutableSortedSet.orderedBy(
                upOrdering).addAll(salesData).build();


    }

    private class SalesTreeTableModel extends DefaultTreeTableModel {

        /*
        get n indices to build the index
        sort the input by 0..n-1
        iterate over the table
        for every row add a row hash
        for every group add a group hash
        for every super group add a super group hash
         */




        /**
         * {@inheritDoc}
         */
        @Override
        public Object getChild(Object parent, int index) {
            return null;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public int getChildCount(Object parent) {
            return 0;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Class<?> getColumnClass(int column) {
            switch (column) {
                case 0:
                    return String.class;
                case 1:
                    return Long.class;
                case 2:
                    return Boolean.class;
                case 3:
                    return Date.class;
                default:
                    return super.getColumnClass(column);
            }
        }

        @Override
        public int getColumnCount() {
            return 4;
        }

        @Override
        public String getColumnName(int column) {
            switch (column) {
                case 0:
                    return "Name";
                case 1:
                    return "Size";
                case 2:
                    return "Directory";
                case 3:
                    return "Modification Date";
                default:
                    return super.getColumnName(column);
            }
        }

        @Override
        public Object getValueAt(Object node, int column) {

            return null;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public int getIndexOfChild(Object parent, Object child) {
            return -1;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public TreeTableNode getRoot() {
            return  null;
        }


        /**
         * {@inheritDoc}
         */
        @Override
        public boolean isLeaf(Object node) {
            return true;
        }


    }
}
