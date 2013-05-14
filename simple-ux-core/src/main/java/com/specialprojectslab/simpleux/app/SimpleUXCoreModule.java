package com.specialprojectslab.simpleux.app;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.multibindings.MapBinder;
import com.google.inject.multibindings.Multibinder;
import com.specialprojectslab.simpleux.message.MessageLogComponent;
import org.flexdock.docking.DockableFactory;

public class SimpleUXCoreModule extends AbstractModule{
    @Override
    protected void configure() {
        bind(UXContainer.class).to(PersistentUXContainer.class).in(Singleton.class);
        bind(DockableFactory.class).to(SimpleUXDockableFactory.class).in(Singleton.class);
        bind(IconRegistry.class);

        MapBinder<String,String> iconBinder = MapBinder.newMapBinder(binder(), String.class, String.class);
        iconBinder.addBinding("MESSAGE_LOG").toInstance("com/famfamfam/silk/application.png");


        Multibinder<SimpleUXComponent> uriBinder = Multibinder.newSetBinder(binder(), SimpleUXComponent.class);
        uriBinder.addBinding().to(MessageLogComponent.class).in(Singleton.class);

    }

    @Singleton
    @Provides
    public Object get() {return null ;}


}
