package com.specialprojectslab.simpleux.app;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.specialprojectslab.simpleux.about.AboutDialog;
//import com.specialprojectslab.simpleux.demo.SimpleUXDemoPerspectiveFactory;
import org.flexdock.docking.DockableFactory;
import org.flexdock.docking.DockingConstants;
import org.flexdock.docking.DockingManager;
import org.flexdock.docking.state.PersistenceException;
import org.flexdock.perspective.PerspectiveFactory;
import org.flexdock.perspective.PerspectiveManager;
import org.flexdock.perspective.persist.FilePersistenceHandler;
import org.flexdock.perspective.persist.PersistenceHandler;
import org.flexdock.view.Viewport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class PersistentUXContainer extends JFrame implements DockingConstants, UXContainer {

    private final DockableFactory dockableFactory;
    private final PerspectiveFactory perspectiveFactory;
    private final String perspectiveFile;
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final Map<String, SimpleUXComponent> components = new HashMap<String, SimpleUXComponent>();
    private static final int NORMAL_TERMINATION = 0;
    private final AboutDialog aboutDialog ;

    @Inject
    public PersistentUXContainer(MenuFactory menuFactory,
                                 Set<SimpleUXComponent> components,
                                 DockableFactory dockableFactory,
                                 PerspectiveFactory perspectiveFactory,
                                 @Named("perspective-file") String perspectiveFile,
                                 @Named("about-file") String aboutFile) {
        super();
        this.dockableFactory = dockableFactory;
        this.perspectiveFactory = perspectiveFactory;
        this.perspectiveFile = perspectiveFile;

        this.aboutDialog = new AboutDialog(this, aboutFile);

        for( SimpleUXComponent c : components ) {
            this.components.put(c.getPersistentId(),c);
        }

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                shutdown();
            }
        });

        getContentPane().setLayout(new BorderLayout());

        Viewport viewport = new Viewport();
        getContentPane().add(viewport, BorderLayout.CENTER);

        JMenuBar menuBar = menuFactory.createMenuBar();
        if ( menuBar != null )
            this.setJMenuBar(menuBar);

        configureDocking();
    }


    private void configureDocking() {
        // setup the DockingManager to work with our application
        DockingManager.setDockableFactory(dockableFactory);
        DockingManager.setFloatingEnabled(true);

        // configureDocking the perspective manager
        PerspectiveManager.setFactory(perspectiveFactory);
        PerspectiveManager.setRestoreFloatingOnLoad(true);
        PerspectiveManager mgr = PerspectiveManager.getInstance();

        //TODO FIX this
        //mgr.setCurrentPerspective(SimpleUXDemoPerspectiveFactory.PERSISTENT_ID, true);


        // load any previously persisted layouts
        PersistenceHandler persister = FilePersistenceHandler.createDefault(perspectiveFile);
        PerspectiveManager.setPersistenceHandler(persister);
        try {
            boolean result = DockingManager.loadLayoutModel(true);
            log.info("Result of loading layout is " + result);
        } catch (IOException e) {
            log.warn("Problem loading perspectives");
        } catch (PersistenceException e) {
            log.warn("Problem loading perspectives");
        }
        // remember to store on shutdown
        DockingManager.setAutoPersist(true);

    }

    public void setPerspective(String perspectiveId) {
        PerspectiveManager.getInstance().setCurrentPerspective(perspectiveId, true);
    }


    @Override
    public JFrame getFrame() {
        return this;
    }

    @Override
    public void shutdown() {
        for (SimpleUXComponent c : components.values()) {
            log.info("Commanding component " + c.getName() + " to shutdown");
            c.stop();
        }
        System.exit(NORMAL_TERMINATION);
    }

    @Override
    public void showComponent(String persistentId) {
        DockingManager.display(persistentId);
    }

    @Override
    public void showAbout() {
        aboutDialog.setVisible(true);
    }

    @Override
    public SimpleUXComponent getComponent(String persistentId) {
        return components.get(persistentId);
    }


}
