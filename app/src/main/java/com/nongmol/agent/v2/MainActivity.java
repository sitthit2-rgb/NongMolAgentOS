package com.nongmol.agent.v2;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import java.io.File;

public class MainActivity extends Activity {
    private LinearLayout statusContainer;
    private final String BASE_PATH = "/storage/emulated/0/002";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // สร้างพื้นหลังแบบโปร่งแสง (Translucent Black) ให้เห็นแอปด้านหลัง
        LinearLayout root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setBackgroundColor(Color.parseColor("#99000000")); // ดำโปร่งใส 60%
        root.setGravity(Gravity.CENTER);

        // กล่อง UI ตรงกลาง (Glassmorphism style)
        LinearLayout card = new LinearLayout(this);
        card.setOrientation(LinearLayout.VERTICAL);
        card.setPadding(50, 50, 50, 50);
        GradientDrawable drawable = new GradientDrawable();
        drawable.setColor(Color.parseColor("#CC10101A")); // ดำเข้มโปร่งใส
        drawable.setCornerRadius(30f);
        drawable.setStroke(2, Color.parseColor("#00CCFF"));
        card.setBackground(drawable);
        
        LinearLayout.LayoutParams cardParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        cardParams.setMargins(40, 0, 40, 0);
        card.setLayoutParams(cardParams);

        TextView title = new TextView(this);
        title.setText("NONGMOL NEURAL LINK");
        title.setTextSize(20);
        title.setTextColor(Color.CYAN);
        title.setGravity(Gravity.CENTER);
        title.setPadding(0, 0, 0, 30);
        card.addView(title);

        statusContainer = new LinearLayout(this);
        statusContainer.setOrientation(LinearLayout.VERTICAL);
        card.addView(statusContainer);

        // ปุ่มขอสิทธิ์ (ต้องมีสำหรับ ClawMobile)
        Button btnOverlay = new Button(this);
        btnOverlay.setText("1. อนุญาตให้แสดงทับแอปอื่น (Overlay)");
        btnOverlay.setOnClickListener(v -> startActivity(new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION)));
        
        Button btnAccess = new Button(this);
        btnAccess.setText("2. เปิดระบบอ่านหน้าจอ (Accessibility)");
        btnAccess.setOnClickListener(v -> startActivity(new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)));

        card.addView(btnOverlay);
        card.addView(btnAccess);
        root.addView(card);
        setContentView(root);

        checkNeuralNodes();
    }

    private void checkNeuralNodes() {
        statusContainer.removeAllViews();
        addNode("🧠 Brain (LLM / GGUF)", BASE_PATH + "/models/brain_llama3.gguf");
        addNode("⚙️ Engine (libllama.so)", BASE_PATH + "/engine/libllama.so");
        addNode("👀 Vision (Vision GGUF)", BASE_PATH + "/models/vision_llama3.gguf");
        addNode("👂 Ear (Whisper BIN)", BASE_PATH + "/ear/whisper_base.bin");
    }

    private void addNode(String name, String path) {
        boolean isOk = new File(path).exists();
        TextView tv = new TextView(this);
        tv.setText((isOk ? "✅ " : "❌ ") + name);
        tv.setTextColor(isOk ? Color.GREEN : Color.parseColor("#FF5555"));
        tv.setPadding(0, 10, 0, 10);
        statusContainer.addView(tv);
    }
}
