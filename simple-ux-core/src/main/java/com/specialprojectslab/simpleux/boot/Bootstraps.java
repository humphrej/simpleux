package com.specialprojectslab.simpleux.boot;


import com.google.common.util.concurrent.Service;

public class Bootstraps {

    private Bootstraps() {}

    public static BootStage stage(int priority, Service service) {
        return new BootServiceAdapter(priority,service);
    }

    private static class BootServiceAdapter  implements BootStage
    {
        private final int priority;
        private final Service service;

        private BootServiceAdapter(int priority, Service service) {
            this.priority = priority;
            this.service = service;
        }

        public int getPriority() {
            return priority;
        }

        public Service getService() {
            return service;
        }
    }
}
