package io.epiclabs.walldroid.jira;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.WebView;

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

import io.epiclabs.walldroid.R;
import io.epiclabs.walldroid.common.Utils;

/**
 * Created by adrian on 31/03/17.
 */
public class JiraWebActivity extends AppCompatActivity {
    public static final String REST_AUTH_1_SESSION = "/rest/auth/1/session";
    private WebView webView;

    // parameters
    private String username;
    private String password;
    private String host;
    private String wallboardId;

    private String finalUrl;

    // options
    private boolean random;
    private Integer cyclePeriod;
    private String transitionFx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jira_web);

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        random = sharedPref.getBoolean("random", false);
        cyclePeriod = Integer.parseInt(sharedPref.getString("cycle_period", "10")) * 1000;
        transitionFx = sharedPref.getString("transition_effect", "scrollLeft");

        Intent intent = getIntent();
        username = intent.getStringExtra(getString(R.string.JIRA_USERNAME));
        password = intent.getStringExtra(getString(R.string.JIRA_PASSWORD));
        host = intent.getStringExtra(getString(R.string.JIRA_HOST));
        wallboardId = intent.getStringExtra(getString(R.string.JIRA_WALLBOARD_ID));

        if (host.endsWith("/")) {
            host = host.substring(0, host.length()-1);;
        }

        View decorView = getWindow().getDecorView();
        // hide the status bar
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        this.webView = (WebView) findViewById(R.id.webview);


        finalUrl = "";
        finalUrl += host + "/plugins/servlet/Wallboard/?dashboardId=" + wallboardId;
        finalUrl += "&cyclePeriod=" + cyclePeriod;
        finalUrl += "&transitionFx=" + transitionFx;
        finalUrl += "&random=" + random;

        System.out.println("--final url--" + finalUrl);

        doRequest(finalUrl);

    }

    private void doRequest(final String url) {
        final RequestQueue mRequestQueue;

        // Instantiate the cache
        Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024); // 1MB cap

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

        JsonObjectRequest authRequest = new JsonObjectRequest(host + REST_AUTH_1_SESSION, jsonBody,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Do something with the response
                        System.out.println("--.response.--" + response.toString());
                        Map <String, String> extraHeaders = new HashMap<String, String>();
                        try {
                            JSONObject session = response.getJSONObject("session");
                            String name = session.getString("name");
                            String value = session.getString("value");

                            webView.setWebViewClient(new JiraWebViewClient(webView, host, name, value));
                            webView.setWebChromeClient(new JiraWebChromeClient());
                            webView.loadUrl(url);
                        } catch (JSONException jsonE) {
                            System.out.println("--.error.--" + jsonE.getMessage());
                            Utils.showAlertDialog(JiraWebActivity.this, jsonE.getMessage(),
                                    "Authentication failed",
                                    "Please check the provided credentials and try again.");
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println("--.error.--" + error.getMessage());
                        Utils.showAlertDialog(JiraWebActivity.this, error.getMessage(),
                                "Authentication failed",
                                "Please check the provided credentials and try again.");
                    }
                });

        // Add the request to the RequestQueue.
        mRequestQueue.add(authRequest);

    }
}

