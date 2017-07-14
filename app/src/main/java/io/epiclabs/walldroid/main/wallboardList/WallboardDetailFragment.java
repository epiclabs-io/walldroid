package io.epiclabs.walldroid.main.wallboardList;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import io.epiclabs.walldroid.R;
import io.epiclabs.walldroid.core.Plugin;
import io.epiclabs.walldroid.core.PluginManager;
import io.epiclabs.walldroid.jira.JiraPlayActivity;
import io.epiclabs.walldroid.jira.JiraPlugin;
import io.epiclabs.walldroid.main.MainActivity;
import io.epiclabs.walldroid.main.PreferencesActivity;

public class WallboardDetailFragment extends Fragment {
    public static final String pluginId = "pluginId";


    private EditText aliasEditText;
    private EditText hostEditText;
    private Spinner typeSpinner;

    private EditText usernameET;
    private EditText passwordET;
    private EditText wallboardIDET;

    private Plugin plugin;

    View rootView;
    View jiraControlsView;

    public WallboardDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        if (getArguments().containsKey(pluginId)) {
            plugin = PluginManager.get(getArguments().getLong(pluginId));

            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                appBarLayout.setTitle(plugin.getAlias());
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.wallboard_detail, container, false);
        jiraControlsView = rootView.findViewById(R.id.jira_settings);
        jiraControlsView.setVisibility(View.INVISIBLE);


        Button clickButton = (Button) rootView.findViewById(R.id.settings_button);
        clickButton.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PreferencesActivity.class);
                startActivity(intent);
            }
        });

        aliasEditText = (EditText) rootView.findViewById(R.id.alias);
        aliasEditText.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                if (plugin != null) {
                    plugin.setAlias(s.toString());
                }
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });

        hostEditText = (EditText) rootView.findViewById(R.id.host);
        hostEditText.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                if (plugin != null) {
                    plugin.setHost(s.toString());
                }
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });

        typeSpinner= (Spinner) rootView.findViewById(R.id.type_spinner);
        typeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                if (plugin != null) {
                    String item = (String)typeSpinner.getSelectedItem();
                    // update plugin
                    plugin.setType(item);

                    // show additional controls for this type of plugin
                    switch (item.toLowerCase()) {
                        case "jira":
                            jiraControlsView.setVisibility(View.VISIBLE);
                            break;
                        default:
                            jiraControlsView.setVisibility(View.INVISIBLE);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        usernameET = (EditText) rootView.findViewById(R.id.username);
        usernameET.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                if (plugin != null) {
                    ((JiraPlugin)plugin).setUsername(s.toString());
                }
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });
        passwordET = (EditText) rootView.findViewById(R.id.password);
        passwordET.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                if (plugin != null) {
                    ((JiraPlugin)plugin).setPassword(s.toString());
                }
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });
        wallboardIDET = (EditText) rootView.findViewById(R.id.wallboardID);
        wallboardIDET.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                if (plugin != null) {
                    ((JiraPlugin)plugin).setWallboardId(s.toString());
                }
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });

        if (plugin != null) {
            aliasEditText.setText(plugin.getAlias());
            hostEditText.setText(plugin.getHost());
            typeSpinner.setSelection(((ArrayAdapter)typeSpinner.getAdapter()).getPosition(plugin.getType()));
            usernameET.setText(((JiraPlugin)plugin).getUsername());
            passwordET.setText(((JiraPlugin)plugin).getPassword());
            wallboardIDET.setText(((JiraPlugin)plugin).getWallboardId());
        }


        return rootView;
    }
}
