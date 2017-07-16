package io.epiclabs.walldroid.jira;

import android.webkit.ConsoleMessage;
import android.webkit.WebChromeClient;

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
