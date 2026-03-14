package com.nongmol.agent.v2;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WebView myWebView = new WebView(this);
        WebSettings webSettings = myWebView.getSettings();
        
        // เปิดสวิตช์ให้ WebView ทำงานได้เหมือน Browser จริง
        webSettings.setJavaScriptEnabled(true); 
        webSettings.setDomStorageEnabled(true); // สำคัญมาก: เพื่อให้ IndexedDB ใน HTML ของพี่ทำงานได้
        webSettings.setDatabaseEnabled(true);
        webSettings.setAllowFileAccess(true);
        webSettings.setAllowContentAccess(true);

        // เชื่อมท่อ Bridge ให้ JavaScript ใน HTML เรียกใช้ได้
        myWebView.addJavascriptInterface(new NongMolBridge(), "NongMolBridge");

        myWebView.setWebViewClient(new WebViewClient());
        myWebView.loadUrl("file:///android_asset/index.html");
        setContentView(myWebView);
    }

    // คลาสสำหรับรับค่าจาก HTML ส่งไป API หรือ JNI
    public class NongMolBridge {
        @android.webkit.JavascriptInterface
        public void postToAI(String data) {
            // จุดนี้คือท่อส่งข้อมูลที่พี่ต้องการ
            System.out.println("Data from HTML: " + data);
        }
    }
}
