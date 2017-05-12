package io.epiclabs.walldroid.main;

import android.content.Intent;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        usernameEditText = (EditText)findViewById(R.id.username);
        passEditText = (EditText)findViewById(R.id.password);
        jiraHostEditText = (EditText)findViewById(R.id.jiraHost);
        wallboardIDEditText = (EditText)findViewById(R.id.wallboardID);
    }

    public void go(View view)
    {
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
