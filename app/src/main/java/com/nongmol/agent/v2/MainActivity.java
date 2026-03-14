package com.nongmol.agent.v2;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.widget.*;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.View;
import java.io.*;
import java.nio.channels.FileChannel;

public class MainActivity extends Activity {
    private TextView statusLog;
    private ProgressBar progressBar;
    private final String EXTERNAL_PATH = Environment.getExternalStorageDirectory() + "/002/models";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // --- UI Design (คืนชีพความสวยงาม) ---
        LinearLayout root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setBackgroundColor(Color.parseColor("#0A0A12"));
        root.setPadding(50, 80, 50, 50);

        // Header
        TextView title = new TextView(this);
        title.setText("NONGMOL AGENT V15.9");
        title.setTextSize(26);
        title.setTypeface(Typeface.DEFAULT_BOLD);
        title.setTextColor(Color.parseColor("#00FFCC"));
        title.setGravity(Gravity.CENTER);
        root.addView(title);

        // Panel แสดงสถานะ
        LinearLayout panel = new LinearLayout(this);
        panel.setOrientation(LinearLayout.VERTICAL);
        panel.setPadding(40, 40, 40, 40);
        panel.setBackgroundColor(Color.parseColor("#161625"));
        
        statusLog = new TextView(this);
        statusLog.setText("🛰️ กำลังตรวจสอบระบบ...");
        statusLog.setTextColor(Color.WHITE);
        statusLog.setTextSize(14);
        panel.addView(statusLog);

        progressBar = new ProgressBar(this, null, android.R.attr.progressBarStyleHorizontal);
        progressBar.setVisibility(View.GONE);
        panel.addView(progressBar);

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(-1, -2);
        lp.setMargins(0, 40, 0, 40);
        root.addView(panel, lp);

        // ปุ่มสั่งการ (Voice Command) - จำลองระบบเสียง
        Button btnVoice = new Button(this);
        btnVoice.setText("🎤 สั่งการด้วยเสียง");
        btnVoice.setBackgroundColor(Color.parseColor("#00FFCC"));
        btnVoice.setTextColor(Color.BLACK);
        btnVoice.setPadding(0, 40, 0, 40);
        btnVoice.setOnClickListener(v -> {
            Toast.makeText(this, "กำลังฟังเสียง...", Toast.LENGTH_SHORT).show();
            // พี่สามารถเชื่อมฟังก์ชัน STT ตรงนี้ได้เลย
        });
        root.addView(btnVoice);

        // ปุ่มแชท
        Button btnChat = new Button(this);
        btnChat.setText("💬 พิมพ์คุยกับ AI");
        btnChat.setTextColor(Color.WHITE);
        btnChat.setPadding(0, 40, 0, 40);
        btnChat.setBackgroundColor(Color.TRANSPARENT);
        root.addView(btnChat);

        setContentView(root);

        // เริ่มระบบ Auto-Sync หลังบ้าน
        new Thread(this::runAutoEngine).start();
    }

    private void runAutoEngine() {
        try {
            File extDir = new File(EXTERNAL_PATH);
            File[] files = extDir.listFiles((dir, name) -> name.endsWith(".gguf"));
            
            if (files != null && files.length > 0) {
                File src = files[0];
                File dst = new File(getFilesDir(), "models/brain.gguf");
                if (!dst.getParentFile().exists()) dst.getParentFile().mkdirs();

                if (!dst.exists() || dst.length() != src.length()) {
                    updateUI("📥 พบโมเดルใหม่: " + src.getName() + "\nกำลังติดตั้งเข้า Engine...", true);
                    copyFile(src, dst);
                    updateUI("✅ ติดตั้งสำเร็จ! ระบบพร้อมทำงาน", false);
                } else {
                    updateUI("🚀 Engine พร้อม! โมเดล: " + src.getName(), false);
                }
            } else {
                updateUI("⚠️ ไม่พบไฟล์โมเดลที่ /002/models/\nกรุณานำไฟล์ .gguf ไปวาง", false);
            }
        } catch (Exception e) {
            updateUI("❌ Error: " + e.getMessage(), false);
        }
    }

    private void copyFile(File src, File dst) throws IOException {
        try (FileChannel in = new FileInputStream(src).getChannel();
             FileChannel out = new FileOutputStream(dst).getChannel()) {
            out.transferFrom(in, 0, in.size());
        }
    }

    private void updateUI(String msg, boolean showProgress) {
        runOnUiThread(() -> {
            statusLog.setText(msg);
            progressBar.setVisibility(showProgress ? View.VISIBLE : View.GONE);
            if (showProgress) progressBar.setIndeterminate(true);
        });
    }
}
