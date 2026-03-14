package com.nongmol.agent.v2;

import android.os.Bundle;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import androidx.appcompat.app.AppCompatActivity;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        webView = new WebView(this);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true); // สำคัญสำหรับ IndexedDB ของพี่
        
        // เชื่อมท่อ Bridge ชื่อ "NongMolBridge" ตามที่พี่ต้องการ
        webView.addJavascriptInterface(new WebAppInterface(), "NongMolBridge");
        
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("file:///android_asset/index.html");
        setContentView(webView);
    }

    class WebAppInterface {
        @JavascriptInterface
        public void postToAI(String data) {
            // นี่คือจุดที่รับข้อมูลจาก HTML มาเลือกว่าจะส่งไป JNI หรือ API เทรน
            runOnUiThread(() -> {
                // ส่งค่ากลับไปที่ฟังก์ชันใน HTML ของพี่ (เช่น displayResult)
                webView.evaluateJavascript("javascript:console.log('Data Received: " + data + "')", null);
            });
        }
    }
}
