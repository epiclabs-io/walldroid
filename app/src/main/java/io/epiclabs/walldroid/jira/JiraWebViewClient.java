package io.epiclabs.walldroid.jira;

import android.content.Context;
import android.util.Log;
import android.webkit.ConsoleMessage;
import android.webkit.CookieManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import io.epiclabs.walldroid.R;

/**
 * Created by adrian on 14/05/17.
 */
@SuppressWarnings("deprecation")
public class JiraWebViewClient extends WebViewClient {

    static String UA = "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.96 Safari/537.36";

    protected CookieManager cookieManager;

    public JiraWebViewClient(WebView webView, String host, String name, String value) {
        super();
        setWebViewSettings(webView);
        setWebViewCookies(host, name, value);
    }

    private void setWebViewSettings(WebView webView) {
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setAppCacheEnabled(true);
        webView.getSettings().setAllowContentAccess(true);
        webView.getSettings().setDatabaseEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setUserAgentString(JiraWebViewClient.UA);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setDisplayZoomControls(false);
        webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        webView.setScrollbarFadingEnabled(false);
        webView.getSettings().setDefaultZoom(WebSettings.ZoomDensity.FAR);
    }

    private void setWebViewCookies(String host, String name, String value) {
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);
        cookieManager.removeAllCookie();
        cookieManager.setCookie(host, name + "=" + value);
    }

    @Override
    public void onPageFinished(WebView view, String url){
        String cookies = CookieManager.getInstance().getCookie(url);
        for (String cookie : cookies.split(";")) {
            System.out.println("--cookie--" + cookie);
        }
    }
}
