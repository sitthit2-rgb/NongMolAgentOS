package com.nongmol.agent.v2;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Button;
import android.graphics.Color;
import java.io.File;
import com.nongmol.core.NongMolCore;

public class MainActivity extends Activity {
    private LinearLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(50, 50, 50, 50);
        layout.setBackgroundColor(Color.parseColor("#121212"));
        setContentView(layout);

        checkPermissions();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Environment.isExternalStorageManager()) {
            drawUI();
        }
    }

    private void checkPermissions() {
        if (!Environment.isExternalStorageManager()) {
            Intent intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
            intent.setData(Uri.parse("package:" + getPackageName()));
            startActivity(intent);
        }
    }

    private void drawUI() {
        layout.removeAllViews();
        
        TextView title = new TextView(this);
        title.setText("NongMol OS - Diagnostics");
        title.setTextColor(Color.WHITE);
        title.setTextSize(24);
        title.setPadding(0, 0, 0, 30);
        layout.addView(title);

        boolean allGreen = true;
        allGreen &= checkFile("/002/engine/libllama.so", "1. Engine (.so)");
        allGreen &= checkFile("/002/models/brain_llama3.gguf", "2. Brain (.gguf)");
        allGreen &= checkFile("/002/models/vision_llama3.gguf", "3. Vision (.gguf)");
        allGreen &= checkFile("/002/ear/whisper_base.bin", "4. Ear (.bin)");

        if (allGreen) {
            Button btnStart = new Button(this);
            btnStart.setText("BOOT AGENT CORE");
            btnStart.setOnClickListener(v -> bootSystem());
            layout.addView(btnStart);
        }
    }

    private boolean checkFile(String path, String label) {
        File file = new File(Environment.getExternalStorageDirectory(), path);
        TextView tv = new TextView(this);
        boolean exists = file.exists();
        tv.setText((exists ? "✅ " : "❌ ") + label);
        tv.setTextColor(exists ? Color.GREEN : Color.RED);
        tv.setTextSize(18);
        tv.setPadding(0, 10, 0, 10);
        layout.addView(tv);
        return exists;
    }

    private void bootSystem() {
        // Phase 1 & 2: เรียก Core ให้โหลด Engine และ Brain
        boolean isEngineUp = NongMolCore.INSTANCE.initEngine(this);
        if (isEngineUp && NongMolCore.INSTANCE.isBrainReady()) {
            // Phase 3: เปิดหน้าตั้งค่าให้ผู้ใช้เปิดสวิตช์ HandService (Accessibility)
            Intent intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
            startActivity(intent);
        }
    }
}
