package com.nongmol.agent.v2;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import android.graphics.Color;
import android.graphics.Typeface;
import com.nongmol.core.NongMolCore;

public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        ScrollView scroll = new ScrollView(this);
        LinearLayout root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setPadding(60, 80, 60, 80);
        root.setBackgroundColor(Color.parseColor("#050508")); 
        
        // Header: NongMol OS
        TextView header = new TextView(this);
        header.setText("NONGMOL AGENT V15");
        header.setTextSize(32);
        header.setTextColor(Color.parseColor("#00FFCC"));
        header.setTypeface(Typeface.create("sans-serif-condensed", Typeface.BOLD));
        root.addView(header);

        addDivider(root);

        // แสดงสถานะการเชื่อมต่อเส้นประสาท
        addStatusCard(root, "🧠 CORE BRAIN", NongMolCore.INSTANCE.getBRAIN_PATH());
        addStatusCard(root, "👁️ VISION EYE", NongMolCore.INSTANCE.getVISION_PATH());
        addStatusCard(root, "🦾 CLAW MOBILE", NongMolCore.INSTANCE.getENGINE_PATH());
        addStatusCard(root, "👂 WHISPER EAR", NongMolCore.INSTANCE.getEAR_PATH());

        // ปุ่มกระตุ้นระบบประสาท
        Button btnBoot = new Button(this);
        btnBoot.setText("CONNECT NEURAL LINK");
        btnBoot.setBackgroundColor(Color.parseColor("#1A1A2E"));
        btnBoot.setTextColor(Color.parseColor("#00FFCC"));
        
        LinearLayout.LayoutParams btnParams = new LinearLayout.LayoutParams(-1, -2);
        btnParams.setMargins(0, 60, 0, 0);
        root.addView(btnBoot, btnParams);

        scroll.addView(root);
        setContentView(scroll);
    }

    private void addStatusCard(LinearLayout root, String label, java.io.File file) {
        LinearLayout card = new LinearLayout(this);
        card.setOrientation(LinearLayout.HORIZONTAL);
        card.setPadding(20, 40, 20, 40);
        
        // แก้ไข Margin ที่ทำให้ Error รอบที่แล้ว
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(-1, -2);
        params.setMargins(0, 15, 0, 15);
        card.setLayoutParams(params);
        card.setBackgroundColor(Color.parseColor("#12121A"));

        TextView tvLabel = new TextView(this);
        tvLabel.setText(label);
        tvLabel.setTextColor(Color.LTGRAY);
        tvLabel.setLayoutParams(new LinearLayout.LayoutParams(0, -2, 1.0f));

        TextView tvStatus = new TextView(this);
        boolean exists = file.exists();
        tvStatus.setText(exists ? "● ONLINE" : "○ OFFLINE");
        tvStatus.setTextColor(exists ? Color.GREEN : Color.RED);
        tvStatus.setTypeface(null, Typeface.BOLD);

        card.addView(tvLabel);
        card.addView(tvStatus);
        root.addView(card);
    }

    private void addDivider(LinearLayout root) {
        View v = new View(this);
        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(-1, 3);
        p.setMargins(0, 30, 0, 50);
        v.setLayoutParams(p);
        v.setBackgroundColor(Color.parseColor("#333333"));
        root.addView(v);
    }
}
