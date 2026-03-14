package com.nongmol.agent.v2;

import android.app.Activity;
import android.os.Bundle;
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
    // ระบุตำแหน่ง Path ตรงๆ ตามที่พี่แจ้งมา
    private final String ACTUAL_PATH = "/storage/emulated/0/002/models";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // UI Layout
        LinearLayout root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setBackgroundColor(Color.parseColor("#0A0A12"));
        root.setPadding(50, 80, 50, 50);

        TextView title = new TextView(this);
        title.setText("NONGMOL AGENT V16.0");
        title.setTextSize(26);
        title.setTypeface(Typeface.DEFAULT_BOLD);
        title.setTextColor(Color.parseColor("#00FFCC"));
        title.setGravity(Gravity.CENTER);
        root.addView(title);

        LinearLayout panel = new LinearLayout(this);
        panel.setOrientation(LinearLayout.VERTICAL);
        panel.setPadding(40, 40, 40, 40);
        panel.setBackgroundColor(Color.parseColor("#161625"));
        
        statusLog = new TextView(this);
        statusLog.setText("🛰️ กำลังตรวจสอบไฟล์ที่:\n" + ACTUAL_PATH);
        statusLog.setTextColor(Color.WHITE);
        panel.addView(statusLog);

        progressBar = new ProgressBar(this, null, android.R.attr.progressBarStyleHorizontal);
        progressBar.setVisibility(View.GONE);
        panel.addView(progressBar);

        root.addView(panel);

        Button btnVoice = new Button(this);
        btnVoice.setText("🎤 สั่งการด้วยเสียง");
        btnVoice.setBackgroundColor(Color.parseColor("#00FFCC"));
        btnVoice.setTextColor(Color.BLACK);
        root.addView(btnVoice);

        setContentView(root);

        // เริ่ม Scan ทันที
        new Thread(this::scanAndSync).start();
    }

    private void scanAndSync() {
        try {
            File extDir = new File(ACTUAL_PATH);
            // หาไฟล์ที่มีคำว่า Qwen หรือนามสกุล .gguf
            File[] files = extDir.listFiles((dir, name) -> name.toLowerCase().endsWith(".gguf"));
            
            if (files != null && files.length > 0) {
                File src = files[0]; // เลือกไฟล์แรกที่เจอ
                File dst = new File(getFilesDir(), "models/brain.gguf");
                if (!dst.getParentFile().exists()) dst.getParentFile().mkdirs();

                if (!dst.exists() || dst.length() != src.length()) {
                    updateUI("📥 พบไฟล์: " + src.getName() + "\nกำลังย้ายเข้าหน่วยความจำแอป...", true);
                    copyFile(src, dst);
                    updateUI("✅ ติดตั้ง " + src.getName() + " สำเร็จ!", false);
                } else {
                    updateUI("🚀 ระบบพร้อมใช้งาน!\nสมอง: " + src.getName(), false);
                }
            } else {
                updateUI("❌ ไม่พบไฟล์ใน " + ACTUAL_PATH + "\nตรวจสอบว่าไฟล์อยู่ในโฟลเดอร์หรือยัง?", false);
            }
        } catch (Exception e) {
            updateUI("⚠️ ติดสิทธิ์การเข้าถึงไฟล์\nกรุณาเปิดสิทธิ์ 'จัดการไฟล์ทั้งหมด' ในตั้งค่า", false);
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
        });
    }
}
