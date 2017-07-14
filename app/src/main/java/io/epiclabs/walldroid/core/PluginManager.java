package io.epiclabs.walldroid.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.epiclabs.walldroid.core.Plugin;
import io.epiclabs.walldroid.jira.JiraPlugin;

public class PluginManager {
    private static long idGenerator = 1;
    public static final List<Plugin> plugins = new ArrayList<Plugin>();

    public static void newPlugin(Plugin plugin) {
        plugin.id = idGenerator;
        idGenerator++;
        plugins.add(plugin);
    }

    public static Plugin get(long id) {
        for (Plugin p : plugins) {
            if(p.id == id) {
                return p;
            }
        }
        return null;
    }
}
