package io.epiclabs.walldroid.main.wallboardList;

import android.app.Activity;
import android.support.design.widget.CollapsingToolbarLayout;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import io.epiclabs.walldroid.R;
import io.epiclabs.walldroid.core.Plugin;
import io.epiclabs.walldroid.core.PluginManager;

public class WallboardDetailFragment extends Fragment {
    public static final String pluginAlias = "item_id";


    private Plugin plugin;

    public WallboardDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        if (getArguments().containsKey(pluginAlias)) {
            plugin = PluginManager.pluginsMap.get(getArguments().getString(pluginAlias));

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
        View rootView = inflater.inflate(R.layout.wallboard_detail, container, false);

        // Show the dummy content as text in a TextView.
        if (plugin != null) {
            TextView a = ((TextView) rootView.findViewById(R.id.wallboard_detail));
            a.setText(plugin.getAlias() + "(" + plugin.getHost() + ")");
//            plugin.setView(getContext(), container, getActivity());
        }

        return rootView;
    }
}
