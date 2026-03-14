package com.nongmol.agent.v2;

import android.accessibilityservice.AccessibilityService;
import android.graphics.PixelFormat;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityEvent;
import android.widget.Button;
import android.widget.Toast;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.media.AudioRecord;
import android.speech.tts.TextToSpeech;
import java.util.Locale;

public class AgentAccessibilityService extends AccessibilityService {
    private WindowManager windowManager;
    private View floatingView;
    private Button agentButton;
    private TextToSpeech tts;
    private boolean isActive = false;

    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();
        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        
        // Setup World-Class TTS
        tts = new TextToSpeech(this, status -> {
            if (status == TextToSpeech.SUCCESS) tts.setLanguage(new Locale("th", "TH"));
        });

        // Floating Action Button (Neural Link Style)
        agentButton = new Button(this);
        updateButtonState(false);
        
        WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_ACCESSIBILITY_OVERLAY,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);
        params.gravity = Gravity.CENTER_VERTICAL | Gravity.END; // วางไว้ขอบขวาตรงกลาง
        
        agentButton.setOnClickListener(v -> {
            isActive = !isActive;
            updateButtonState(isActive);
            if (isActive) {
                tts.speak("ระบบเชื่อมต่อประสาทพร้อมรับคำสั่ง", TextToSpeech.QUEUE_FLUSH, null, null);
            }
        });

        windowManager.addView(agentButton, params);
        floatingView = agentButton;
    }

    private void updateButtonState(boolean active) {
        agentButton.setText(active ? "◉ ACTIVE" : "○ NM-OS");
        agentButton.setTextSize(12);
        agentButton.setTextColor(active ? Color.parseColor("#00FFCC") : Color.WHITE);
        
        GradientDrawable gd = new GradientDrawable();
        gd.setColor(active ? Color.parseColor("#CC000000") : Color.parseColor("#8810101A"));
        gd.setCornerRadius(100f);
        gd.setStroke(4, active ? Color.parseColor("#00FFCC") : Color.parseColor("#33FFFFFF"));
        agentButton.setBackground(gd);
        agentButton.setPadding(30, 30, 30, 30);
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {}
    @Override
    public void onInterrupt() {}
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (floatingView != null) windowManager.removeView(floatingView);
        if (tts != null) tts.shutdown();
    }
}
