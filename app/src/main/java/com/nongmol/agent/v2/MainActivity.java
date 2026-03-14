package com.nongmol.agent.v2;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.*;
import java.io.File;

public class MainActivity extends Activity {
    private EditText inputField;
    private TextView chatFeed;
    private final String MODEL_PATH = "/storage/emulated/0/002/models/Qwen3.5-0.8B-BF16.gguf";

    // Load Native Engine
    static {
        System.loadLibrary("nongmol-engine");
    }
    public native String stringFromJNI();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        LinearLayout mainLayout = new LinearLayout(this);
        mainLayout.setOrientation(LinearLayout.VERTICAL);
        mainLayout.setBackgroundColor(Color.parseColor("#020617"));
        mainLayout.setPadding(40, 60, 40, 40);

        // Header
        TextView title = new TextView(this);
        title.setText("NONGMOL AGENT OS");
        title.setTextColor(Color.parseColor("#38BDF8"));
        title.setTextSize(22);
        title.setTypeface(Typeface.DEFAULT_BOLD);
        title.setGravity(Gravity.CENTER);
        mainLayout.addView(title);

        // Status Feed
        chatFeed = new TextView(this);
        chatFeed.setText("> " + stringFromJNI() + "\n> System Ready.");
        chatFeed.setTextColor(Color.GREEN);
        chatFeed.setBackground(createShape("#0F172A", 20));
        chatFeed.setPadding(30, 30, 30, 30);
        chatFeed.setHeight(800);
        
        LinearLayout.LayoutParams feedParams = new LinearLayout.LayoutParams(-1, 0, 1f);
        feedParams.setMargins(0, 40, 0, 40);
        chatFeed.setLayoutParams(feedParams);
        mainLayout.addView(chatFeed);

        // Input Area
        LinearLayout inputArea = new LinearLayout(this);
        inputField = new EditText(this);
        inputField.setHint("Ask Qwen anything...");
        inputField.setHintTextColor(Color.GRAY);
        inputField.setTextColor(Color.WHITE);
        inputField.setBackground(createShape("#1E293B", 15));
        
        LinearLayout.LayoutParams inputParams = new LinearLayout.LayoutParams(0, -2, 1f);
        inputField.setLayoutParams(inputParams);
        inputArea.addView(inputField);

        Button btnSend = new Button(this);
        btnSend.setText("SEND");
        btnSend.setTextColor(Color.WHITE);
        btnSend.setBackground(createShape("#0EA5E9", 10));
        btnSend.setOnClickListener(v -> {
            String msg = inputField.getText().toString();
            if(!msg.isEmpty()){
                chatFeed.append("\n\nYOU: " + msg);
                chatFeed.append("\nAGENT: [Computing via " + new File(MODEL_PATH).getName() + "...]");
                inputField.setText("");
            }
        });
        inputArea.addView(btnSend);
        mainLayout.addView(inputArea);

        setContentView(mainLayout);
    }

    private GradientDrawable createShape(String color, int radius) {
        GradientDrawable gd = new GradientDrawable();
        gd.setColor(Color.parseColor(color));
        gd.setCornerRadius(radius);
        return gd;
    }
}
