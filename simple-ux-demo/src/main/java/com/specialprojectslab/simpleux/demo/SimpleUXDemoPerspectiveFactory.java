package com.specialprojectslab.simpleux.demo;

import com.specialprojectslab.simpleux.message.MessageLogComponent;
import org.flexdock.docking.DockingConstants;
import org.flexdock.perspective.LayoutSequence;
import org.flexdock.perspective.Perspective;
import org.flexdock.perspective.PerspectiveFactory;

public class SimpleUXDemoPerspectiveFactory implements PerspectiveFactory, DockingConstants {


    public static final String PERSISTENT_ID = "SimpleUXDefaultPerspective";

    @Override
    public Perspective getPerspective(String persistentId) {
        if (persistentId != PERSISTENT_ID)
            throw new IllegalArgumentException("Cannot handle id:" + persistentId);

        Perspective perspective = new Perspective(PERSISTENT_ID, PERSISTENT_ID);
        LayoutSequence sequence = perspective.getInitialSequence(true);

        sequence.add(FilterTableDemoComponent.PERSISTENT_ID);
        sequence.add(MessageLogComponent.PERSISTENT_ID, FilterTableDemoComponent.PERSISTENT_ID, EAST_REGION, .3f);

        return perspective;
    }
}
