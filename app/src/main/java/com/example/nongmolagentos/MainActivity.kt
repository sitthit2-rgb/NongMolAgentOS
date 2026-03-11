package com.example.nongmolagentos

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.nongmolagentos.service.OverlayUI

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val overlay = OverlayUI(this)
        overlay.show()
    }
}
