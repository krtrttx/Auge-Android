package de.auge.bildschirmschoner;

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

/** Erzeugt die WebView, die auge.html aus den App-Assets anzeigt. */
final class AugeWeb {

    /**
     * URL-Parameter für auge.html. "kiosk" blendet den Vollbild-Hinweis aus.
     * Weitere Möglichkeiten (mit & anhängen):
     *   e=0..9   fester Ausdruck (0 neutral, 1 Mona Lisa, 2 Freude, 3 Trauer, 4 Angst,
     *            5 Überraschung, 6 Ärger, 7 Misstrauen, 8 Müdigkeit, 9 Melancholie)
     *   q=0.7    feste Renderauflösung (70 %) statt automatischer Anpassung
     * Beispiel: "kiosk&e=1&q=0.8"
     */
    static final String PARAMS = "kiosk";

    private static final String URL =
            "https://appassets.androidplatform.net/assets/auge.html?" + PARAMS;

    private AugeWeb() {}

    @SuppressLint("SetJavaScriptEnabled")
    static WebView create(Context ctx) {
        final WebViewAssetLoader loader = new WebViewAssetLoader.Builder()
                .addPathHandler("/assets/", new WebViewAssetLoader.AssetsPathHandler(ctx))
                .build();

        WebView web = new WebView(ctx);
        web.setBackgroundColor(Color.WHITE);          // kein schwarzes Aufblitzen beim Start
        web.setVerticalScrollBarEnabled(false);
        web.setHorizontalScrollBarEnabled(false);
        web.setOverScrollMode(View.OVER_SCROLL_NEVER);
        web.setLayerType(View.LAYER_TYPE_HARDWARE, null);

        WebSettings s = web.getSettings();
        s.setJavaScriptEnabled(true);                 // für die Animation erforderlich
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
                return true;                          // keine Navigation nach außen
            }
        });

        web.loadUrl(URL);
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
