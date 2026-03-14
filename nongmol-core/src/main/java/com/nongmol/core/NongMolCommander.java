package com.nongmol.agent.v2;

import android.accessibilityservice.AccessibilityService;
import android.view.accessibility.AccessibilityNodeInfo;
import java.util.List;

public class NongMolCommander {
    private AccessibilityService service;
    public NongMolCommander(AccessibilityService service) { this.service = service; }

    public void click(String text) {
        AccessibilityNodeInfo root = service.getRootInActiveWindow();
        if (root == null) return;
        List<AccessibilityNodeInfo> nodes = root.findAccessibilityNodeInfosByText(text);
        if (nodes != null && !nodes.isEmpty()) {
            nodes.get(0).performAction(AccessibilityNodeInfo.ACTION_CLICK);
        }
    }
}
