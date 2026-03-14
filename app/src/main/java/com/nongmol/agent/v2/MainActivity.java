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
        
        LinearLayout mainLayout = new LinearLayout(this);
        mainLayout.setOrientation(LinearLayout.VERTICAL);
        mainLayout.setBackgroundColor(Color.parseColor("#020617"));
        mainLayout.setPadding(40, 40, 40, 40);

        // Header
        TextView title = new TextView(this);
        title.setText("NONGMOL AGENT OS V3.1");
        title.setTextColor(Color.parseColor("#38BDF8"));
        title.setTextSize(20);
        title.setTypeface(Typeface.DEFAULT_BOLD);
        title.setGravity(Gravity.CENTER);
        mainLayout.addView(title);

        // Chat Display Area
        scrollView = new ScrollView(this);
        LinearLayout.LayoutParams scrollParams = new LinearLayout.LayoutParams(-1, 0, 1f);
        scrollParams.setMargins(0, 20, 0, 20);
        scrollView.setLayoutParams(scrollParams);

        chatFeed = new TextView(this);
        chatFeed.setText("> " + stringFromJNI() + "\n> System Online.");
        chatFeed.setTextColor(Color.GREEN);
        chatFeed.setBackground(createShape("#0F172A", 20));
        chatFeed.setPadding(30, 30, 30, 30);
        chatFeed.setTextSize(14);
        
        scrollView.addView(chatFeed);
        mainLayout.addView(scrollView);

        // Input Field
        inputField = new EditText(this);
        inputField.setHint("Type message...");
        inputField.setHintTextColor(Color.GRAY);
        inputField.setTextColor(Color.WHITE);
        inputField.setBackground(createShape("#1E293B", 15));
        inputField.setPadding(30, 30, 30, 30);
        mainLayout.addView(inputField);

        // Action Buttons Row
        LinearLayout buttonRow = new LinearLayout(this);
        buttonRow.setOrientation(LinearLayout.HORIZONTAL);
        buttonRow.setGravity(Gravity.CENTER);
        buttonRow.setPadding(0, 20, 0, 0);

        // Clear Button
        Button btnClear = new Button(this);
        btnClear.setText("CLEAR");
        btnClear.setBackground(createShape("#334155", 10));
        btnClear.setTextColor(Color.WHITE);
        btnClear.setOnClickListener(v -> {
            chatFeed.setText("> Screen Cleared.\n> Waiting for command...");
        });
        
        // Send Button
        Button btnSend = new Button(this);
        btnSend.setText("SEND COMMAND");
        btnSend.setBackground(createShape("#0EA5E9", 10));
        btnSend.setTextColor(Color.WHITE);
        LinearLayout.LayoutParams sendParams = new LinearLayout.LayoutParams(0, -2, 1f);
        sendParams.setMargins(20, 0, 0, 0);
        btnSend.setLayoutParams(sendParams);
        
        btnSend.setOnClickListener(v -> {
            String msg = inputField.getText().toString();
            if(!msg.isEmpty()){
                chatFeed.append("\n\nYOU: " + msg);
                inputField.setText("");
                
                // ระบบตอบกลับแบบ Real-time (จำลอง)
                chatFeed.postDelayed(() -> {
                    String res = "AGENT: [Processing via Qwen...]\n> Result: ภารกิจสำเร็จ ข้อมูลถูกส่งไปยังแกนกลางแล้ว";
                    chatFeed.append("\n\n" + res);
                    scrollView.post(() -> scrollView.fullScroll(View.FOCUS_DOWN));
                }, 800);
            }
        });

        buttonRow.addView(btnClear);
        buttonRow.addView(btnSend);
        mainLayout.addView(buttonRow);

        setContentView(mainLayout);
    }

    private GradientDrawable createShape(String color, int radius) {
        GradientDrawable gd = new GradientDrawable();
        gd.setColor(Color.parseColor(color));
        gd.setCornerRadius(radius);
        return gd;
    }
}
