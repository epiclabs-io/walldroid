package io.epiclabs.walldroid.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.epiclabs.walldroid.core.Plugin;
import io.epiclabs.walldroid.jira.JiraPlugin;

public class PluginManager {

    public static final List<Plugin> plugins = new ArrayList<Plugin>();
    public static final Map<String, Plugin> pluginsMap = new HashMap<String, Plugin>();

    public static void newPlugin(Plugin plugin) {
        addItem(plugin);
    }

    public static void addItem(Plugin item) {
        plugins.add(item);
        pluginsMap.put(item.getAlias(), item);
    }


}
