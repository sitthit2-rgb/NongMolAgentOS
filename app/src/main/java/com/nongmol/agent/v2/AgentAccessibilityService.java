package com.nongmol.agent.v2;

import android.accessibilityservice.AccessibilityService;
import android.graphics.PixelFormat;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityEvent;
import android.widget.Button;
import android.widget.Toast;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;

public class AgentAccessibilityService extends AccessibilityService {
    private WindowManager windowManager;
    private View floatingView;

    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();
        // ทันทีที่เชื่อมต่อสมองสำเร็จ สร้างปุ่มลอยโปร่งใส (Floating Button)
        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        
        Button agentButton = new Button(this);
        agentButton.setText("🤖 NONGMOL");
        agentButton.setTextColor(Color.WHITE);
        
        GradientDrawable bg = new GradientDrawable();
        bg.setColor(Color.parseColor("#88000000")); // ปุ่มสีดำโปร่งแสง
        bg.setCornerRadius(50f);
        bg.setStroke(3, Color.CYAN);
        agentButton.setBackground(bg);

        WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_ACCESSIBILITY_OVERLAY,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);
        params.gravity = Gravity.TOP | Gravity.START;
        params.x = 0;
        params.y = 200;

        agentButton.setOnClickListener(v -> {
            Toast.makeText(this, "ดึงข้อมูลหน้าจอ (UI Tree)... แบบ ClawMobile", Toast.LENGTH_SHORT).show();
            // TODO: ส่งข้อมูลหน้าจอไปยัง JNI / libllama.so ตรงนี้
        });

        windowManager.addView(agentButton, params);
        floatingView = agentButton;
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        // ตรงนี้แหละคือที่ที่ AI จะ 'มองเห็น' สิ่งที่เกิดขึ้นบนจอ (mobile-use)
    }

    @Override
    public void onInterrupt() {}

    @Override
    public boolean onUnbind(android.content.Intent intent) {
        if (floatingView != null) windowManager.removeView(floatingView);
        return super.onUnbind(intent);
    }
}
