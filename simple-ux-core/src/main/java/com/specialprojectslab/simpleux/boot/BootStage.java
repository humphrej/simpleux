package com.specialprojectslab.simpleux.boot;

import com.google.common.util.concurrent.Service;

public interface BootStage {

    int getPriority();
    Service getService();

}
