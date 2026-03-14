package com.nongmol.agent.v8;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.view.Gravity;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements TextToSpeech.OnInitListener {
    private TextToSpeech tts;
    private TextView tvStatus, tvChatLog;
    private EditText etApiKey, etInput;
    private static final int VOICE_CODE = 102;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LinearLayout root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setBackgroundColor(Color.parseColor("#F1F3F4"));
        root.setPadding(30, 40, 30, 30);
        
        TextView header = new TextView(this);
        header.setText("🤖 NONGMOL AGENT V8.6");
        header.setTextSize(22);
        header.setTypeface(null, Typeface.BOLD);
        header.setGravity(Gravity.CENTER);
        header.setPadding(0, 0, 0, 20);
        root.addView(header);

        etApiKey = new EditText(this);
        etApiKey.setHint("🔑 API Key");
        root.addView(wrapInCard(etApiKey));

        tvStatus = new TextView(this);
        tvStatus.setText("📡 ระบบพร้อมใช้งาน");
        root.addView(wrapInCard(tvStatus));

        ScrollView scroll = new ScrollView(this);
        tvChatLog = new TextView(this);
        tvChatLog.setText("ยินดีต้อนรับค่ะ! พิมพ์หรือพูดได้เลย");
        tvChatLog.setTextSize(16);
        tvChatLog.setMinLines(10);
        scroll.addView(tvChatLog);
        root.addView(wrapInCard(scroll), new LinearLayout.LayoutParams(-1, 0, 1.0f));

        LinearLayout ctrl = new LinearLayout(this);
        etInput = new EditText(this);
        etInput.setHint("พิมพ์ตรงนี้...");
        Button btnSend = new Button(this); btnSend.setText("ส่ง");
        btnSend.setOnClickListener(v -> { process(etInput.getText().toString()); etInput.setText(""); });
        Button btnMic = new Button(this); btnMic.setText("🎤");
        btnMic.setOnClickListener(v -> {
            Intent i = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            i.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "th-TH");
            startActivityForResult(i, VOICE_CODE);
        });
        ctrl.addView(etInput, new LinearLayout.LayoutParams(0, -2, 1.0f));
        ctrl.addView(btnSend); ctrl.addView(btnMic);
        root.addView(ctrl);
        setContentView(root);
        tts = new TextToSpeech(this, this);
        check();
    }
    private void check() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R && !Environment.isExternalStorageManager()) {
            Intent i = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
            i.setData(Uri.parse("package:" + getPackageName()));
            startActivity(i);
        }
    }
    private void process(String m) {
        if(m.isEmpty()) return;
        tvChatLog.append("\n\n👤 พี่: " + m + "\n🤖 น้องมล: รับทราบค่ะ");
        if(tts != null) tts.speak("รับทราบค่ะพี่", TextToSpeech.QUEUE_FLUSH, null, null);
    }
    private CardView wrapInCard(View v) {
        CardView c = new CardView(this); c.setRadius(15); c.setCardElevation(8);
        c.setUseCompatPadding(true); c.addView(v); v.setPadding(20,20,20,20);
        return c;
    }
    @Override public void onInit(int s) { if(s==TextToSpeech.SUCCESS) tts.setLanguage(new Locale("th")); }
    @Override protected void onActivityResult(int r, int rc, Intent d) {
        if (r == VOICE_CODE && rc == RESULT_OK) process(d.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS).get(0));
    }
}
