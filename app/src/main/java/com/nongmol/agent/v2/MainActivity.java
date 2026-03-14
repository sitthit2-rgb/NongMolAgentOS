package com.nongmol.agent.v2;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.widget.*;
import android.graphics.Color;
import java.io.*;
import java.nio.channels.FileChannel;

public class MainActivity extends Activity {
    private TextView logText;
    private final String EXTERNAL_MODEL_DIR = Environment.getExternalStorageDirectory() + "/002/models";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        LinearLayout root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setPadding(60, 60, 60, 60);
        root.setBackgroundColor(Color.parseColor("#050505"));

        TextView title = new TextView(this);
        title.setText("NONGMOL AUTO-SYNC");
        title.setTextSize(24);
        title.setTextColor(Color.GREEN);
        root.addView(title);

        logText = new TextView(this);
        logText.setText("\n🛰️ เริ่มการสแกนระบบ...");
        logText.setTextColor(Color.WHITE);
        root.addView(logText);

        setContentView(root);

        // เริ่มการทำงานอัตโนมัติ
        new Thread(this::autoSyncTask).start();
    }

    private void autoSyncTask() {
        try {
            File extDir = new File(EXTERNAL_MODEL_DIR);
            if (!extDir.exists()) {
                updateUI("❌ ไม่พบโฟลเดอร์ภายนอก: /002/models");
                return;
            }

            // สแกนหาไฟล์ .gguf แรกที่เจอ
            File[] files = extDir.listFiles((dir, name) -> name.endsWith(".gguf"));
            
            if (files != null && files.length > 0) {
                File sourceFile = files[0];
                File destDir = new File(getFilesDir(), "models");
                if (!destDir.exists()) destDir.mkdirs();
                File destFile = new File(destDir, "brain.gguf");

                updateUI("🔍 พบโมเดล: " + sourceFile.getName());

                // ตรวจสอบว่าต้อง Copy ไหม (เช็คขนาดไฟล์เพื่อความเร็ว)
                if (!destFile.exists() || destFile.length() != sourceFile.length()) {
                    updateUI("📥 กำลังติดตั้งโมเดลเข้าสู่ระบบ...\n(กรุณารอสักครู่ ห้ามปิดแอป)");
                    copyFile(sourceFile, destFile);
                    updateUI("✅ ติดตั้งสำเร็จ!");
                } else {
                    updateUI("⚡ โมเดลพร้อมใช้งานแล้ว (ไม่ต้องคัดลอกซ้ำ)");
                }
            } else {
                updateUI("❓ ไม่พบไฟล์ .gguf ใน /002/models");
            }
        } catch (Exception e) {
            updateUI("⚠️ เกิดข้อผิดพลาด: " + e.getMessage());
        }
    }

    private void copyFile(File src, File dst) throws IOException {
        try (FileChannel in = new FileInputStream(src).getChannel();
             FileChannel out = new FileOutputStream(dst).getChannel()) {
            out.transferFrom(in, 0, in.size());
        }
    }

    private void updateUI(String msg) {
        runOnUiThread(() -> logText.append("\n" + msg));
    }
}
