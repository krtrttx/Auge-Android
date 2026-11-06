package io.github.krtrttx.auge;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowInsets;
import android.view.WindowInsetsController;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.Toast;

/** Continuous fullscreen mode, e.g. for installations. Keeps the screen on. */
public class AugeActivity extends Activity {

    private WebView web;
    private boolean dark;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        dark = AugeWeb.isDark(this);
        web = AugeWeb.create(this, dark);
        setContentView(web);
        hideSystemBars();

        // Double tap toggles light <-> dark (a single tap does nothing, to avoid accidental switching)
        final GestureDetector gd = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener() {
            @Override public boolean onDoubleTap(MotionEvent e) {
                dark = !dark;
                AugeWeb.setDark(AugeActivity.this, dark);
                AugeWeb.show(web, dark);
                Toast.makeText(AugeActivity.this, dark ? "Dark" : "Light", Toast.LENGTH_SHORT).show();
                return true;
            }
        });
        web.setOnTouchListener((v, ev) -> { gd.onTouchEvent(ev); return false; });
        Toast.makeText(this, "Double-tap: light ↔ dark", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) hideSystemBars();
    }

    @SuppressWarnings("deprecation")
    private void hideSystemBars() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            getWindow().setDecorFitsSystemWindows(false);
            WindowInsetsController c = getWindow().getInsetsController();
            if (c != null) {
                c.hide(WindowInsets.Type.statusBars() | WindowInsets.Type.navigationBars());
                c.setSystemBarsBehavior(WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE);
            }
        } else {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
        }
    }

    @Override protected void onResume()  { super.onResume();  if (web != null) { web.onResume(); web.resumeTimers(); } }
    @Override protected void onPause()   { if (web != null) { web.onPause(); web.pauseTimers(); } super.onPause(); }
    @Override protected void onDestroy() { AugeWeb.destroy(web); web = null; super.onDestroy(); }
}
