package io.github.krtrttx.auge;

import android.service.dreams.DreamService;
import android.webkit.WebView;

/** Android screensaver ("Daydream"). A touch ends it, as usual. */
public class AugeDream extends DreamService {

    private WebView web;

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        setInteractive(false);       // touch / key press ends the screensaver
        setFullscreen(true);         // hide the status bar
        setScreenBright(true);
        web = AugeWeb.create(this, AugeWeb.isDark(this));   // uses the choice made in the app
        setContentView(web);
    }

    @Override
    public void onDreamingStarted() {
        super.onDreamingStarted();
        if (web != null) { web.onResume(); web.resumeTimers(); }
    }

    @Override
    public void onDreamingStopped() {
        if (web != null) { web.onPause(); web.pauseTimers(); }
        super.onDreamingStopped();
    }

    @Override
    public void onDetachedFromWindow() {
        AugeWeb.destroy(web);
        web = null;
        super.onDetachedFromWindow();
    }
}
