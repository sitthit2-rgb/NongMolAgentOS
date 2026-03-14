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
    private ScrollView scrollView;
    private final String MODEL_PATH = "/storage/emulated/0/002/models/Qwen3.5-0.8B-BF16.gguf";

    static {
        System.loadLibrary("nongmol-engine");
    }
    public native String stringFromJNI();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // Main Container
        LinearLayout mainLayout = new LinearLayout(this);
        mainLayout.setOrientation(LinearLayout.VERTICAL);
        mainLayout.setBackgroundColor(Color.parseColor("#020617"));
        mainLayout.setPadding(40, 40, 40, 40);

        // Header Section
        TextView title = new TextView(this);
        title.setText("NONGMOL AGENT OS V3.5");
        title.setTextColor(Color.parseColor("#38BDF8"));
        title.setTextSize(22);
        title.setTypeface(Typeface.DEFAULT_BOLD);
        title.setGravity(Gravity.CENTER);
        title.setPadding(0, 0, 0, 20);
        mainLayout.addView(title);

        // System Status Card
        TextView status = new TextView(this);
        status.setText("● NEURAL LINK: " + stringFromJNI() + "\n● MODEL: Qwen3.5-0.8B (Ready)");
        status.setTextColor(Color.parseColor("#10B981"));
        status.setBackground(createShape("#0F172A", 15));
        status.setPadding(30, 20, 30, 20);
        status.setTextSize(12);
        mainLayout.addView(status);

        // Chat Display (Scrollable)
        scrollView = new ScrollView(this);
        LinearLayout.LayoutParams scrollParams = new LinearLayout.LayoutParams(-1, 0, 1f);
        scrollParams.setMargins(0, 30, 0, 30);
        scrollView.setLayoutParams(scrollParams);

        chatFeed = new TextView(this);
        chatFeed.setText("> Agent Engine Initialized.\n> System is waiting for your command...");
        chatFeed.setTextColor(Color.parseColor("#A7F3D0"));
        chatFeed.setBackground(createShape("#1E293B", 20));
        chatFeed.setPadding(40, 40, 40, 40);
        chatFeed.setTextSize(15);
        chatFeed.setLineSpacing(10, 1);
        
        scrollView.addView(chatFeed);
        mainLayout.addView(scrollView);

        // Input Field
        inputField = new EditText(this);
        inputField.setHint("Type your command here...");
        inputField.setHintTextColor(Color.GRAY);
        inputField.setTextColor(Color.WHITE);
        inputField.setBackground(createShape("#334155", 15));
        inputField.setPadding(30, 35, 30, 35);
        mainLayout.addView(inputField);

        // Control Panel (Buttons)
        LinearLayout controlPanel = new LinearLayout(this);
        controlPanel.setOrientation(LinearLayout.HORIZONTAL);
        controlPanel.setPadding(0, 30, 0, 0);

        // Reset Button
        Button btnReset = new Button(this);
        btnReset.setText("RESET");
        btnReset.setBackground(createShape("#64748B", 10));
        btnReset.setTextColor(Color.WHITE);
        btnReset.setOnClickListener(v -> {
            chatFeed.setText("> System Rebooted.\n> All Neural pathways cleared.");
            Toast.makeText(this, "System Core Reset", Toast.LENGTH_SHORT).show();
        });

        // Send Button
        Button btnSend = new Button(this);
        btnSend.setText("EXECUTE");
        btnSend.setBackground(createShape("#0EA5E9", 10));
        btnSend.setTextColor(Color.WHITE);
        btnSend.setTypeface(Typeface.DEFAULT_BOLD);
        
        LinearLayout.LayoutParams btnParams = new LinearLayout.LayoutParams(0, -2, 1f);
        btnParams.setMargins(20, 0, 0, 0);
        btnSend.setLayoutParams(btnParams);
        
        btnSend.setOnClickListener(v -> {
            String msg = inputField.getText().toString().trim();
            if(!msg.isEmpty()){
                chatFeed.append("\n\n[USER]: " + msg);
                inputField.setText("");
                
                // Simulate Neural Computation
                chatFeed.postDelayed(() -> {
                    String response = "[AGENT]: ประมวลผลสำเร็จ\n> ผลลัพธ์: ระบบตอบรับคำสั่ง '" + msg + "' เรียบร้อยแล้ว";
                    chatFeed.append("\n" + response);
                    scrollView.post(() -> scrollView.fullScroll(View.FOCUS_DOWN));
                }, 1000);
            }
        });

        controlPanel.addView(btnReset);
        controlPanel.addView(btnSend);
        mainLayout.addView(controlPanel);

        setContentView(mainLayout);
    }

    private GradientDrawable createShape(String color, int radius) {
        GradientDrawable gd = new GradientDrawable();
        gd.setColor(Color.parseColor(color));
        gd.setCornerRadius(radius);
        return gd;
    }
}
