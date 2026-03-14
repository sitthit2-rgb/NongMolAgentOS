package com.nongmol.agent.v2;

import android.accessibilityservice.AccessibilityService;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.util.Log;

public class NongMolHandService extends AccessibilityService {
    // นี่คือ "มือ" (Claw) ที่จะใช้กดหรือเลื่อนหน้าจอตาม mobile-use
    public void performClawAction(int x, int y, String action) {
        Log.i("ClawMobile", "Executing " + action + " at [" + x + "," + y + "]");
        // Logic สำหรับการเลื่อน/คลิกจะอยู่ตรงนี้
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        // "ตา" จะมองเห็นโครงสร้างหน้าจอผ่าน NodeInfo
        AccessibilityNodeInfo rootNode = getRootInActiveWindow();
    }

    @Override
    public void onInterrupt() {}
}
