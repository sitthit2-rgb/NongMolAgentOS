package com.nongmol.agent.v2;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private WebView myWebView;
    private TextToSpeech tts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myWebView = new WebView(this);
        WebSettings ws = myWebView.getSettings();
        ws.setJavaScriptEnabled(true);
        ws.setDomStorageEnabled(true);
        
        // ตั้งค่า TTS (เสียงพูด)
        tts = new TextToSpeech(this, status -> {
            if (status != TextToSpeech.ERROR) {
                tts.setLanguage(new Locale("th", "TH"));
            }
        });

        myWebView.addJavascriptInterface(new NongMolBridge(), "NongMolBridge");
        myWebView.setWebViewClient(new WebViewClient());
        myWebView.loadUrl("file:///android_asset/index.html");
        setContentView(myWebView);
    }

    public class NongMolBridge {
        @JavascriptInterface
        public void postToAI(final String message) {
            runOnUiThread(() -> {
                // ระบบโต้ตอบ (Logic จำลอง - พี่สามารถเปลี่ยนเป็นเรียก JNI ตรงนี้ได้)
                String response = "น้องมลได้ยินพี่พูดว่า " + message + " ค่ะ";
                
                // 1. ส่งข้อความกลับไปโชว์ที่หน้าจอ HTML
                myWebView.evaluateJavascript("updateBotResponse('" + response + "')", null);
                
                // 2. สั่งให้น้องมลออกเสียงพูด
                tts.speak(response, TextToSpeech.QUEUE_FLUSH, null, null);
            });
        }
    }

    @Override
    protected void onDestroy() {
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
        super.onDestroy();
    }
}
