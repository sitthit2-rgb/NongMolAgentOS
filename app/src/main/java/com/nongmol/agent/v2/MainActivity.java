package com.nongmol.agent.v2;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.view.Gravity;
import android.widget.*;
import java.io.File;

public class MainActivity extends Activity {
    // ชี้เป้าไปที่ไฟล์ในเครื่องพี่เหมือนเดิม (เสถียรที่สุด)
    private final String MODEL_PATH = "/storage/emulated/0/002/models/Qwen3.5-0.8B-BF16.gguf";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupUI();
    }

    private void setupUI() {
        LinearLayout root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setBackgroundColor(Color.BLACK);
        root.setPadding(60, 100, 60, 60);
        root.setGravity(Gravity.CENTER_HORIZONTAL);

        TextView status = new TextView(this);
        File modelFile = new File(MODEL_PATH);
        
        if (modelFile.exists()) {
            status.setText("✅ SYSTEM READY\nBrain: Qwen3.5 Found");
            status.setTextColor(Color.GREEN);
        } else {
            status.setText("❌ BRAIN NOT FOUND\nPath: " + MODEL_PATH);
            status.setTextColor(Color.RED);
        }
        status.setGravity(Gravity.CENTER);
        root.addView(status);

        Button btn = new Button(this);
        btn.setText("OPEN PERMISSIONS");
        btn.setOnClickListener(v -> startActivity(new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)));
        root.addView(btn);

        setContentView(root);
    }
}
