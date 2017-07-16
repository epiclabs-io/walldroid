package io.epiclabs.walldroid.core;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import io.epiclabs.walldroid.jira.JiraPlugin;

public class PluginManager {
    public enum PluginType {
        JIRA_CLOUD,
    }

    public static final List<Plugin> plugins = new ArrayList<Plugin>();

    public static void newPlugin(JiraPlugin plugin) {
        long a = plugin.save();
        plugins.add(plugin);
    }

    public static Plugin get(PluginType type, long id) {
        if (type == PluginType.JIRA_CLOUD)
            return JiraPlugin.findById(JiraPlugin.class, id);
        else
            return null;
    }

    public static void loadPlugins() {
        for (PluginType type : PluginType.values()) {
            if (type == PluginType.JIRA_CLOUD) {
                loadPluginByType(JiraPlugin.class);
            }
       }
    }

    private static <T extends Plugin> void loadPluginByType(Class<T> tClass) {
        Iterator<T> iterator = T.findAll(tClass);
        while (iterator.hasNext()) {
            plugins.add(iterator.next());
        }
    }
}
