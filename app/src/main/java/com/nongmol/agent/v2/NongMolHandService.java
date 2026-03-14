package com.nongmol.agent.v2;

import android.accessibilityservice.AccessibilityService;
import android.view.accessibility.AccessibilityEvent;
import android.util.Log;

public class NongMolHandService extends AccessibilityService {
    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        // รอรับคำสั่งจาก Brain ผ่าน ClawMobile Logic
    }

    @Override
    public void onInterrupt() {
        Log.e("NongMolHand", "Interrupted!");
    }

    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();
        Log.i("NongMolHand", "ClawMobile Hands Connected!");
    }
}
