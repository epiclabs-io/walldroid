package io.epiclabs.walldroid.jira;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import io.epiclabs.walldroid.R;

/**
 * Created by adrian on 14/05/17.
 */

public class JiraEditActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        Intent intent = getIntent();
        JiraPlugin service = new JiraPlugin(
                "MyJiraActivity1",
                intent.getStringExtra(getString(R.string.JIRA_HOST)),
                intent.getStringExtra(getString(R.string.JIRA_USERNAME)),
                intent.getStringExtra(getString(R.string.JIRA_PASSWORD)),
                intent.getStringExtra(getString(R.string.JIRA_WALLBOARD_ID)),
                Integer.parseInt(sharedPref.getString("cycle_period", "10")),
                sharedPref.getString("transition_effect", "scrollLeft"),
                sharedPref.getBoolean("random", false)
        );

        View decorView = getWindow().getDecorView();

        // hide the status bar
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
    }
}
