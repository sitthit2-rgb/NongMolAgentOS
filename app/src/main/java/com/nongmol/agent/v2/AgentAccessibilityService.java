package com.nongmol.agent.v2;

import android.accessibilityservice.AccessibilityService;
import android.graphics.PixelFormat;
import android.view.Gravity;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityEvent;
import android.widget.Button;
import android.widget.Toast;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.speech.tts.TextToSpeech;
import java.util.Locale;

public class AgentAccessibilityService extends AccessibilityService {
    private WindowManager windowManager;
    private View floatingView;
    private Button agentButton;
    
    // ระบบเสียง
    private boolean isRecording = false;
    private AudioRecord audioRecord;
    private TextToSpeech tts;

    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();
        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        
        // เตรียมระบบพูดตอบกลับ (TTS)
        tts = new TextToSpeech(this, status -> {
            if (status == TextToSpeech.SUCCESS) {
                tts.setLanguage(new Locale("th", "TH")); // ตั้งเสียงภาษาไทย
            }
        });

        agentButton = new Button(this);
        agentButton.setText("🤖 NONGMOL (กดเพื่อพูด)");
        agentButton.setTextColor(Color.WHITE);
        
        GradientDrawable bg = new GradientDrawable();
        bg.setColor(Color.parseColor("#99000000")); // ดำโปร่งแสง
        bg.setCornerRadius(50f);
        bg.setStroke(3, Color.CYAN);
        agentButton.setBackground(bg);

        WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_ACCESSIBILITY_OVERLAY,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);
        params.gravity = Gravity.TOP | Gravity.START;
        params.x = 0;
        params.y = 300;

        // ระบบกดเพื่อสลับการฟังเสียง (Toggle Recording)
        agentButton.setOnClickListener(v -> {
            if (!isRecording) {
                startListening();
            } else {
                stopListeningAndProcess();
            }
        });

        windowManager.addView(agentButton, params);
        floatingView = agentButton;
    }

    private void startListening() {
        isRecording = true;
        agentButton.setText("🔴 กำลังฟัง...");
        agentButton.setTextColor(Color.RED);
        Toast.makeText(this, "เริ่มบันทึกเสียง...", Toast.LENGTH_SHORT).show();
        
        // TODO: ในโลกจริง ต้องดึง Raw Audio ผ่าน AudioRecord ส่งเข้า JNI ให้ Whisper.cpp
        // โค้ดเตรียมพร้อมโครงสร้าง AudioRecord ไว้แล้ว
    }

    private void stopListeningAndProcess() {
        isRecording = false;
        agentButton.setText("🤖 NONGMOL (กำลังคิด)");
        agentButton.setTextColor(Color.YELLOW);
        
        // จำลองสถานการณ์: ส่งเสียงเข้าสมอง แล้วให้ระบบพูดตอบกลับ
        Toast.makeText(this, "กำลังประมวลผลเสียง...", Toast.LENGTH_SHORT).show();
        
        // จำลอง AI พูดตอบกลับ
        new android.os.Handler().postDelayed(() -> {
            agentButton.setText("🤖 NONGMOL (กดเพื่อพูด)");
            agentButton.setTextColor(Color.WHITE);
            tts.speak("ได้รับคำสั่งเสียงแล้ว กำลังวิเคราะห์หน้าจอค่ะ", TextToSpeech.QUEUE_FLUSH, null, null);
        }, 1500);
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {}
    @Override
    public void onInterrupt() {}

    @Override
    public boolean onUnbind(android.content.Intent intent) {
        if (floatingView != null) windowManager.removeView(floatingView);
        if (tts != null) { tts.stop(); tts.shutdown(); }
        return super.onUnbind(intent);
    }
}
