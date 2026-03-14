package com.nongmol.agent.v2;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import java.io.File;

public class MainActivity extends Activity {
    private final String MODEL_PATH = "/storage/emulated/0/002/models/Qwen3.5-0.8B-BF16.gguf";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // Main Container with Gradient
        LinearLayout mainLayout = new LinearLayout(this);
        mainLayout.setOrientation(LinearLayout.VERTICAL);
        mainLayout.setBackgroundColor(Color.parseColor("#020617")); // Deep Space Blue
        mainLayout.setPadding(50, 80, 50, 50);

        // Header Title
        TextView title = new TextView(this);
        title.setText("NONGMOL AGENT OS");
        title.setTextColor(Color.parseColor("#38BDF8"));
        title.setTextSize(24);
        title.setTypeface(Typeface.create("sans-serif-condensed", Typeface.BOLD));
        title.setGravity(Gravity.CENTER);
        mainLayout.addView(title);

        addSpacer(mainLayout, 60);

        // --- Stats Section ---
        LinearLayout statsCard = createCard("#1E293B");
        TextView brainStatus = createStatusLine("NEURAL BRAIN", new File(MODEL_PATH).exists());
        statsCard.addView(brainStatus);
        mainLayout.addView(statsCard);

        addSpacer(mainLayout, 40);

        // --- Chat Preview Window ---
        TextView chatLabel = new TextView(this);
        chatLabel.setText("LIVE NEURAL FEED");
        chatLabel.setTextColor(Color.parseColor("#94A3B8"));
        chatLabel.setTextSize(12);
        mainLayout.addView(chatLabel);

        EditText chatDisplay = new EditText(this);
        chatDisplay.setHint("Awaiting commands...");
        chatDisplay.setHintTextColor(Color.GRAY);
        chatDisplay.setTextColor(Color.GREEN);
        chatDisplay.setBackground(createCardBackground("#0F172A"));
        chatDisplay.setHeight(400);
        chatDisplay.setGravity(Gravity.TOP);
        chatDisplay.setFocusable(false);
        mainLayout.addView(chatDisplay);

        addSpacer(mainLayout, 40);

        // --- Action Buttons ---
        Button btnRun = createStyledButton("INITIALIZE CORE", "#0EA5E9");
        btnRun.setOnClickListener(v -> {
            if(new File(MODEL_PATH).exists()) {
                chatDisplay.setText("> Initializing Qwen 3.5...\n> Memory Allocated...\n> Brain Ready.");
                Toast.makeText(this, "Core Activated", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Error: Model File Missing!", Toast.LENGTH_LONG).show();
            }
        });
        mainLayout.addView(btnRun);

        setContentView(mainLayout);
    }

    private LinearLayout createCard(String color) {
        LinearLayout card = new LinearLayout(this);
        card.setOrientation(LinearLayout.VERTICAL);
        card.setPadding(40, 40, 40, 40);
        card.setBackground(createCardBackground(color));
        return card;
    }

    private GradientDrawable createCardBackground(String color) {
        GradientDrawable gd = new GradientDrawable();
        gd.setColor(Color.parseColor(color));
        gd.setCornerRadius(25f);
        gd.setStroke(2, Color.parseColor("#334155"));
        return gd;
    }

    private TextView createStatusLine(String label, boolean isOnline) {
        TextView tv = new TextView(this);
        tv.setText(label + ": " + (isOnline ? "● ONLINE" : "○ OFFLINE"));
        tv.setTextColor(isOnline ? Color.GREEN : Color.RED);
        tv.setTypeface(null, Typeface.BOLD);
        return tv;
    }

    private Button createStyledButton(String text, String color) {
        Button b = new Button(this);
        b.setText(text);
        b.setTextColor(Color.WHITE);
        GradientDrawable gd = new GradientDrawable();
        gd.setColor(Color.parseColor(color));
        gd.setCornerRadius(15f);
        b.setBackground(gd);
        return b;
    }

    private void addSpacer(LinearLayout layout, int height) {
        View s = new View(this);
        s.setLayoutParams(new LinearLayout.LayoutParams(1, height));
        layout.addView(s);
    }
}
