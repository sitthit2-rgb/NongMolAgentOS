package com.nongmol.agent.v2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.*;
import android.graphics.Color;
import java.io.*;
import java.nio.channels.FileChannel;

public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // รับคำสั่งจาก Terminal (Intent)
        handleIntent(getIntent());

        LinearLayout root = new LinearLayout(this);
        root.setPadding(50, 50, 50, 50);
        root.setBackgroundColor(Color.BLACK);
        TextView tv = new TextView(this);
        tv.setText("SYSTEM READY\nWaiting for CLI Command...");
        tv.setTextColor(Color.GREEN);
        root.addView(tv);
        setContentView(root);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        if (intent != null && "COPY_MODEL".equals(intent.getAction())) {
            copyModelTask();
        }
    }

    private void copyModelTask() {
        File source = new File("/sdcard/002/models/Qwen3.5-0.8B-BF16.gguf");
        File destDir = new File(getFilesDir(), "models");
        if (!destDir.exists()) destDir.mkdirs();
        File dest = new File(destDir, "brain.gguf");

        try (FileChannel src = new FileInputStream(source).getChannel();
             FileChannel dst = new FileOutputStream(dest).getChannel()) {
            dst.transferFrom(src, 0, src.size());
            android.util.Log.d("NONGMOL", "Copy Success!");
        } catch (Exception e) {
            android.util.Log.e("NONGMOL", "Copy Failed: " + e.getMessage());
        }
    }
}
