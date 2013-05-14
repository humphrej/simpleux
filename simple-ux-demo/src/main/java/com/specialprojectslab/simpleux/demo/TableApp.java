package com.specialprojectslab.simpleux.demo;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Table;
import com.google.common.collect.TreeBasedTable;
import com.specialprojectslab.simpleux.util.MultiKey;

import javax.swing.tree.DefaultMutableTreeNode;
import java.util.Set;

public class TableApp {

    public static void main(String[] args )    {

        Table<Integer,String, Object> table = TreeBasedTable.create();

        table.put(0,"City","Belfast");
        table.put(0,"Store","Tesco");
        table.put(0,"Product","Spanners");
        table.put(0,"Quantity",3000);
        table.put(1,"City", "London");
        table.put(1,"Store","B&Q");
        table.put(1,"Product","Sausages");
        table.put(1,"Quantity",2);
        table.put(2,"City", "London");
        table.put(2,"Store","Dunhill");
        table.put(2,"Product","Socks");
        table.put(2,"Quantity",99);


        String[] columnKeys = new String[] {"City","Store","Product"};


        for( Integer rowKey : table.rowKeySet() ) {
            Object[] values = new Object[columnKeys.length];
            for (int i = 0; i < columnKeys.length; i++) {
                values[i] = table.get(rowKey,columnKeys[i]);
            }
            System.out.println("Key is :"+ new MultiKey<Object>(values));

        }

        System.out.println(ImmutableSet.copyOf(table.column("City").values()));
        System.out.println(ImmutableSet.copyOf(table.column("Store").values()));
        System.out.println(ImmutableSet.copyOf(table.column("Product").values()));

        DefaultMutableTreeNode root = new DefaultMutableTreeNode("ROOT");
        root.add(new DefaultMutableTreeNode("CHILD1"));
        root.add(new DefaultMutableTreeNode("CHILD2"));


        System.out.println(root.getChildAt(0));
        System.out.println(root.getChildAt(1));
        System.out.println(root);

    }
}
