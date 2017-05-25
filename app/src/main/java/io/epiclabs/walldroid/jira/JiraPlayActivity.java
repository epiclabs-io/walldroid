package io.epiclabs.walldroid.jira;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;

import io.epiclabs.walldroid.R;

/**
 * Created by adrian on 31/03/17.
 */
public class JiraPlayActivity extends AppCompatActivity  {
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_webview);

        View decorView = getWindow().getDecorView();
        // hide the status bar
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        Intent intent = getIntent();
        JiraService service = new JiraService(
                "MyJiraActivity1",
                intent.getStringExtra(getString(R.string.JIRA_HOST)),
                intent.getStringExtra(getString(R.string.JIRA_USERNAME)),
                intent.getStringExtra(getString(R.string.JIRA_PASSWORD)),
                intent.getStringExtra(getString(R.string.JIRA_WALLBOARD_ID)),
                Integer.parseInt(sharedPref.getString("cycle_period", "10")),
                sharedPref.getString("transition_effect", "scrollLeft"),
                sharedPref.getBoolean("random", false)
        );

        webView = (WebView) findViewById(R.id.webview);

        service.initWebView(this, webView);
    }
}
