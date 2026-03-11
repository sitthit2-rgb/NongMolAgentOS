package com.example.nongmolagentos.ai
import kotlinx.coroutines.flow.MutableStateFlow

class InferenceEngine {
    val status = MutableStateFlow("Idle")
    fun processTask(input: String) {
        status.value = "Thinking..."
        // เชื่อมต่อกับ LlamaWrapper ต่อไป
    }
}