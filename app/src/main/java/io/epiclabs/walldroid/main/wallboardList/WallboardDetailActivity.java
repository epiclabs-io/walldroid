package io.epiclabs.walldroid.main.wallboardList;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import io.epiclabs.walldroid.R;
import io.epiclabs.walldroid.core.Plugin;
import io.epiclabs.walldroid.core.PluginManager;

/**
 * An activity representing a single Wallboard detail screen. This
 * activity is only used narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link WallboardListActivity}.
 */
public class WallboardDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallboard_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);

        final Intent intent = this.getIntent();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.addPluginFab);
        final AppCompatActivity self = this;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchPlugin(intent, view, self);
            }
        });

        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        // savedInstanceState is non-null when there is fragment state
        // saved from previous configurations of this activity
        // (e.g. when rotating the screen from portrait to landscape).
        // In this case, the fragment will automatically be re-added
        // to its container so we don't need to manually add it.
        // For more information, see the Fragments API guide at:
        //
        // http://developer.android.com/guide/components/fragments.html
        //
        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
            Bundle arguments = new Bundle();
            arguments.putLong(WallboardDetailFragment.pluginId, getIntent().getLongExtra(WallboardDetailFragment.pluginId, 0));
            arguments.putSerializable("pluginType", PluginManager.PluginType.JIRA_CLOUD);
            WallboardDetailFragment fragment = new WallboardDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.wallboard_detail_container, fragment)
                    .commit();
        }
    }

    private void launchPlugin(Intent intent, View view, AppCompatActivity activity) {
        Long id = intent.getLongExtra(WallboardDetailFragment.pluginId, 0);
        // TODO: type needs to be inferred
        Plugin plugin = PluginManager.get(PluginManager.PluginType.JIRA_CLOUD, id);
        if (plugin != null)
            plugin.playService(view, activity);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // This ID represents the Home or Up button. In the case of this
            // activity, the Up button is shown. For
            // more details, see the Navigation pattern on Android Design:
            //
            // http://developer.android.com/design/patterns/navigation.html#up-vs-back
            //
            navigateUpTo(new Intent(this, WallboardListActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
