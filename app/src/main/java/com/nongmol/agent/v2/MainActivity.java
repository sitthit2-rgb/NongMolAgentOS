package com.nongmol.agent.v2;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.*;
import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends Activity implements TextToSpeech.OnInitListener {
    private TextView chatFeed, statusMonitor;
    private ScrollView scrollView;
    private TextToSpeech tts;
    private final int REQ_CODE_SPEECH = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
        tts = new TextToSpeech(this, this);

        RelativeLayout root = new RelativeLayout(this);
        root.setBackgroundColor(Color.parseColor("#010409"));
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(40, 60, 40, 40);

        // Header & Status
        TextView header = new TextView(this);
        header.setText("NONGMOL VOICE CORE");
        header.setTextColor(Color.parseColor("#00F2FF"));
        header.setTextSize(24);
        header.setGravity(Gravity.CENTER);
        layout.addView(header);

        statusMonitor = new TextView(this);
        statusMonitor.setText("● EAR: LISTENING... | ● VOICE: ACTIVE");
        statusMonitor.setTextColor(Color.parseColor("#00FF41"));
        statusMonitor.setGravity(Gravity.CENTER);
        layout.addView(statusMonitor);

        // Chat Console
        scrollView = new ScrollView(this);
        chatFeed = new TextView(this);
        chatFeed.setText("> ระบบเสียงเริ่มต้นแล้ว\n> กดปุ่มไมค์เพื่อสนทนา...");
        chatFeed.setTextColor(Color.parseColor("#A7F3D0"));
        chatFeed.setBackground(createShape("#0D1117", 2, "#30363D", 20));
        chatFeed.setPadding(40, 40, 40, 40);
        chatFeed.setTypeface(Typeface.MONOSPACE);
        scrollView.addView(chatFeed);
        layout.addView(scrollView, new LinearLayout.LayoutParams(-1, 0, 1f));

        // VOICE BUTTON (ปุ่มล้ำๆ สำหรับกดพูด)
        Button btnVoice = new Button(this);
        btnVoice.setText("🎤 TAP TO SPEAK");
        btnVoice.setTextColor(Color.BLACK);
        btnVoice.setBackground(createShape("#FF0055", 0, "#FF0055", 15));
        btnVoice.setOnClickListener(v -> startVoiceInput());
        layout.addView(btnVoice);

        root.addView(layout);
        setContentView(root);
    }

    private void startVoiceInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "th-TH");
        try { startActivityForResult(intent, REQ_CODE_SPEECH); } catch (Exception e) {}
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ_CODE_SPEECH && resultCode == RESULT_OK && data != null) {
            ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            String spokenText = result.get(0);
            chatFeed.append("\n\nYOU (Voice): " + spokenText);
            processAndSpeak(spokenText);
        }
    }

    private void processAndSpeak(String text) {
        String response = "รับทราบครับ ระบบกำลังดำเนินการตามคำสั่ง " + text;
        chatFeed.append("\nAGENT: " + response);
        tts.speak(response, TextToSpeech.QUEUE_FLUSH, null, null);
        scrollView.post(() -> scrollView.fullScroll(View.FOCUS_DOWN));
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) tts.setLanguage(new Locale("th", "TH"));
    }

    private GradientDrawable createShape(String bg, int sw, String sc, int r) {
        GradientDrawable gd = new GradientDrawable();
        gd.setColor(Color.parseColor(bg));
        gd.setCornerRadius(r);
        if (sw > 0) gd.setStroke(sw, Color.parseColor(sc));
        return gd;
    }
}
