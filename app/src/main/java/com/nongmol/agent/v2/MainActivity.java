package com.nongmol.agent.v2;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import androidx.appcompat.app.AppCompatActivity;

import okhttp3.*;
import org.json.JSONObject;
import java.io.IOException;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private WebView myWebView;
    private TextToSpeech tts;
    private final OkHttpClient client = new OkHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myWebView = new WebView(this);
        WebSettings ws = myWebView.getSettings();
        ws.setJavaScriptEnabled(true);
        ws.setDomStorageEnabled(true);
        
        tts = new TextToSpeech(this, status -> {
            if (status != TextToSpeech.ERROR) tts.setLanguage(new Locale("th", "TH"));
        });

        myWebView.addJavascriptInterface(new NongMolBridge(), "NongMolBridge");
        myWebView.setWebViewClient(new WebViewClient());
        myWebView.loadUrl("file:///android_asset/index.html");
        setContentView(myWebView);
    }

    public class NongMolBridge {
        @JavascriptInterface
        public void postToAI(String userText) {
            // เรียกฟังก์ชันยิง API
            callAI_API(userText);
        }
    }

    private void callAI_API(String text) {
        // --- ส่วนที่พี่ต้องแก้: URL และ API Key ---
        String apiUrl = "https://api.openai.com/v1/chat/completions"; 
        String apiKey = "YOUR_API_KEY_HERE"; 

        try {
            JSONObject json = new JSONObject();
            json.put("model", "gpt-3.5-turbo"); // หรือโมเดลที่พี่ใช้
            json.append("messages", new JSONObject().put("role", "user").put("content", text));

            RequestBody body = RequestBody.create(
                json.toString(), MediaType.get("application/json; charset=utf-8"));

            Request request = new Request.Builder()
                .url(apiUrl)
                .header("Authorization", "Bearer " + apiKey)
                .post(body)
                .build();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    updateUIAndSpeak("ขออภัยค่ะพี่ เชื่อมต่อเซิร์ฟเวอร์ไม่ได้");
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (response.isSuccessful()) {
                        try {
                            String resBody = response.body().string();
                            JSONObject resJson = new JSONObject(resBody);
                            String answer = resJson.getJSONArray("choices")
                                .getJSONObject(0).getJSONObject("message").getString("content");
                            updateUIAndSpeak(answer);
                        } catch (Exception e) {
                            updateUIAndSpeak("อ่านคำตอบไม่ได้ค่ะ");
                        }
                    }
                }
            });
        } catch (Exception e) { e.printStackTrace(); }
    }

    private void updateUIAndSpeak(final String msg) {
        runOnUiThread(() -> {
            myWebView.evaluateJavascript("updateBotResponse('" + msg.replace("'", "\\'") + "')", null);
            tts.speak(msg, TextToSpeech.QUEUE_FLUSH, null, null);
        });
    }
}
