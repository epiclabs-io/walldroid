package io.epiclabs.walldroid.main.wallboardList;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import io.epiclabs.walldroid.R;
import io.epiclabs.walldroid.core.Plugin;
import io.epiclabs.walldroid.core.PluginManager;
import io.epiclabs.walldroid.jira.JiraPlugin;

/**
 * An activity representing a list of Wallboards. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link WallboardDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class WallboardListActivity extends AppCompatActivity {
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

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        FloatingActionButton addPluginFab = (FloatingActionButton) findViewById(R.id.addPluginFab);
        addPluginFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PluginManager.newPlugin(new JiraPlugin("New JIRA plugin", "https://XXX.atlassian.net", "XXX", "XXX", "10800", 10, "", false));
                Snackbar.make(view, "New JIRA Plugin added", Snackbar.LENGTH_LONG).setAction("Action", null).show();
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
                    arguments.putLong(WallboardDetailFragment.pluginId, holder.mItem.getId());
                    WallboardDetailFragment fragment = new WallboardDetailFragment();
                    fragment.setArguments(arguments);

                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.wallboard_detail_container, fragment)
                            .commit();
                } else {
                    Context context = v.getContext();
                    Intent intent = new Intent(context, WallboardDetailActivity.class);
                    intent.putExtra(WallboardDetailFragment.pluginId, holder.mItem.getId());
                    context.startActivity(intent);
                }
                }
            });
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

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
