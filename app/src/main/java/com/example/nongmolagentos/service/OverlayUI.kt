package com.example.nongmolagentos.service

import android.content.Context
import com.example.nongmolagentos.ai.InferenceEngine
import com.example.nongmolagentos.utils.PermissionHelper

class OverlayUI(private val context: Context) {
    private val engine = InferenceEngine(context)
    private val helper = PermissionHelper(context)
    
    fun show() {
        if (helper.hasOverlayPermission()) {
            engine.startInference()
        }
    }
}
