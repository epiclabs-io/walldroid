package io.epiclabs.walldroid.core;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by adrian on 14/05/17.
 */

public abstract class Plugin {
    public Long id;
    protected String alias;
    protected String host;
    protected String type;

    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }

    public String getAlias() {
        return alias;
    }
    public void setAlias(String alias) { this.alias = alias; }

    public String getHost() { return host; }
    public void setHost(String host) { this.host = host; }

    abstract public void setView(Context context, ViewGroup layout, Activity activity);

    abstract public void addService(View view);

    abstract public void editService(View view);

    abstract public void playService(View view);

}
