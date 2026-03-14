package com.nongmol.agent.v2;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.*;
import java.io.File;

public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // พื้นหลังโทนเข้มแบบ Sci-Fi
        ScrollView scroll = new ScrollView(this);
        scroll.setBackgroundColor(Color.parseColor("#0F172A"));

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(60, 80, 60, 80);
        layout.setGravity(Gravity.CENTER_HORIZONTAL);

        // Header
        TextView header = new TextView(this);
        header.setText("NONGMOL AGENT OS");
        header.setTextColor(Color.parseColor("#38BDF8"));
        header.setTextSize(26);
        header.setTypeface(null, Typeface.BOLD);
        layout.addView(header);

        // Model Status Card
        addCard(layout, "NEURAL BRAIN", "/storage/emulated/0/002/models/Qwen3.5-0.8B-BF16.gguf");
        
        // Voice Status Card
        addCard(layout, "EAR/VOICE ENGINE", "/storage/emulated/0/002/ear/whisper_base.bin");

        // ปุ่ม Chat (ทางลัด)
        Button btnChat = new Button(this);
        btnChat.setText("OPEN NEURAL CHAT");
        btnChat.setBackgroundColor(Color.parseColor("#1E293B"));
        btnChat.setTextColor(Color.WHITE);
        layout.addView(btnChat);

        scroll.addView(layout);
        setContentView(scroll);
    }

    private void addCard(LinearLayout parent, String title, String path) {
        LinearLayout card = new LinearLayout(this);
        card.setOrientation(LinearLayout.VERTICAL);
        card.setPadding(40, 40, 40, 40);
        card.setBackgroundColor(Color.parseColor("#1E293B"));
        
        TextView t = new TextView(this);
        t.setText(title);
        t.setTextColor(Color.GRAY);
        card.addView(t);

        TextView s = new TextView(this);
        File f = new File(path);
        if(f.exists()) {
            s.setText("● ONLINE");
            s.setTextColor(Color.GREEN);
        } else {
            s.setText("○ OFFLINE (Missing File)");
            s.setTextColor(Color.RED);
        }
        card.addView(s);
        
        parent.addView(card);
        // ใส่ Space ระหว่าง Card
        View space = new View(this);
        space.setMinimumHeight(30);
        parent.addView(space);
    }
}
