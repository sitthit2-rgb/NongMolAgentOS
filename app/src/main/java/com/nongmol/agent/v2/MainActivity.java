package com.nongmol.agent.v2;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.widget.*;
import java.io.File;

public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LinearLayout root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setBackgroundColor(Color.BLACK);
        root.setPadding(50, 50, 50, 50);

        TextView head = new TextView(this);
        head.setText("NONGMOL AGENT V2\n[CORE STATUS]");
        head.setTextColor(Color.WHITE);
        head.setTextSize(20);
        root.addView(head);

        // เช็กจุดเชื่อมต่อสำคัญ
        String basePath = Environment.getExternalStorageDirectory().getPath() + "/002";
        checkFile(root, "Brain (.gguf)", basePath + "/models/brain_llama3.gguf");
        checkFile(root, "Ear (.bin)", basePath + "/ear/whisper_base.bin");

        Button btn = new Button(this);
        btn.setText("OPEN SETTINGS");
        btn.setOnClickListener(v -> startActivity(new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)));
        root.addView(btn);

        setContentView(root);
    }

    private void checkFile(LinearLayout layout, String label, String path) {
        TextView tv = new TextView(this);
        File f = new File(path);
        tv.setText((f.exists() ? "✅ " : "❌ ") + label);
        tv.setTextColor(f.exists() ? Color.GREEN : Color.RED);
        tv.setPadding(0, 20, 0, 20);
        layout.addView(tv);
    }
}
