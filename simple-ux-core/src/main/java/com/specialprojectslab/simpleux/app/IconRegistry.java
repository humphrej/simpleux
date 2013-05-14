package com.specialprojectslab.simpleux.app;

import com.google.common.util.concurrent.AbstractService;
import com.google.inject.Inject;
import org.flexdock.util.ResourceManager;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

public class IconRegistry extends AbstractService {

    private final Map<String,String> resourceMap ;
    private final Map<String,ImageIcon> iconMap = new HashMap<String,ImageIcon>();

    @Inject
    public IconRegistry(Map<String,String> initialBindings) {
        resourceMap = new HashMap<String,String>(initialBindings);
    }


    public void registerIcon(String name, String fileResource) {
        resourceMap.put(name, fileResource);

    }

    public Icon getIcon(String name) {
        ImageIcon icon = iconMap.get(name);
        if ( icon != null )
            return icon ;

        String resource = resourceMap.get(name);
        if ( resource == null )
            return null ;

        icon = ResourceManager.createIcon(resource);

        iconMap.put(name, icon);
        return icon;
    }


    @Override
    protected void doStart() {
        for( Map.Entry<String,String> e : resourceMap.entrySet()) {
            iconMap.put( e.getKey(), ResourceManager.createIcon( e.getValue()));
        }
    }

    @Override
    protected void doStop() {
    }

}
