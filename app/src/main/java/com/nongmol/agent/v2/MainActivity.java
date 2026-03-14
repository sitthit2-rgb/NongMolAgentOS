package com.nongmol.agent.v2;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.*;
import java.io.File;

public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        ScrollView scroll = new ScrollView(this);
        scroll.setBackgroundColor(Color.parseColor("#0F172A"));

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(60, 80, 60, 80);
        layout.setGravity(Gravity.CENTER_HORIZONTAL);

        // Logo / Title
        TextView header = new TextView(this);
        header.setText("NONGMOL AGENT OS");
        header.setTextColor(Color.parseColor("#38BDF8"));
        header.setTextSize(28);
        header.setTypeface(null, Typeface.BOLD);
        header.setPadding(0, 0, 0, 60);
        layout.addView(header);

        // Status Cards
        addCard(layout, "NEURAL BRAIN (LLM)", "/storage/emulated/0/002/models/Qwen3.5-0.8B-BF16.gguf");
        addCard(layout, "VOICE ENGINE (STT)", "/storage/emulated/0/002/ear/whisper_base.bin");

        // Action Button
        Button btnLaunch = new Button(this);
        btnLaunch.setText("START NEURAL SERVICE");
        btnLaunch.setAllCaps(true);
        btnLaunch.setPadding(40, 40, 40, 40);
        btnLaunch.setBackgroundColor(Color.parseColor("#38BDF8"));
        btnLaunch.setTextColor(Color.WHITE);
        
        // ใส่ Margin ให้ปุ่มนิดหน่อย
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT, 
            LinearLayout.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(0, 50, 0, 0);
        btnLaunch.setLayoutParams(params);
        
        layout.addView(btnLaunch);

        scroll.addView(layout);
        setContentView(scroll);
    }

    private void addCard(LinearLayout parent, String title, String path) {
        LinearLayout card = new LinearLayout(this);
        card.setOrientation(LinearLayout.VERTICAL);
        card.setPadding(45, 45, 45, 45);
        card.setBackgroundColor(Color.parseColor("#1E293B"));
        
        TextView t = new TextView(this);
        t.setText(title);
        t.setTextColor(Color.parseColor("#94A3B8"));
        t.setTextSize(14);
        card.addView(t);

        TextView s = new TextView(this);
        File f = new File(path);
        if(f.exists()) {
            s.setText("● SYSTEM ONLINE");
            s.setTextColor(Color.parseColor("#10B981"));
        } else {
            s.setText("○ FILE MISSING");
            s.setTextColor(Color.parseColor("#EF4444"));
        }
        s.setTypeface(null, Typeface.BOLD);
        s.setPadding(0, 10, 0, 0);
        card.addView(s);
        
        parent.addView(card);

        // ตัวคั่น (Space) - แก้ปัญหาที่ทำให้ Build ล้มเหลว
        View space = new View(this);
        space.setMinimumHeight(35);
        parent.addView(space);
    }
}
