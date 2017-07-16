package io.epiclabs.walldroid.core;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.orm.SugarRecord;

/**
 * Created by adrian on 14/05/17.
 */
public abstract class Plugin extends SugarRecord {
    protected String alias;
    protected String host;
    protected PluginManager.PluginType type;

    public PluginManager.PluginType getType() { return type; }
    public long setType(PluginManager.PluginType type) {
        this.type = type;
        return save();
    }

    public String getAlias() {
        return alias;
    }
    public long setAlias(String alias) {
        this.alias = alias;
        return save();
    }

    public String getHost() { return host; }
    public long setHost(String host) {
        this.host = host;
        return save();
    }

    abstract public void setView(Context context, ViewGroup layout, Activity activity);

    abstract public void addService(View view);

    abstract public void editService(View view);

    abstract public void playService(View view, Activity activity);

}
