package de.auge.bildschirmschoner;

import android.service.dreams.DreamService;
import android.webkit.WebView;

/** Android-Bildschirmschoner ("Daydream"). Eine Berührung beendet ihn wie üblich. */
public class AugeDream extends DreamService {

    private WebView web;

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        setInteractive(false);       // Berührung/Taste beendet den Bildschirmschoner
        setFullscreen(true);         // Statusleiste ausblenden
        setScreenBright(true);
        web = AugeWeb.create(this, AugeWeb.isDark(this));   // Wahl aus der App
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
