package com.nongmol.agent.v2;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.speech.tts.TextToSpeech;
import android.view.Gravity;
import android.widget.*;
import java.io.File;
import java.util.Locale;

public class MainActivity extends Activity {
    // ล็อกเป้าหมายไปที่ไฟล์ของพี่โดยตรง ไม่ต้องเปลี่ยนชื่อไฟล์แล้ว
    private final String BRAIN_PATH = "/storage/emulated/0/002/models/Qwen3.5-0.8B-BF16.gguf";
    private TextToSpeech tts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // เตรียมระบบ "ปาก" (TTS)
        tts = new TextToSpeech(this, status -> {
            if (status == TextToSpeech.SUCCESS) tts.setLanguage(new Locale("th", "TH"));
        });

        setupIntegratedUI();
    }

    private void setupIntegratedUI() {
        LinearLayout root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setBackgroundColor(Color.parseColor("#050505"));
        root.setPadding(50, 80, 50, 50);
        root.setGravity(Gravity.CENTER_HORIZONTAL);

        TextView title = new TextView(this);
        title.setText("NONGMOL ALL-IN-ONE SYSTEM");
        title.setTextColor(Color.CYAN);
        title.setTextSize(22);
        root.addView(title);

        // เช็คการเชื่อมต่อไฟล์จริง
        File brainFile = new File(BRAIN_PATH);
        TextView status = new TextView(this);
        status.setPadding(0, 40, 0, 40);
        
        if (brainFile.exists()) {
            status.setText("✅ BRAIN CONNECTED\n(Qwen3.5-0.8B Detected)");
            status.setTextColor(Color.GREEN);
            tts.speak("ระบบเชื่อมต่อสมองสำเร็จแล้วค่ะ", TextToSpeech.QUEUE_FLUSH, null, null);
        } else {
            status.setText("❌ BRAIN DISCONNECTED\nSearching for: " + BRAIN_PATH);
            status.setTextColor(Color.RED);
        }
        root.addView(status);

        // ปุ่มเปิดระบบทั้งหมด
        Button btn = new Button(this);
        btn.setText("ACTIVATE ALL SYSTEMS");
        btn.setBackgroundColor(Color.DKGRAY);
        btn.setTextColor(Color.WHITE);
        btn.setOnClickListener(v -> {
            startActivity(new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS));
            Toast.makeText(this, "กรุณาเปิดการอนุญาต NongMol ในหน้าตั้งค่า", Toast.LENGTH_LONG).show();
        });
        root.addView(btn);

        setContentView(root);
    }

    @Override
    protected void onDestroy() {
        if (tts != null) tts.shutdown();
        super.onDestroy();
    }
}
