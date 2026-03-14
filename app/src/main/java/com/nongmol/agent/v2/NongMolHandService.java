package com.nongmol.agent.v2;

import android.accessibilityservice.AccessibilityService;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import java.util.List;

public class NongMolHandService extends AccessibilityService {
    private final BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override public void onReceive(Context c, Intent i) {
            String target = i.getStringExtra("target");
            if (target != null) {
                AccessibilityNodeInfo root = getRootInActiveWindow();
                if (root == null) return;
                List<AccessibilityNodeInfo> nodes = root.findAccessibilityNodeInfosByText(target);
                for (AccessibilityNodeInfo node : nodes) {
                    if (node.isClickable()) { node.performAction(AccessibilityNodeInfo.ACTION_CLICK); break; }
                }
            }
        }
    };
    @Override public void onServiceConnected() { registerReceiver(receiver, new IntentFilter("NONGMOL_CLICK"), Context.RECEIVER_NOT_EXPORTED); }
    @Override public void onAccessibilityEvent(AccessibilityEvent e) {}
    @Override public void onInterrupt() {}
}
