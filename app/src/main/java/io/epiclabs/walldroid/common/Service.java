package io.epiclabs.walldroid.common;

import android.view.View;

/**
 * Created by adrian on 14/05/17.
 */

public abstract class Service {
    protected String alias;
    protected String host;

    abstract public String getAlias();

    abstract public void setAlias(String alias);

    abstract public String getHost();

    abstract public void setHost(String host);

    abstract public void addService(View view);

    abstract public void editService(View view);

    abstract public void playService(View view);
}
