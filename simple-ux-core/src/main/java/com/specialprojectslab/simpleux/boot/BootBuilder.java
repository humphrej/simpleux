package com.specialprojectslab.simpleux.boot;

import com.google.common.util.concurrent.Service;

import java.util.HashSet;
import java.util.Set;

import static com.specialprojectslab.simpleux.boot.Bootstraps.stage;

public class BootBuilder {

    private final Set<BootStage> stages = new HashSet<BootStage>();

    public BootBuilder add(int priority, Service service) {
        stages.add(stage(priority, service));
        return this;
    }


    public Set<BootStage> build() {
        return stages;
    }
}
