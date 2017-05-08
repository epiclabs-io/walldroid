package io.epiclabs.walldroid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.net.URL;

/**
 * Created by adrian on 31/03/17.
 */
public class JiraWebActivity extends AppCompatActivity {
    private WebView webView;
    private String password;
    private String username;
    private String url;
    private String wallboardUrl;
    private String usernameFieldId;

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

        View decorView = getWindow().getDecorView();
        // hide the status bar
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        // hide the action bar
//        ActionBar actionBar = getActionBar();
//        actionBar.hide();

        this.webView = (WebView) findViewById(R.id.webview);

        try {
            Document doc = Jsoup.connect(this.url).get();
            Elements usernameInput = doc.select(this.usernameFieldId);
        } catch (java.io.IOException e) {
            Log.e(e.toString(), "Jsoup fail");
        }

//        CookieSyncManager.createInstance(this);
//        CookieSyncManager.getInstance().startSync();


        webView.getSettings().setJavaScriptEnabled(true);

        this.webView.setWebViewClient(new MyWebViewClient());

        this.webView.loadUrl(this.wallboardUrl);
    }
}