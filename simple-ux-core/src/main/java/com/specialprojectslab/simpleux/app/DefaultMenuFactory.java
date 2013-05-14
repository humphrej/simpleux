package com.specialprojectslab.simpleux.app;

import com.google.inject.Inject;
import com.specialprojectslab.simpleux.about.AboutDialog;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.Set;

public class DefaultMenuFactory implements MenuFactory {


    private final Set<SimpleUXComponent> components;
    private final UXContainer container ;
    public static final String MENU_EXIT = "MENU_EXIT";
    private static final String MENU_ABOUT = "MENU_ABOUT";

    @Inject
    public DefaultMenuFactory(UXContainer container, Set<SimpleUXComponent> components) {
        this.components = components ;
        this.container = container ;
    }


    public JMenuBar createMenuBar() {

        JMenuBar menuBar = new JMenuBar();


        // File Menu
        JMenu fileMenu = new JMenu("File");
        fileMenu.setMnemonic(KeyEvent.VK_F);
        menuBar.add(fileMenu);

        JMenuItem exitItem  = new JMenuItem("Exit");
        exitItem.setMnemonic(KeyEvent.VK_X);
        exitItem.setActionCommand(MENU_EXIT);
        exitItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                container.shutdown();
            }
        });

        fileMenu.add(exitItem);

        // Window Menu
        JMenu windowMenu = new JMenu("Window");
        windowMenu.setMnemonic(KeyEvent.VK_W);
        menuBar.add(windowMenu);


        for( final SimpleUXComponent c : components ) {
            JMenuItem menuItem  = new JMenuItem(c.getName(),c.getIcon());
            menuItem.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    container.showComponent(c.getPersistentId());
                }
            });
            windowMenu.add(menuItem);
        }

        // Help Menu
        JMenu helpMenu = new JMenu("Help");
        windowMenu.setMnemonic(KeyEvent.VK_H);
        menuBar.add(helpMenu);

        JMenuItem aboutItem  = new JMenuItem("About");
        aboutItem.setMnemonic(KeyEvent.VK_A);
        aboutItem.setActionCommand(MENU_ABOUT);
        aboutItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                container.showAbout();
            }
        });
        helpMenu.add(aboutItem);



        return menuBar;
    }
}
