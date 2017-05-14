package io.epiclabs.walldroid.jira;

import android.provider.Settings;
import android.util.Log;
import android.webkit.ConsoleMessage;
import android.webkit.WebChromeClient;

import io.epiclabs.walldroid.R;

import static android.provider.Settings.Global.getString;

/**
 * Created by adrian on 14/05/17.
 */

public class JiraWebChromeClient extends WebChromeClient {
    @Override
    public boolean onConsoleMessage(ConsoleMessage cm) {
        System.out.println(cm.message() + " -- From line "
                + cm.lineNumber() + " of "
                + cm.sourceId() );
        return true;
    }
}
