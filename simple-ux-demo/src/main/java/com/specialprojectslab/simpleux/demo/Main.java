package com.specialprojectslab.simpleux.demo;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.jgoodies.looks.plastic.Plastic3DLookAndFeel;
import com.specialprojectslab.simpleux.app.SimpleUXCoreModule;
import com.specialprojectslab.simpleux.app.UXContainer;
import com.specialprojectslab.simpleux.boot.Bootstrapper;
import com.specialprojectslab.simpleux.message.MessageLog;
import com.specialprojectslab.simpleux.message.MessageLogComponent;
import org.flexdock.docking.DockingManager;
import org.flexdock.util.SwingUtility;
import org.jdesktop.swingx.JXLoginPane;
import org.jdesktop.swingx.auth.LoginService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.util.Date;

public class Main {

    private static  Logger log = LoggerFactory.getLogger(Main.class);

    private static class LaFRunnable implements Runnable{
        @Override
        public void run() {
            try {
                UIManager.setLookAndFeel(new Plastic3DLookAndFeel());
            } catch (UnsupportedLookAndFeelException e) {
                log.warn("Unable to set look and feel!");
            }
        }
    }

    private static class LoginRunnable implements Runnable {

        @Override
        public void run() {
            final JXLoginPane panel = new JXLoginPane(new LoginService() {
                public boolean authenticate(String name, char[] password,
                                            String server) throws Exception {
                    // perform authentication and return true on success.
                    if ( name.equals("guest"))
                        return true;
                    else
                        return false;
                }
            });


            final JXLoginPane.Status status =  JXLoginPane.showLoginDialog(null,panel);
            if ( status != JXLoginPane.Status.SUCCEEDED) {
                log.warn("Aborting - unable to login");
                System.exit(1);
                return;
            }
            String user = panel.getUserName();
            SwingUtilities.invokeLater(new RunGUI(user));

        }
    }

    private static class RunGUI implements Runnable{

        private final String user;

        public RunGUI(String user) {
            this.user = user ; 
        }

        @Override
        public void run() {

            Injector injector = Guice.createInjector(new SimpleUXCoreModule(), new SimpleUXDemoModule(user));

            UXContainer uxContainer = injector.getInstance(UXContainer.class);

            uxContainer.getFrame().setTitle("Test Application");
            uxContainer.getFrame().setSize(800, 800);
            SwingUtility.centerOnScreen(uxContainer.getFrame());

        /* TEST DATA */
            MessageLog log = (MessageLog) uxContainer.getComponent(MessageLogComponent.PERSISTENT_ID);
            log.onMessage(new Date(), "FOO", "MESSAGE1");
            log.onMessage(new Date(), "FOO", "MESSAGE2");
            log.onMessage(new Date(), "FOO", "MESSAGE3");
        /* TEST DATA */


            DockingManager.restoreLayout();

            uxContainer.getFrame().setVisible(true);



            /* alternative

            Bootstrapper boot = injector.getInstance(Bootstrapper.class);
            boot.boot();

            means that modules need to register their startup order with the bootstrapper
            */

        }
    }


    public static void main(String[] args) throws Exception {
        SwingUtilities.invokeLater(new LaFRunnable());
        SwingUtilities.invokeLater(new LoginRunnable());
    }

}
