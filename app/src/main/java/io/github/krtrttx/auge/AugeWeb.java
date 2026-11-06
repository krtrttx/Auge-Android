package io.github.krtrttx.auge;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.webkit.WebViewAssetLoader;

/** Creates the WebView that renders auge.html from the app's assets. */
final class AugeWeb {

    /**
     * URL parameters for auge.html. "kiosk" hides the fullscreen hint.
     * Further options (append with &):
     *   e=0..9   fixed expression (0 neutral, 1 Mona Lisa, 2 joy, 3 sadness, 4 fear,
     *            5 surprise, 6 anger, 7 suspicion, 8 fatigue, 9 melancholy)
     *   q=0.7    fixed render resolution (70 %) instead of automatic adaptation
     *   y=0.3    vertical eye position (fraction of screen height)
     * Example: "kiosk&e=1&q=0.8"
     */
    static final String PARAMS = "kiosk";

    private static final String URL =
            "https://appassets.androidplatform.net/assets/auge.html?" + PARAMS;

    private static final String PREFS = "auge", KEY_DARK = "dark";

    private AugeWeb() {}

    /** Remembered light/dark choice (shared by the app and the screensaver). */
    static boolean isDark(Context ctx) {
        return ctx.getSharedPreferences(PREFS, Context.MODE_PRIVATE).getBoolean(KEY_DARK, false);
    }

    static void setDark(Context ctx, boolean dark) {
        ctx.getSharedPreferences(PREFS, Context.MODE_PRIVATE).edit().putBoolean(KEY_DARK, dark).apply();
    }

    /** (Re)loads the animation with the requested background. */
    static void show(WebView web, boolean dark) {
        web.setBackgroundColor(dark ? Color.BLACK : Color.WHITE);   // avoids a flash while loading
        web.loadUrl(dark ? URL + "&dark" : URL);                    // &dark: black background, light lashes
    }

    @SuppressLint("SetJavaScriptEnabled")
    static WebView create(Context ctx, boolean dark) {
        final WebViewAssetLoader loader = new WebViewAssetLoader.Builder()
                .addPathHandler("/assets/", new WebViewAssetLoader.AssetsPathHandler(ctx))
                .build();

        WebView web = new WebView(ctx);
        web.setVerticalScrollBarEnabled(false);
        web.setHorizontalScrollBarEnabled(false);
        web.setOverScrollMode(View.OVER_SCROLL_NEVER);
        web.setLayerType(View.LAYER_TYPE_HARDWARE, null);

        WebSettings s = web.getSettings();
        s.setJavaScriptEnabled(true);                 // required for the animation
        s.setAllowFileAccess(false);
        s.setAllowContentAccess(false);
        s.setDomStorageEnabled(false);
        s.setMediaPlaybackRequiresUserGesture(true);

        web.setWebViewClient(new WebViewClient() {
            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
                return loader.shouldInterceptRequest(request.getUrl());
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return true;                          // never navigate away
            }
        });

        show(web, dark);
        return web;
    }

    static void destroy(WebView web) {
        if (web == null) return;
        web.stopLoading();
        web.loadUrl("about:blank");
        web.removeAllViews();
        web.destroy();
    }
}
