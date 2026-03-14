package com.nongmol.agent.v2;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private String savedKey = "";
    private final String SYSTEM_PROMPT = "คุณคือ 'ครูหม่น' AI ผู้ช่วยมือถือ " +
            "1.ถ้าสั่งกด/เปิดแอป ตอบขึ้นต้นว่า 'COMMAND_CLICK: [ชื่อปุ่ม]' " +
            "2.ถ้าคุยทั่วไป ตอบสั้นๆ สุภาพ";
    private TextView logTv;
    private EditText keyInput;
    private SpeechRecognizer ear;
    private TextToSpeech mouth;
    private OkHttpClient brainClient = new OkHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupUI();
        checkPermissions();
        initMouth();
    }

    private void setupUI() {
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(40, 40, 40, 40);
        layout.setBackgroundColor(0xFFF0F7FF);

        keyInput = new EditText(this); keyInput.setHint("วาง Groq API Key...");
        Button btnSave = new Button(this); btnSave.setText("💾 บันทึก Key");
        Button btnListen = new Button(this); btnListen.setText("🎤 คุยกับครูหม่น");
        logTv = new TextView(this); logTv.setText("🎓 ครูหม่นพร้อมสอนแล้ว!");

        layout.addView(new TextView(this){{setText("🏫 NongMol V18 Master"); setTextSize(22);}});
        layout.addView(keyInput); layout.addView(btnSave); layout.addView(btnListen);
        layout.addView(new ScrollView(this){{addView(logTv);}});
        setContentView(layout);

        SharedPreferences prefs = getSharedPreferences("NongMolPrefs", MODE_PRIVATE);
        savedKey = prefs.getString("api_key", "");
        keyInput.setText(savedKey);

        btnSave.setOnClickListener(v -> {
            savedKey = keyInput.getText().toString().trim();
            prefs.edit().putString("api_key", savedKey).apply();
            Toast.makeText(this, "บันทึกเรียบร้อย", Toast.LENGTH_SHORT).show();
        });

        btnListen.setOnClickListener(v -> startListening());
    }

    private void checkPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO}, 1);
        }
    }

    private void initMouth() {
        mouth = new TextToSpeech(this, s -> mouth.setLanguage(new Locale("th", "TH")));
    }

    private void startListening() {
        ear = SpeechRecognizer.createSpeechRecognizer(this);
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "th-TH");
        ear.setRecognitionListener(new SimpleListener());
        ear.startListening(intent);
        logTv.append("\n👂 ฟังอยู่...");
    }

    private void askBrain(String q) {
        if (savedKey.isEmpty()) return;
        logTv.append("\n👤 คุณ: " + q);
        try {
            JSONObject json = new JSONObject();
            json.put("model", "llama-3.3-70b-versatile");
            JSONArray msgs = new JSONArray();
            msgs.put(new JSONObject().put("role", "system").put("content", SYSTEM_PROMPT));
            msgs.put(new JSONObject().put("role", "user").put("content", q));
            json.put("messages", msgs);

            Request req = new Request.Builder().url("https://api.groq.com/openai/v1/chat/completions")
                    .addHeader("Authorization", "Bearer " + savedKey)
                    .post(RequestBody.create(json.toString(), MediaType.parse("application/json"))).build();

            brainClient.newCall(req).enqueue(new Callback() {
                @Override public void onFailure(Call c, IOException e) {}
                @Override public void onResponse(Call c, Response r) throws IOException {
                    try {
                        String ans = new JSONObject(r.body().string()).getJSONArray("choices")
                                .getJSONObject(0).getJSONObject("message").getString("content");
                        runOnUiThread(() -> handleResponse(ans));
                    } catch (Exception e) {}
                }
            });
        } catch (Exception e) {}
    }

    private void handleResponse(String ans) {
        if (ans.startsWith("COMMAND_CLICK:")) {
            String target = ans.replace("COMMAND_CLICK:", "").trim();
            Intent i = new Intent("NONGMOL_CLICK");
            i.putExtra("target", target);
            sendBroadcast(i);
            mouth.speak("กำลังกด " + target, TextToSpeech.QUEUE_FLUSH, null, null);
        } else {
            logTv.append("\n🎓 ครูหม่น: " + ans);
            mouth.speak(ans, TextToSpeech.QUEUE_FLUSH, null, null);
        }
    }

    class SimpleListener implements RecognitionListener {
        public void onResults(Bundle r) { askBrain(r.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION).get(0)); }
        public void onReadyForSpeech(Bundle b) {}
        public void onError(int e) { logTv.append("\n❌ Error: " + e); }
        public void onBeginningOfSpeech() {}
        public void onRmsChanged(float r) {}
        public void onBufferReceived(byte[] b) {}
        public void onEndOfSpeech() {}
        public void onPartialResults(Bundle b) {}
        public void onEvent(int i, Bundle b) {}
    }
}
