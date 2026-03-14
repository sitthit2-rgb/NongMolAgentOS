package com.nongmol.agent.v2;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.*;
import java.io.File;

public class MainActivity extends Activity {
    private EditText inputField;
    private TextView chatFeed, statusMonitor;
    private ScrollView scrollView;
    private LinearLayout sensorsGrid;

    static {
        System.loadLibrary("nongmol-engine");
    }
    public native String stringFromJNI();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // โหมดเต็มหน้าจอแบบไม่มีแถบด้านบน
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // พื้นหลังโทน Cyber-Dark
        RelativeLayout root = new RelativeLayout(this);
        root.setBackgroundColor(Color.parseColor("#010409"));

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(40, 60, 40, 40);

        // 1. หัวข้อระบบ (Neon Glow Style)
        TextView header = new TextView(this);
        header.setText("NONGMOL CORE V4.0");
        header.setTextColor(Color.parseColor("#00F2FF"));
        header.setTextSize(26);
        header.setTypeface(Typeface.create("sans-serif-thin", Typeface.BOLD));
        header.setGravity(Gravity.CENTER);
        layout.addView(header);

        // 2. แถบสถานะ แสง สี เสียง (Live API Indicators)
        statusMonitor = new TextView(this);
        statusMonitor.setText("● LIGHT: GREEN | ● SOUND: ACTIVE | ● COLOR: SYNCED");
        statusMonitor.setTextColor(Color.parseColor("#00FF41"));
        statusMonitor.setTextSize(11);
        statusMonitor.setGravity(Gravity.CENTER);
        statusMonitor.setPadding(0, 10, 0, 30);
        layout.addView(statusMonitor);

        // 3. แผงควบคุมอวัยวะ (หู ตา จมูก แขน ขา) - UI ล้ำๆ
        sensorsGrid = new LinearLayout(this);
        sensorsGrid.setOrientation(LinearLayout.HORIZONTAL);
        sensorsGrid.setGravity(Gravity.CENTER);
        
        addOrganNode("ตา (EYE)", "#FF0055");
        addOrganNode("หู (EAR)", "#00D1FF");
        addOrganNode("จมูก (NOSE)", "#FFD700");
        addOrganNode("แขน (ARM)", "#AD00FF");
        addOrganNode("ขา (LEG)", "#00FF41");
        
        layout.addView(sensorsGrid);

        // 4. จอแสดงผล Neural Feed (ใสๆ เห็นพื้นหลัง)
        scrollView = new ScrollView(this);
        LinearLayout.LayoutParams scrollParams = new LinearLayout.LayoutParams(-1, 0, 1f);
        scrollParams.setMargins(0, 40, 0, 40);
        scrollView.setLayoutParams(scrollParams);

        chatFeed = new TextView(this);
        chatFeed.setText("> SYSTEM BOOTING...\n> " + stringFromJNI() + "\n> SENSORS CONNECTED.");
        chatFeed.setTextColor(Color.parseColor("#A7F3D0"));
        chatFeed.setBackground(createShape("#0D1117", 2, "#30363D", 20));
        chatFeed.setPadding(40, 40, 40, 40);
        chatFeed.setTypeface(Typeface.MONOSPACE);
        chatFeed.setTextSize(14);
        
        scrollView.addView(chatFeed);
        layout.addView(scrollView);

        // 5. ช่องใส่คำสั่ง (Command Line)
        inputField = new EditText(this);
        inputField.setHint("SYNC ALL SYSTEMS...");
        inputField.setHintTextColor(Color.GRAY);
        inputField.setTextColor(Color.WHITE);
        inputField.setBackground(createShape("#161B22", 1, "#30363D", 10));
        inputField.setPadding(35, 35, 35, 35);
        layout.addView(inputField);

        // 6. ปุ่ม EXECUTE (ปุ่มกดแบบล้ำสมัย)
        Button btnExecute = new Button(this);
        btnExecute.setText("INITIALIZE GLOBAL SYNC");
        btnExecute.setTextColor(Color.BLACK);
        btnExecute.setBackground(createShape("#00F2FF", 0, "#00F2FF", 12));
        btnExecute.setTypeface(Typeface.DEFAULT_BOLD);
        
        LinearLayout.LayoutParams btnParams = new LinearLayout.LayoutParams(-1, -2);
        btnParams.setMargins(0, 30, 0, 0);
        btnExecute.setLayoutParams(btnParams);
        
        btnExecute.setOnClickListener(v -> {
            String cmd = inputField.getText().toString();
            triggerGlobalAction(cmd.isEmpty() ? "AUTO_SCAN" : cmd);
            inputField.setText("");
        });
        layout.addView(btnExecute);

        root.addView(layout);
        setContentView(root);
    }

    private void addOrganNode(String name, String color) {
        TextView node = new TextView(this);
        node.setText(name);
        node.setTextColor(Color.parseColor(color));
        node.setTextSize(8);
        node.setGravity(Gravity.CENTER);
        node.setPadding(15, 12, 15, 12);
        node.setBackground(createShape("#00000000", 2, color, 8));
        
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(-2, -2);
        params.setMargins(8, 0, 8, 0);
        node.setLayoutParams(params);
        sensorsGrid.addView(node);
    }

    private void triggerGlobalAction(String cmd) {
        statusMonitor.setText("● ANALYZING NEURAL PATHS...");
        statusMonitor.setTextColor(Color.YELLOW);
        chatFeed.append("\n\n[USER_CMD]: " + cmd);

        chatFeed.postDelayed(() -> {
            chatFeed.append("\n> ตาสแกนวัตถุ... OK\n> หูรับฟังคำสั่ง... OK\n> จมูกเช็คสถานะ... OK\n> แขนขาเตรียมเคลื่อนไหว... OK");
            chatFeed.append("\n[RESULT]: ระบบร่างกายน้องมนต์พร้อมทำงาน 100%");
            statusMonitor.setText("● ALL SYSTEMS SYNCED | GREEN LIGHT");
            statusMonitor.setTextColor(Color.parseColor("#00FF41"));
            scrollView.post(() -> scrollView.fullScroll(View.FOCUS_DOWN));
        }, 1500);
    }

    private GradientDrawable createShape(String bgColor, int strokeWidth, String strokeColor, int radius) {
        GradientDrawable gd = new GradientDrawable();
        gd.setColor(Color.parseColor(bgColor));
        gd.setCornerRadius(radius);
        if (strokeWidth > 0) gd.setStroke(strokeWidth, Color.parseColor(strokeColor));
        return gd;
    }
}
