package com.nongmol.core

import android.content.Context
import android.os.Environment
import android.util.Log
import java.io.File

object NongMolCore {
    // กำหนด Root Path ให้ชัดเจน
    val ROOT_PATH = File(Environment.getExternalStorageDirectory(), "002")
    
    // เส้นประสาทแต่ละส่วน
    val ENGINE_PATH = File(ROOT_PATH, "engine/libllama.so")
    val BRAIN_PATH = File(ROOT_PATH, "models/brain_llama3.gguf")
    val VISION_PATH = File(ROOT_PATH, "models/vision_llama3.gguf")
    val EAR_PATH = File(ROOT_PATH, "ear/whisper_base.bin")

    fun checkStatus(file: File): Boolean = file.exists()

    // ระบบ API ครู (Neural Bridge - ตัวแปลคำสั่ง)
    fun processInstruction(task: String): String {
        return "🧠 NongMol Thinking: Analyzing $task using ClawMobile logic..."
    }
}
