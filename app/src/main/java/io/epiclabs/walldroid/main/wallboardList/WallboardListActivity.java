package io.epiclabs.walldroid.main.wallboardList;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;


import io.epiclabs.walldroid.R;
import io.epiclabs.walldroid.core.Plugin;
import io.epiclabs.walldroid.core.PluginManager;
import io.epiclabs.walldroid.jira.JiraPlayActivity;
import io.epiclabs.walldroid.jira.JiraPlugin;
import io.epiclabs.walldroid.main.MainActivity;

import java.util.List;

/**
 * An activity representing a list of Wallboards. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link WallboardDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class WallboardListActivity extends AppCompatActivity {
    EditText usernameEditText;
    EditText passEditText;
    EditText jiraHostEditText;
    EditText wallboardIdEditText;
    SharedPreferences sharedPreferences;

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;

    private SimpleItemRecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallboard_list);

//        View decorView = getWindow().getDecorView();
//        // hide the status bar
//        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN;
//        decorView.setSystemUiVisibility(uiOptions);
//
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

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        FloatingActionButton addPluginFab = (FloatingActionButton) findViewById(R.id.addPluginFab);
        addPluginFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PluginManager.newPlugin(new JiraPlugin("New JIRA plugin", "http://hostname", "username", "", "", 10, "", false));
                Snackbar.make(view, "New JIRA Plugin added", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                adapter.notifyDataSetChanged();
            }
        });

        View recyclerView = findViewById(R.id.wallboard_list);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);

        if (findViewById(R.id.wallboard_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        adapter = new SimpleItemRecyclerViewAdapter(PluginManager.plugins);
        recyclerView.setAdapter(adapter);
    }

    public class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private final List<Plugin> mValues;

        public SimpleItemRecyclerViewAdapter(List<Plugin> items) {
            mValues = items;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.wallboard_list_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.mItem = mValues.get(position);
            holder.mIdView.setText(mValues.get(position).getAlias());
            holder.mContentView.setText(mValues.get(position).getHost());

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mTwoPane) {
                        Bundle arguments = new Bundle();
                        arguments.putString(WallboardDetailFragment.pluginAlias, holder.mItem.getAlias());
                        WallboardDetailFragment fragment = new WallboardDetailFragment();
                        fragment.setArguments(arguments);
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.wallboard_detail_container, fragment)
                                .commit();
                    } else {
                        Context context = v.getContext();
                        Intent intent = new Intent(context, WallboardDetailActivity.class);
                        intent.putExtra(WallboardDetailFragment.pluginAlias, holder.mItem.getAlias());

                        context.startActivity(intent);
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

//        public void go(View view)
//        {
//            SharedPreferences.Editor sharedPreferencesEditor = sharedPreferences.edit();
//            sharedPreferencesEditor.putString(getString(R.string.JIRA_USERNAME), usernameEditText.getText().toString());
//            sharedPreferencesEditor.putString(getString(R.string.JIRA_PASSWORD), passEditText.getText().toString());
//            sharedPreferencesEditor.putString(getString(R.string.JIRA_HOST), jiraHostEditText.getText().toString());
//            sharedPreferencesEditor.putString(getString(R.string.JIRA_WALLBOARD_ID), wallboardIdEditText.getText().toString());
//            sharedPreferencesEditor.apply();
//
//            if (usernameEditText.getText().toString().trim().equalsIgnoreCase("")) {
//                usernameEditText.setError(getString(R.string.FIELD_REQUIRED));
//                return;
//            }
//            if (passEditText.getText().toString().trim().equalsIgnoreCase("")) {
//                passEditText.setError(getString(R.string.FIELD_REQUIRED));
//                return;
//            }
//            if (jiraHostEditText.getText().toString().trim().equalsIgnoreCase("")) {
//                jiraHostEditText.setError(getString(R.string.FIELD_REQUIRED));
//                return;
//            }
//            if (wallboardIdEditText.getText().toString().trim().equalsIgnoreCase("")) {
//                wallboardIdEditText.setError(getString(R.string.FIELD_REQUIRED));
//                return;
//            }
//
//            Intent intent = new Intent(WallboardListActivity.this, JiraPlayActivity.class);
//            intent.putExtra(getString(R.string.JIRA_USERNAME), usernameEditText.getText().toString());
//            intent.putExtra(getString(R.string.JIRA_PASSWORD), passEditText.getText().toString());
//            intent.putExtra(getString(R.string.JIRA_HOST), jiraHostEditText.getText().toString());
//            intent.putExtra(getString(R.string.JIRA_WALLBOARD_ID), wallboardIdEditText.getText().toString());
//
//            startActivity(intent);
//        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public final View mView;
            public final TextView mIdView;
            public final TextView mContentView;
            public Plugin mItem;

            public ViewHolder(View view) {
                super(view);
                mView = view;
                mIdView = (TextView) view.findViewById(R.id.alias);
                mContentView = (TextView) view.findViewById(R.id.host);
            }

            @Override
            public String toString() {
                return super.toString() + " '" + mContentView.getText() + "'";
            }
        }
    }
}
