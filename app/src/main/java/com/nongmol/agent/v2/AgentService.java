package com.nongmol.agent.v2;
import android.accessibilityservice.AccessibilityService;
import android.view.accessibility.AccessibilityEvent;

public class AgentService extends AccessibilityService {
    @Override public void onAccessibilityEvent(AccessibilityEvent event) {}
    @Override public void onInterrupt() {}
}
