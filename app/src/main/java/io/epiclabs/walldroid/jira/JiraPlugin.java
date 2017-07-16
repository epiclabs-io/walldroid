package io.epiclabs.walldroid.jira;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import io.epiclabs.walldroid.common.Utils;
import io.epiclabs.walldroid.core.Plugin;
import io.epiclabs.walldroid.core.PluginManager;

/**
 * Created by adrian on 14/05/17.
 */

public class JiraPlugin extends Plugin {
    private String username;
    private String password;
    private String wallboardId;

    private Integer period;
    private String effect;
    private Boolean random;

    public JiraPlugin() {
        // Default constructor is necessary for SugarRecord
    }

    public JiraPlugin(String alias, String host, String username, String password, String wallboardId, Integer period, String effect, Boolean random) {
        this.type = PluginManager.PluginType.JIRA_CLOUD;
        this.alias = alias;
        if (host.endsWith("/")) {
            host = host.substring(0, host.length()-1);
        }
        this.host = host;
        this.username = username;
        this.password = password;
        this.wallboardId = wallboardId;
        this.period = period < 1000 ? period * 1000 : period;
        this.effect = effect;
        this.random = random;
    }

    public String getUsername() { return username; }
    public long setUsername(String username) {
        this.username = username;
        return this.save();
    }

    public String getPassword() { return password; }
    public long setPassword(String password) {
        this.password = password;
        return this.save();
    }

    public String getWallboardId() { return wallboardId; }
    public long setWallboardId(String wallboardId) {
        this.wallboardId = wallboardId;
        return this.save();
    }

    public Integer getPeriod() { return period; }
    public long setPeriod(Integer period) {
        this.period = period;
        return this.save();
    }

    public String getEffect() { return effect; }
    public long setEffect(String effect) {
        this.effect = effect;
        return this.save();
    }

    public Boolean getRandom() { return random; }
    public long setRandom(Boolean random) {
        this.random = random;
        return this.save();
    }

    @Override
    public void addService(View view) {
        //
    };

    @Override
    public void editService(View view) {
        //
    }

    @Override
    public void playService(View view, Activity activity) {
        Intent intent = new Intent(activity, JiraPlayActivity.class);
        final Context context = view.getContext();
        intent.putExtra("pluginId", this.getId());
        activity.startActivity(intent);
    }

    EditText usernameEditText;
    EditText passEditText;
    EditText jiraHostEditText;
    EditText wallboardIdEditText;
//    SharedPreferences sharedPreferences;

    public void setView(Context context, ViewGroup layout, Activity activity) {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);

        usernameEditText = new EditText(context);
        passEditText = new EditText(context);
        jiraHostEditText = new EditText(context);
        wallboardIdEditText = new EditText(context);
//        sharedPreferences = activity.getPreferences(Context.MODE_PRIVATE);
//
        usernameEditText.setText(username);
        passEditText.setText(password);
        jiraHostEditText.setText(host);
        wallboardIdEditText.setText(wallboardId);

//        layout.addView(usernameEditText, 0, layoutParams);
//        layout.addView(passEditText, 1, layoutParams);
//        layout.addView(jiraHostEditText, 2, layoutParams);
//        layout.addView(wallboardIdEditText, 3, layoutParams);
    }

    public void initWebView(final JiraPlayActivity jiraPlayActivity, final WebView webView) {
        final RequestQueue mRequestQueue;

        // Instantiate the cache
        Cache cache = new DiskBasedCache(jiraPlayActivity.getCacheDir(), 1024 * 1024); // 1MB cap

        // Set up the network to use HttpURLConnection as the HTTP client.
        Network network = new BasicNetwork(new HurlStack());

        // Instantiate the RequestQueue with the cache and network.
        mRequestQueue = new RequestQueue(cache, network);

        // Start the queue
        mRequestQueue.start();
        JSONObject jsonBody;
        try {
            jsonBody = new JSONObject("{\"username\":\""+username+"\", \"password\": \""+password+"\"}");
        } catch (JSONException e) {
            jsonBody = new JSONObject();
        }

        JsonObjectRequest authRequest = new JsonObjectRequest(host + "/rest/auth/1/session", jsonBody,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Do something with the response
                        System.out.println("--.response.--" + response.toString());
                        Map<String, String> extraHeaders = new HashMap<String, String>();
                        try {
                            JSONObject session = response.getJSONObject("session");
                            String name = session.getString("name");
                            String value = session.getString("value");

                            webView.setWebViewClient(new JiraWebViewClient(webView, host, name, value));
                            webView.setWebChromeClient(new JiraWebChromeClient());
                            webView.loadUrl(wallboardUrl());
                            System.out.println(" --- JIRACLOUD --- Loading URL " + wallboardUrl() + "---");
                        } catch (JSONException jsonE) {
                            System.out.println("--.error.--" + jsonE.getMessage());
                            Utils.showAlertDialog(jiraPlayActivity, jsonE.getMessage(),
                                    "Authentication failed",
                                    "Please check the provided credentials and try again.");
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println("--.error.--" + error.getMessage());
                        Utils.showAlertDialog(jiraPlayActivity, error.getMessage(),
                                "Authentication failed",
                                "Please check the provided credentials and try again.");
                    }
                });

        // Add the request to the RequestQueue.
        mRequestQueue.add(authRequest);
    }

    private String wallboardUrl() {
        return host + "/plugins/servlet/Wallboard/?dashboardId=" + wallboardId +
            "&cyclePeriod=" + period +
            "&transitionFx=" + effect +
            "&random=" + random;
    }
}
