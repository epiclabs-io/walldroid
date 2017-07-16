package io.epiclabs.walldroid.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import io.epiclabs.walldroid.R;
import io.epiclabs.walldroid.jira.JiraPlayActivity;

public class MainActivity extends AppCompatActivity {
//    EditText usernameEditText;
//    EditText passEditText;
//    EditText jiraHostEditText;
//    EditText wallboardIdEditText;
//    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        View decorView = getWindow().getDecorView();
        // hide the status bar
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);

//        usernameEditText = (EditText)findViewById(R.id.username);
//        passEditText = (EditText)findViewById(R.id.password);
//        jiraHostEditText = (EditText)findViewById(R.id.jiraHost);
//        wallboardIdEditText = (EditText)findViewById(R.id.wallboardID);
//        sharedPreferences = getPreferences(Context.MODE_PRIVATE);
//
//        usernameEditText.setText(sharedPreferences.getString(getString(R.string.JIRA_USERNAME), ""));
//        passEditText.setText(sharedPreferences.getString(getString(R.string.JIRA_PASSWORD), ""));
//        jiraHostEditText.setText(sharedPreferences.getString(getString(R.string.JIRA_HOST), ""));
//        wallboardIdEditText.setText(sharedPreferences.getString(getString(R.string.JIRA_WALLBOARD_ID), ""));
    }

    public void go(View view)
    {
//        SharedPreferences.Editor sharedPreferencesEditor = sharedPreferences.edit();
//        sharedPreferencesEditor.putString(getString(R.string.JIRA_USERNAME), usernameEditText.getText().toString());
//        sharedPreferencesEditor.putString(getString(R.string.JIRA_PASSWORD), passEditText.getText().toString());
//        sharedPreferencesEditor.putString(getString(R.string.JIRA_HOST), jiraHostEditText.getText().toString());
//        sharedPreferencesEditor.putString(getString(R.string.JIRA_WALLBOARD_ID), wallboardIdEditText.getText().toString());
//        sharedPreferencesEditor.apply();
//
//        if (usernameEditText.getText().toString().trim().equalsIgnoreCase("")) {
//            usernameEditText.setError(getString(R.string.FIELD_REQUIRED));
//            return;
//        }
//        if (passEditText.getText().toString().trim().equalsIgnoreCase("")) {
//            passEditText.setError(getString(R.string.FIELD_REQUIRED));
//            return;
//        }
//        if (jiraHostEditText.getText().toString().trim().equalsIgnoreCase("")) {
//            jiraHostEditText.setError(getString(R.string.FIELD_REQUIRED));
//            return;
//        }
//        if (wallboardIdEditText.getText().toString().trim().equalsIgnoreCase("")) {
//            wallboardIdEditText.setError(getString(R.string.FIELD_REQUIRED));
//            return;
//        }
//
        Intent intent = new Intent(MainActivity.this, JiraPlayActivity.class);
//        intent.putExtra(getString(R.string.JIRA_USERNAME), usernameEditText.getText().toString());
//        intent.putExtra(getString(R.string.JIRA_PASSWORD), passEditText.getText().toString());
//        intent.putExtra(getString(R.string.JIRA_HOST), jiraHostEditText.getText().toString());
//        intent.putExtra(getString(R.string.JIRA_WALLBOARD_ID), wallboardIdEditText.getText().toString());

        startActivity(intent);
    }

    public void goSettings(View view)
    {
        Intent intent = new Intent(MainActivity.this, PreferencesActivity.class);
        startActivity(intent);
    }
}
