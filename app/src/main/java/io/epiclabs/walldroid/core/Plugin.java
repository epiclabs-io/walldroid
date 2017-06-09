package io.epiclabs.walldroid.core;

import android.view.View;

/**
 * Created by adrian on 14/05/17.
 */

public abstract class Plugin {
    protected String alias;
    protected String host;

    public String getAlias() {
        return null;
    }

    public void setAlias(String alias) {}

    public String getHost() {
        return null;
    }

    public void setHost(String host) {}

    abstract public void addService(View view);

    abstract public void editService(View view);

    abstract public void playService(View view);
}
