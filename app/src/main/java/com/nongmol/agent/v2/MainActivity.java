package com.nongmol.agent.v2;

import android.os.Bundle;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private WebView myWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myWebView = new WebView(this);
        WebSettings ws = myWebView.getSettings();
        
        ws.setJavaScriptEnabled(true);
        ws.setDomStorageEnabled(true); // สำหรับ IndexedDB/RAG
        
        myWebView.addJavascriptInterface(new NongMolBridge(), "NongMolBridge");
        myWebView.setWebViewClient(new WebViewClient());
        myWebView.loadUrl("file:///android_asset/index.html");
        
        setContentView(myWebView);
    }

    public class NongMolBridge {
        @JavascriptInterface
        public void postToAI(final String message) {
            // จำลองการทำงาน: รับข้อความมา แล้วส่งกลับไปหา HTML
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    // ตรงนี้ในอนาคต พี่เอาไปเชื่อมกับ JNI หรือ API ได้เลย
                    String response = "น้องมลได้รับข้อความ: '" + message + "' แล้วค่ะ กำลังประมวลผล...";
                    myWebView.evaluateJavascript("updateBotResponse('" + response + "')", null);
                }
            });
        }
    }
}
