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

    private static final int COUNT = 3;

    static {
        // Add some sample items.
        for (int i = 1; i <= COUNT; i++) {
            addItem(createPlugin(i));
        }
    }

    private static void addItem(Plugin item) {
        plugins.add(item);
        pluginsMap.put(item.getAlias(), item);
    }

    private static Plugin createPlugin(int position) {
        return new JiraPlugin("alias " + position, "host" + position + "00", "username", "password", "wallboard id", 123, "effect string", false);
    }

}
