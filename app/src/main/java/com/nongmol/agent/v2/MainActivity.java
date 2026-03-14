package com.nongmol.agent.v2;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.*;
import java.io.File;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;

public class MainActivity extends Activity {
    private final String BASE_PATH = Environment.getExternalStorageDirectory().getPath() + "/002";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // ขอสิทธิ์เข้าถึงไฟล์ทั้งหมด (สำหรับ Android 11+)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (!Environment.isExternalStorageManager()) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
                intent.setData(Uri.parse("package:" + getPackageName()));
                startActivity(intent);
            }
        }

        setupUI();
    }

    private void setupUI() {
        LinearLayout root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setBackgroundColor(Color.parseColor("#121212"));
        root.setPadding(60, 100, 60, 60);

        TextView statusLabel = new TextView(this);
        statusLabel.setText("--- SYSTEM DIAGNOSTIC ---");
        statusLabel.setTextColor(Color.GRAY);
        root.addView(statusLabel);

        // เช็กไฟล์ในเครื่องจริงๆ
        String[] files = {BASE_PATH+"/models/brain_llama3.gguf", BASE_PATH+"/ear/whisper_base.bin"};
        String[] names = {"BRAIN CONNECTED", "EAR CONNECTED"};

        for(int i=0; i<files.length; i++) {
            File file = new File(files[i]);
            TextView tv = new TextView(this);
            if(file.exists()) {
                tv.setText("✅ " + names[i]);
                tv.setTextColor(Color.GREEN);
            } else {
                tv.setText("❌ Missing: " + files[i]);
                tv.setTextColor(Color.RED);
            }
            root.addView(tv);
        }

        Button btn = new Button(this);
        btn.setText("START NEURAL AGENT");
        btn.setOnClickListener(v -> {
            startActivity(new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS));
            Toast.makeText(this, "กรุณาเปิด 'NongMol Agent' ในรายการ", Toast.LENGTH_LONG).show();
        });
        root.addView(btn);

        setContentView(root);
    }
}
