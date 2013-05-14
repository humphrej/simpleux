package com.specialprojectslab.simpleux.demo;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.multibindings.MapBinder;
import com.google.inject.multibindings.Multibinder;
import com.google.inject.name.Names;
import com.specialprojectslab.simpleux.app.DefaultMenuFactory;
import com.specialprojectslab.simpleux.app.IconRegistry;
import com.specialprojectslab.simpleux.app.MenuFactory;
import com.specialprojectslab.simpleux.app.SimpleUXComponent;
import org.flexdock.perspective.PerspectiveFactory;

public class SimpleUXDemoModule extends AbstractModule {

    private final String user;

    public SimpleUXDemoModule(String user) {
        this.user = user ;
    }

    @Override
    protected void configure() {
        Multibinder<SimpleUXComponent> uriBinder = Multibinder.newSetBinder(binder(), SimpleUXComponent.class);
        uriBinder.addBinding().to(FilterTableDemoComponent.class).in(Singleton.class);
        uriBinder.addBinding().to(TreeTableDemoComponent.class).in(Singleton.class);

        //this deals with gui persistence
        bind(PerspectiveFactory.class).to(SimpleUXDemoPerspectiveFactory.class).in(Singleton.class);
        bind(String.class).annotatedWith(Names.named("perspective-file")).toInstance("demo.perspective");
        bind(String.class).annotatedWith(Names.named("about-file")).toInstance("/com/specialprojectslab/simpleux/demo/SimpleSign.jpg");
        bind(String.class).annotatedWith(Names.named("login-user")).toInstance(user);

        //this builds the menu of the app
        bind(MenuFactory.class).to(DefaultMenuFactory.class).in(Singleton.class);

        MapBinder<String,String> iconBinder = MapBinder.newMapBinder(binder(), String.class, String.class);
        iconBinder.addBinding("DEMO_COMPONENT").toInstance("com/famfamfam/silk/application_key.png");
        iconBinder.addBinding("FILTER_DEMO_COMPONENT").toInstance("com/famfamfam/silk/application_key.png");
        iconBinder.addBinding("TREE_DEMO_COMPONENT").toInstance("com/famfamfam/silk/application_key.png");


    }


}
