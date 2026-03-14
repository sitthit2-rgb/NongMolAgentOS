package com.nongmol.agent.v2;

import android.app.Activity;
import android.os.Bundle;
import android.widget.*;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.View;
import java.io.*;

public class MainActivity extends Activity {
    private LinearLayout statusContainer;
    private final String BASE_PATH = "/storage/emulated/0/002";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // --- UI หลัก (Agent Interface) ---
        ScrollView scroll = new ScrollView(this);
        LinearLayout root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setBackgroundColor(Color.parseColor("#050508"));
        root.setPadding(40, 60, 40, 40);

        TextView title = new TextView(this);
        title.setText("NONGMOL NEURAL LINK");
        title.setTextSize(22);
        title.setTextColor(Color.CYAN);
        title.setGravity(Gravity.CENTER);
        root.addView(title);

        // กล่องสถานะการเชื่อมต่อประสาท
        statusContainer = new LinearLayout(this);
        statusContainer.setOrientation(LinearLayout.VERTICAL);
        statusContainer.setPadding(30, 30, 30, 30);
        statusContainer.setBackgroundColor(Color.parseColor("#10101A"));
        root.addView(statusContainer);

        // ปุ่มคำสั่งเสียง (ของเดิมที่พี่อยากได้)
        Button btnVoice = new Button(this);
        btnVoice.setText("🎤 ACTIVATE VOICE COMMAND");
        btnVoice.setBackgroundColor(Color.parseColor("#00CCFF"));
        btnVoice.setTextColor(Color.BLACK);
        root.addView(btnVoice);

        scroll.addView(root);
        setContentView(scroll);

        // สั่งเชื่อมต่อประสาททั้งหมดทันที
        checkNeuralNodes();
    }

    private void checkNeuralNodes() {
        statusContainer.removeAllViews();
        addNode("🧠 Brain (Qwen Model)", BASE_PATH + "/models/Qwen3.5-0.8B-BF16.gguf");
        addNode("⚙️ Engine (Llama SO)", BASE_PATH + "/engine/libllama.so");
        addNode("👂 Ear (Whisper BIN)", BASE_PATH + "/ear/whisper_base.bin");
        addNode("⚙️ Config System", BASE_PATH + "/config/agent_tasks.json");
    }

    private void addNode(String name, String path) {
        File file = new File(path);
        boolean isOk = file.exists();

        TextView tv = new TextView(this);
        tv.setText((isOk ? "✅ " : "❌ ") + name);
        tv.setTextColor(isOk ? Color.GREEN : Color.RED);
        tv.setPadding(0, 10, 0, 10);
        statusContainer.addView(tv);
        
        if (!isOk) {
            TextView pathHint = new TextView(this);
            pathHint.setText("   ㄴ ต้องวางที่: " + path);
            pathHint.setTextSize(10);
            pathHint.setTextColor(Color.GRAY);
            statusContainer.addView(pathHint);
        }
    }
}
