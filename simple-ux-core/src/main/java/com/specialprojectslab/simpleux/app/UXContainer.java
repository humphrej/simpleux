package com.specialprojectslab.simpleux.app;

import javax.swing.*;

public interface UXContainer {


    JFrame getFrame();

    void shutdown();

    void showComponent(String persistentId);

    void showAbout();

    SimpleUXComponent getComponent(String persistentId);
}
