
package com.example.nongmolagentos.service

import android.content.Context
import android.graphics.PixelFormat
import android.view.*
import android.widget.*
import android.graphics.drawable.GradientDrawable
import android.graphics.Color
import android.view.animation.AnimationUtils
import android.view.animation.LinearInterpolator
import android.view.animation.AlphaAnimation

class OverlayUI(private val context: Context) {
    private val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    private var overlayView: View? = null

    fun show() {
        val params = WindowManager.LayoutParams(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
            PixelFormat.TRANSLUCENT
        ).apply {
            gravity = Gravity.TOP or Gravity.START
            x = 20
            y = 150
        }

        // สร้าง Container สไตล์ Glassmorphism
        val container = FrameLayout(context).apply {
            val gd = GradientDrawable().apply {
                shape = GradientDrawable.OVAL
                setColor(Color.parseColor("#E6222222")) // พื้นหลังดำโปร่งแสง
                setStroke(3, Color.parseColor("#FF6600")) // ขอบส้ม Neon
            }
            background = gd
            setPadding(10, 10, 10, 10)
            elevation = 20f
        }

        // สร้าง AI Pulse (ไฟกะพริบสถานะ)
        val pulse = View(context).apply {
            val gd = GradientDrawable().apply {
                shape = GradientDrawable.OVAL
                setColor(Color.parseColor("#FF6600"))
            }
            background = gd
            layoutParams = FrameLayout.LayoutParams(100, 100)
            
            // อนิเมชั่นไฟกะพริบระดับเทพ
            val anim = AlphaAnimation(0.2f, 0.8f).apply {
                duration = 1000
                interpolator = LinearInterpolator()
                repeatCount = AlphaAnimation.INFINITE
                repeatMode = AlphaAnimation.REVERSE
            }
            startAnimation(anim)
        }

        val logo = ImageView(context).apply {
            setImageResource(android.R.drawable.ic_menu_compass) // หรือใส่ไอคอน NongMol ของคุณ
            setColorFilter(Color.WHITE)
            layoutParams = FrameLayout.LayoutParams(100, 100).apply { gravity = Gravity.CENTER }
        }

        container.addView(pulse)
        container.addView(logo)

        // ระบบลากปุ่ม (Drag and Drop) แบบลื่นไหล
        container.setOnTouchListener(object : View.OnTouchListener {
            private var initialX = 0; private var initialY = 0
            private var initialTouchX = 0f; private var initialTouchY = 0f

            override fun onTouch(v: View, event: MotionEvent): Boolean {
                when (event.action) {
                    MotionEvent.ACTION_DOWN -> {
                        initialX = params.x; initialY = params.y
                        initialTouchX = event.rawX; initialTouchY = event.rawY
                        return true
                    }
                    MotionEvent.ACTION_MOVE -> {
                        params.x = initialX + (event.rawX - initialTouchX).toInt()
                        params.y = initialY + (event.rawY - initialTouchY).toInt()
                        windowManager.updateViewLayout(container, params)
                        return true
                    }
                    MotionEvent.ACTION_UP -> {
                        // เมื่อปล่อยมือ ให้สั่ง AI วิเคราะห์หน้าจอ
                        // startInference()
                        return true
                    }
                }
                return false
            }
        })

        windowManager.addView(container, params)
        overlayView = container
    }
}
