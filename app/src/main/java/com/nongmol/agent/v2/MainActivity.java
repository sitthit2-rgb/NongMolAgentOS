package com.nongmol.agent.v2;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Gravity;
import android.view.View;
import android.widget.*;
import java.io.File;

public class MainActivity extends Activity {
    private final String BASE_PATH = "/storage/emulated/0/002";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        LinearLayout root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setBackgroundColor(Color.parseColor("#121212"));
        root.setPadding(60, 100, 60, 60);
        root.setGravity(Gravity.CENTER_HORIZONTAL);

        TextView logo = new TextView(this);
        logo.setText("NONGMOL AGENT\nNEURAL LINK v2");
        logo.setTextSize(26);
        logo.setTypeface(null, Typeface.BOLD);
        logo.setTextColor(Color.WHITE);
        logo.setGravity(Gravity.CENTER);
        logo.setPadding(0, 0, 0, 80);
        root.addView(logo);

        // System Status Card
        LinearLayout card = createStyledCard();
        String[] nodes = {"Brain (Llama3)", "Engine (Llama.so)", "Vision (GGUF)", "Ear (Whisper)"};
        String[] paths = {BASE_PATH+"/models/brain_llama3.gguf", BASE_PATH+"/engine/libllama.so", BASE_PATH+"/models/vision_llama3.gguf", BASE_PATH+"/ear/whisper_base.bin"};
        
        for (int i=0; i<nodes.length; i++) {
            boolean ok = new File(paths[i]).exists();
            TextView tv = new TextView(this);
            tv.setText((ok ? "● " : "○ ") + nodes[i]);
            tv.setTextColor(ok ? Color.parseColor("#00FFCC") : Color.parseColor("#FF4444"));
            tv.setPadding(20, 15, 20, 15);
            card.addView(tv);
        }
        root.addView(card);

        // Actions
        root.addView(createStyledButton("ACTIVATE PERMISSIONS", v -> {
            startActivity(new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS));
        }));

        setContentView(root);
    }

    private LinearLayout createStyledCard() {
        LinearLayout l = new LinearLayout(this);
        l.setOrientation(LinearLayout.VERTICAL);
        l.setPadding(40, 40, 40, 40);
        GradientDrawable gd = new GradientDrawable();
        gd.setColor(Color.parseColor("#1E1E1E"));
        gd.setCornerRadius(30f);
        l.setBackground(gd);
        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(-1, -2);
        p.setMargins(0, 0, 0, 80);
        l.setLayoutParams(p);
        return l;
    }

    private Button createStyledButton(String text, View.OnClickListener clk) {
        Button b = new Button(this);
        b.setText(text);
        b.setTextColor(Color.BLACK);
        b.setTypeface(null, Typeface.BOLD);
        GradientDrawable gd = new GradientDrawable();
        gd.setColor(Color.parseColor("#00FFCC"));
        gd.setCornerRadius(15f);
        b.setBackground(gd);
        b.setOnClickListener(clk);
        return b;
    }
}
