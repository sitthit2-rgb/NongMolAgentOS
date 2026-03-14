package com.nongmol.agent.v2;

import android.app.Activity;
import android.os.Bundle;
import android.widget.*;
import android.graphics.Color;
import java.io.*;
import java.nio.channels.FileChannel;

public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        File modelDir = new File(getFilesDir(), "models");
        if (!modelDir.exists()) modelDir.mkdirs();

        LinearLayout root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setPadding(50, 50, 50, 50);
        root.setBackgroundColor(Color.BLACK);

        TextView tv = new TextView(this);
        tv.setText("MODEL IMPORTER V15.6\n");
        tv.setTextColor(Color.CYAN);
        root.addView(tv);

        // ปุ่มพิเศษสำหรับย้ายไฟล์จาก 002 เข้ามาในแอป
        Button btnImport = new Button(this);
        btnImport.setText("🚀 IMPORT FROM /sdcard/002/");
        btnImport.setOnClickListener(v -> {
            File source = new File("/sdcard/002/models/Qwen3.5-0.8B-BF16.gguf");
            File dest = new File(modelDir, "brain.gguf");
            try {
                copyFile(source, dest);
                Toast.makeText(this, "✅ Import Success!", Toast.LENGTH_SHORT).show();
                recreate();
            } catch (IOException e) {
                Toast.makeText(this, "❌ Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        root.addView(btnImport);

        // แสดงสถานะไฟล์
        checkFile(root, modelDir, "brain.gguf");

        setContentView(root);
    }

    private void checkFile(LinearLayout root, File dir, String name) {
        TextView tv = new TextView(this);
        File f = new File(dir, name);
        tv.setText(name + ": " + (f.exists() ? "READY ✅" : "MISSING ❌"));
        tv.setTextColor(f.exists() ? Color.GREEN : Color.RED);
        root.addView(tv);
    }

    private void copyFile(File source, File dest) throws IOException {
        try (FileChannel src = new FileInputStream(source).getChannel();
             FileChannel dst = new FileOutputStream(dest).getChannel()) {
            dst.transferFrom(src, 0, src.size());
        }
    }
}
