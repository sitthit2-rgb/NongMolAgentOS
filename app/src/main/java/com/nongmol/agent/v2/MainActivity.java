package com.nongmol.agent.v2;

import android.app.Activity;
import android.os.Bundle;
import android.widget.*;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.Gravity;
import java.io.File;

public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // สร้างโฟลเดอร์ภายในแอป (ไม่ต้องขอสิทธิ์ไฟล์ภายนอก)
        File modelDir = new File(getFilesDir(), "models");
        if (!modelDir.exists()) modelDir.mkdirs();

        LinearLayout root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setPadding(60, 80, 60, 80);
        root.setBackgroundColor(Color.parseColor("#050508"));

        TextView header = new TextView(this);
        header.setText("NONGMOL AGENT V15.5");
        header.setTextSize(28);
        header.setTextColor(Color.parseColor("#00FFCC"));
        header.setGravity(Gravity.CENTER);
        header.setTypeface(Typeface.DEFAULT_BOLD);
        root.addView(header);

        // แสดง Path ที่พี่ต้องเอาไฟล์ไปวาง
        TextView pathLabel = new TextView(this);
        pathLabel.setText("\n📂 ที่เก็บโมเดล (นำไฟล์มาวางที่นี่):");
        pathLabel.setTextColor(Color.WHITE);
        root.addView(pathLabel);

        TextView pathValue = new TextView(this);
        pathValue.setText(modelDir.getAbsolutePath());
        pathValue.setTextColor(Color.YELLOW);
        pathValue.setTextSize(12);
        pathValue.setTextIsSelectable(true); // ให้กดค้างก๊อปปี้ได้
        root.addView(pathValue);

        root.addView(createStatusRow("🧠 BRAIN", new File(modelDir, "brain.gguf")));
        root.addView(createStatusRow("👁️ VISION", new File(modelDir, "vision.gguf")));
        root.addView(createStatusRow("👂 EAR", new File(modelDir, "whisper.bin")));

        Button btnCheck = new Button(this);
        btnCheck.setText("RE-SCAN MODELS");
        btnCheck.setBackgroundColor(Color.parseColor("#00FFCC"));
        btnCheck.setTextColor(Color.BLACK);
        btnCheck.setOnClickListener(v -> recreate());
        
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(-1, -2);
        params.setMargins(0, 50, 0, 0);
        root.addView(btnCheck, params);

        setContentView(root);
    }

    private LinearLayout createStatusRow(String label, File file) {
        LinearLayout row = new LinearLayout(this);
        row.setPadding(0, 20, 0, 20);
        
        TextView tvLabel = new TextView(this);
        tvLabel.setText(label);
        tvLabel.setTextColor(Color.WHITE);
        tvLabel.setLayoutParams(new LinearLayout.LayoutParams(0, -2, 1f));
        
        TextView tvStatus = new TextView(this);
        boolean exists = file.exists();
        tvStatus.setText(exists ? "● ONLINE" : "○ OFFLINE");
        tvStatus.setTextColor(exists ? Color.GREEN : Color.RED);
        
        row.addView(tvLabel);
        row.addView(tvStatus);
        return row;
    }
}
