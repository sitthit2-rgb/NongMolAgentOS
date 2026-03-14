package com.nongmol.agent.v2;

import android.accessibilityservice.AccessibilityService;
import android.graphics.PixelFormat;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityEvent;
import android.widget.Button;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.speech.tts.TextToSpeech;
import java.util.Locale;

public class AgentAccessibilityService extends AccessibilityService {
    private WindowManager windowManager;
    private View floatingView;
    private TextToSpeech tts;

    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();
        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        tts = new TextToSpeech(this, status -> {
            if (status == TextToSpeech.SUCCESS) tts.setLanguage(new Locale("th", "TH"));
        });

        Button btn = new Button(this);
        btn.setText("🤖 NM-OS");
        btn.setTextColor(Color.CYAN);
        
        GradientDrawable gd = new GradientDrawable();
        gd.setColor(Color.parseColor("#AA000000"));
        gd.setCornerRadius(100f);
        gd.setStroke(2, Color.CYAN);
        btn.setBackground(gd);

        WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_ACCESSIBILITY_OVERLAY,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);
        params.gravity = Gravity.CENTER_VERTICAL | Gravity.END;

        btn.setOnClickListener(v -> tts.speak("ระบบพร้อมทำงานค่ะ", TextToSpeech.QUEUE_FLUSH, null, null));
        windowManager.addView(btn, params);
        floatingView = btn;
    }

    @Override public void onAccessibilityEvent(AccessibilityEvent event) {}
    @Override public void onInterrupt() {}
    @Override public void onDestroy() {
        if (floatingView != null) windowManager.removeView(floatingView);
        if (tts != null) tts.shutdown();
        super.onDestroy();
    }
}
