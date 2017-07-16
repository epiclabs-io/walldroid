package io.epiclabs.walldroid.main.wallboardList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
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
import android.widget.Spinner;

import io.epiclabs.walldroid.R;
import io.epiclabs.walldroid.core.Plugin;
import io.epiclabs.walldroid.core.PluginManager;
import io.epiclabs.walldroid.jira.JiraPlugin;
import io.epiclabs.walldroid.main.PreferencesActivity;

public class WallboardDetailFragment extends Fragment {
    public static final String pluginId = "pluginId";
    public static final String pluginType = "pluginType";

    private EditText aliasEditText;
    private EditText hostEditText;
    private Spinner typeSpinner;

    private Plugin plugin;

    View rootView;
    View jiraControlsView;

    public WallboardDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(pluginId) && getArguments().containsKey(pluginType)) {
            final PluginManager.PluginType type = (PluginManager.PluginType) getArguments().getSerializable(pluginType);
            final long id = getArguments().getLong(pluginId);
            plugin = PluginManager.get(type, id);

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

        typeSpinner = (Spinner) rootView.findViewById(R.id.type_spinner);
        typeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                if (plugin != null) {
                    String item = (String)typeSpinner.getSelectedItem();
                    // update plugin
                    plugin.setType(PluginManager.PluginType.JIRA_CLOUD);

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

        // TODO: Extract to each XPlugin
        final JiraPlugin jiraPlugin = ((JiraPlugin) plugin);

        EditText usernameEditText = (EditText) rootView.findViewById(R.id.username);
        usernameEditText.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                if (jiraPlugin != null) {
                    jiraPlugin.setUsername(s.toString());
                }
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });

        EditText passwordEditText = (EditText) rootView.findViewById(R.id.password);
        passwordEditText.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                if (jiraPlugin != null) {
                    jiraPlugin.setPassword(s.toString());
                }
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });

        EditText wallboardId = (EditText) rootView.findViewById(R.id.wallboardID);
        wallboardId.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                if (jiraPlugin != null) {
                    jiraPlugin.setWallboardId(s.toString());
                }
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });

//        EditText period = (EditText) rootView.findViewById(R.id.username);

//        EditText effect = (EditText) rootView.findViewById(R.id.username);

//        EditText random = (EditText) rootView.findViewById(R.id.username);

        if (plugin != null) {
            aliasEditText.setText(plugin.getAlias());
            hostEditText.setText(plugin.getHost());
            typeSpinner.setSelection(((ArrayAdapter)typeSpinner.getAdapter()).getPosition(plugin.getType()));
            usernameEditText.setText(((JiraPlugin) plugin).getUsername());
            passwordEditText.setText(((JiraPlugin) plugin).getPassword());
            wallboardId.setText(((JiraPlugin) plugin).getWallboardId());
        }

        return rootView;
    }
}