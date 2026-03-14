package com.nongmol.agent.v2;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.graphics.Color;
import java.io.File;

public class MainActivity extends Activity {
    private LinearLayout statusLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        statusLayout = new LinearLayout(this);
        statusLayout.setOrientation(LinearLayout.VERTICAL);
        statusLayout.setPadding(50, 50, 50, 50);
        statusLayout.setBackgroundColor(0xFF121212); // สีดำเท่ๆ
        setContentView(statusLayout);

        checkPermissions();
        refreshFileStatus();
    }

    private void checkPermissions() {
        if (!Environment.isExternalStorageManager()) {
            try {
                Intent intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
                intent.setData(Uri.parse("package:" + getPackageName()));
                startActivity(intent);
            } catch (Exception e) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
                startActivity(intent);
            }
        }
    }

    private void refreshFileStatus() {
        statusLayout.removeAllViews();
        addStatusTitle("NongMolAgentOS - System Check");

        checkFile("/002/engine/libllama.so", "Engine (Llama Core)");
        checkFile("/002/models/brain_llama3.gguf", "Brain (Llama3)");
        checkFile("/002/models/vision_llama3.gguf", "Vision (Screen Reader)");
        checkFile("/002/ear/whisper_base.bin", "Ear (Speech-to-Text)");
    }

    private void checkFile(String path, String label) {
        File file = new File(Environment.getExternalStorageDirectory(), path);
        TextView tv = new TextView(this);
        if (file.exists()) {
            tv.setText("✅ " + label + ": Found");
            tv.setTextColor(Color.GREEN);
        } else {
            tv.setText("❌ " + label + ": Missing\n   -> " + path);
            tv.setTextColor(Color.RED);
        }
        tv.setTextSize(16);
        tv.setPadding(0, 10, 0, 10);
        statusLayout.addView(tv);
    }

    private void addStatusTitle(String text) {
        TextView title = new TextView(this);
        title.setText(text);
        title.setTextColor(Color.WHITE);
        title.setTextSize(22);
        title.setPadding(0, 0, 0, 40);
        statusLayout.addView(title);
    }
}
