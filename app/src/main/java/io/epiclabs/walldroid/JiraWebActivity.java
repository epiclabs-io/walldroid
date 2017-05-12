package io.epiclabs.walldroid;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import io.epiclabs.walldroid.main.PreferencesActivity;

/**
 * Created by adrian on 31/03/17.
 */
public class JiraWebActivity extends AppCompatActivity {
    private WebView webView;

    // parameters
    private String password;
    private String username;
    private String url;
    private String wallboardUrl;
    private String usernameFieldId;

    private String finalUrl;

    // options
    private boolean random;
    private Integer cyclePeriod;
    private String transitionFx;

    void JiraWebActivity() {
        this.username = "###";
        this.password = "###";
        this.url = "###";
        this.wallboardUrl = "###";
        this.usernameFieldId = "#username";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jira_web);

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        random = sharedPref.getBoolean("random", false);
        cyclePeriod = Integer.parseInt(sharedPref.getString("cycle_period", "10")) * 1000;
        transitionFx = sharedPref.getString("transition_effect", "scrollLeft");

        Intent intent = getIntent();
        username = intent.getStringExtra("USERNAME");
        password = intent.getStringExtra("PASSWORD");
        url = intent.getStringExtra("JIRA_HOST");
        wallboardUrl = intent.getStringExtra("WALLBOARD_ID");

        View decorView = getWindow().getDecorView();
        // hide the status bar
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        this.webView = (WebView) findViewById(R.id.webview);


        finalUrl = "";
        finalUrl += url + "/plugins/servlet/Wallboard/?dashboardId=" + wallboardUrl;
        finalUrl += "&cyclePeriod=" + cyclePeriod;
        finalUrl += "&transitionFx=" + transitionFx;
        finalUrl += "&random=" + random;

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

        JsonObjectRequest authRequest = new JsonObjectRequest("https://epiclabs.atlassian.net/rest/auth/1/session",
                jsonBody,
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
                            extraHeaders.put("Cookie", name +"="+ value);
                        } catch (JSONException e) {
                            System.out.println("--.error.--" + e.getMessage());
                        }

                        webView.loadUrl(url, extraHeaders);
                        //mRequestQueue.add(stringRequest);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle error
                        System.out.println("--.error.--" + error.getMessage());
                    }
                });


        // Add the request to the RequestQueue.
        mRequestQueue.add(authRequest);
    }
}