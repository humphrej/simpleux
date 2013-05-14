package com.specialprojectslab.simpleux.app;

import javax.swing.*;
import java.awt.*;

public interface SimpleUXComponent  {
    void start();
    void stop();

    String getName();
    Icon getIcon();

    Component getComponent();

    String getPersistentId();
}
