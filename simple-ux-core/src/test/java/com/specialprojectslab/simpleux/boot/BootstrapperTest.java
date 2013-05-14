package com.specialprojectslab.simpleux.boot;

import com.google.common.util.concurrent.AbstractService;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.common.util.concurrent.Service;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.multibindings.Multibinder;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.HashSet;
import java.util.Set;

import static com.google.common.util.concurrent.MoreExecutors.sameThreadExecutor;
import static com.specialprojectslab.simpleux.boot.Bootstraps.stage;
import static org.mockito.Mockito.*;

public class BootstrapperTest {


    @Test
    public void shouldStartAndStopInTheCorrectOrder() throws Exception {

        Service myService = new MyService();


        Service.Listener listener = mock(Service.Listener.class);

        myService.addListener(listener, sameThreadExecutor());

        Bootstrapper bs = new Bootstrapper(new BootBuilder().add(44, myService).build());
        bs.boot();

        verify(listener).starting();

        reset(listener);

        bs.shutdown();

        verify(listener).stopping(Mockito.any(Service.State.class));
    }


    private static class MyService extends AbstractService {

        @Override
        protected void doStart() {
        }

        @Override
        protected void doStop() {
        }
    }
}
