package io.epiclabs.walldroid.main;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import io.epiclabs.walldroid.JiraWebActivity;
import io.epiclabs.walldroid.R;

public class MainActivity extends AppCompatActivity {
    EditText usernameEditText;
    EditText passEditText;
    EditText jiraHostEditText;
    EditText wallboardIDEditText;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        usernameEditText = (EditText)findViewById(R.id.username);
        passEditText = (EditText)findViewById(R.id.password);
        jiraHostEditText = (EditText)findViewById(R.id.jiraHost);
        wallboardIDEditText = (EditText)findViewById(R.id.wallboardID);
        sharedPreferences = getPreferences(Context.MODE_PRIVATE);

        usernameEditText.setText(sharedPreferences.getString("USERNAME", ""));
        passEditText.setText(sharedPreferences.getString("PASSWORD", ""));
        jiraHostEditText.setText(sharedPreferences.getString("JIRA_HOST", ""));
        wallboardIDEditText.setText(sharedPreferences.getString("WALLBOARD_ID", ""));
    }

    public void go(View view)
    {
        SharedPreferences.Editor sharedPreferencesEditor = sharedPreferences.edit();
        sharedPreferencesEditor.putString("USERNAME", usernameEditText.getText().toString());
        sharedPreferencesEditor.putString("PASSWORD", passEditText.getText().toString());
        sharedPreferencesEditor.putString("JIRA_HOST", jiraHostEditText.getText().toString());
        sharedPreferencesEditor.putString("WALLBOARD_ID", wallboardIDEditText.getText().toString());
        sharedPreferencesEditor.commit();

        Intent intent = new Intent(MainActivity.this, JiraWebActivity.class);
        intent.putExtra("USERNAME", usernameEditText.getText().toString());
        intent.putExtra("PASSWORD", passEditText.getText().toString());
        intent.putExtra("JIRA_HOST", jiraHostEditText.getText().toString());
        intent.putExtra("WALLBOARD_ID", wallboardIDEditText.getText().toString());

        startActivity(intent);
    }

    public void goSettings(View view)
    {
        Intent intent = new Intent(MainActivity.this, PreferencesActivity.class);
        startActivity(intent);
    }
}
