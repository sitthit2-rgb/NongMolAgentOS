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
        
        // สร้าง UI แบบ Dynamic ที่สวยงามขึ้น
        ScrollView scroll = new ScrollView(this);
        LinearLayout root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setPadding(60, 80, 60, 80);
        root.setBackgroundColor(Color.parseColor("#0A0A0F")); // Deep Space Blue
        
        // Header
        TextView header = new TextView(this);
        header.setText("NONGMOL AGENT OS");
        header.setTextSize(28);
        header.setTextColor(Color.parseColor("#00FFCC")); // Cyber Neon
        header.setTypeface(null, Typeface.BOLD);
        root.addView(header);

        addDivider(root);

        // ระบบตรวจสอบสถานะ (Diagnostics)
        addStatusCard(root, "ENGINE UNIT", NongMolCore.INSTANCE.getENGINE_PATH());
        addStatusCard(root, "NEURAL BRAIN", NongMolCore.INSTANCE.getBRAIN_PATH());
        addStatusCard(root, "VISION SENSOR", NongMolCore.INSTANCE.getVISION_PATH());
        addStatusCard(root, "AUDITORY EAR", NongMolCore.INSTANCE.getEAR_PATH());

        // ปุ่มควบคุม
        Button btnBoot = new Button(this);
        btnBoot.setText("INITIALIZE NEURAL LINK");
        btnBoot.setBackgroundColor(Color.parseColor("#1A1A2E"));
        btnBoot.setTextColor(Color.WHITE);
        btnBoot.setPadding(20, 40, 20, 40);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(-1, -2);
        lp.setMargins(0, 50, 0, 0);
        root.addView(btnBoot, lp);

        scroll.addView(root);
        setContentView(scroll);
    }

    private void addStatusCard(LinearLayout root, String label, java.io.File file) {
        LinearLayout card = new LinearLayout(this);
        card.setOrientation(LinearLayout.HORIZONTAL);
        card.setPadding(30, 30, 30, 30);
        card.setMargins(0, 10, 0, 10);
        
        TextView tvLabel = new TextView(this);
        tvLabel.setText(label);
        tvLabel.setTextColor(Color.GRAY);
        tvLabel.setLayoutParams(new LinearLayout.LayoutParams(0, -2, 1f));

        TextView tvStatus = new TextView(this);
        boolean exists = file.exists();
        tvStatus.setText(exists ? "ONLINE" : "OFFLINE");
        tvStatus.setTextColor(exists ? Color.GREEN : Color.RED);
        tvStatus.setTypeface(null, Typeface.BOLD);

        card.addView(tvLabel);
        card.addView(tvStatus);
        root.addView(card);
    }

    private void addDivider(LinearLayout root) {
        View v = new View(this);
        v.setLayoutParams(new LinearLayout.LayoutParams(-1, 2));
        v.setBackgroundColor(Color.DKGRAY);
        root.addView(v);
    }
}
