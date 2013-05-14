package com.specialprojectslab.simpleux.app;

import org.flexdock.docking.DockingConstants;
import org.flexdock.perspective.LayoutSequence;
import org.flexdock.perspective.Perspective;
import org.flexdock.perspective.PerspectiveFactory;

import java.util.Set;

public class TabbedPerspectiveFactory implements PerspectiveFactory, DockingConstants {
    private static final String DEFAULT_PERSPECTIVE_ID = "DEFAULT";
    private final Set<SimpleUXComponent> components;


    public TabbedPerspectiveFactory(Set<SimpleUXComponent> components) {
        this.components = components ;
    }

    @Override
    public Perspective getPerspective(String persistentId) {
        Perspective perspective = new Perspective(DEFAULT_PERSPECTIVE_ID, DEFAULT_PERSPECTIVE_ID);
        LayoutSequence sequence = perspective.getInitialSequence(true);

        for( SimpleUXComponent c : components) {
            sequence.add(c.getPersistentId(), CENTER_REGION);
        }

        return perspective;
    }}
