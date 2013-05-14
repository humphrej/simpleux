package com.specialprojectslab.simpleux.app;

import com.google.inject.Inject;
import org.flexdock.docking.DockableFactory;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class SimpleUXDockableFactory extends DockableFactory.Stub{

    private final Map<String,SimpleUXComponent> componentMap = new HashMap<String,SimpleUXComponent>();

    @Inject
    public SimpleUXDockableFactory(Set<SimpleUXComponent> components) {

        for( SimpleUXComponent c : components) {
            componentMap.put(c.getName(),c);
        }
    }

    @Override
    public Component getDockableComponent(String dockableId) {
        SimpleUXComponent uxComponent = componentMap.get(dockableId);
        if (uxComponent == null)
            return null;
        else
            return uxComponent.getComponent();
    }
}
