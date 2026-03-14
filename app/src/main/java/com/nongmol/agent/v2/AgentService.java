package com.nongmol.agent.v2;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class AgentService extends Service {
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("NongMolAgent", "Neural Engine Core Starting...");
        // ตรงนี้คือจุดที่เราจะโหลด llama.cpp เข้ามาประมวลผลไฟล์ .gguf ครับ
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) { return null; }
}
