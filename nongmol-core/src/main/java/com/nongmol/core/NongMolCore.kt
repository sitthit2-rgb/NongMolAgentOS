package com.nongmol.core

import android.content.Context
import android.os.Environment
import android.util.Log
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

object NongMolCore {
    private const val TAG = "NongMolCore"
    
    // Phase 1: โหลด Engine (Auto-Loader)
    fun initEngine(context: Context): Boolean {
        val externalSo = File(Environment.getExternalStorageDirectory(), "/002/engine/libllama.so")
        if (!externalSo.exists()) {
            Log.e(TAG, "libllama.so not found in /002/engine/")
            return false
        }

        try {
            // Android บังคับให้โหลด .so จากพื้นที่ส่วนตัวของแอปเท่านั้น จึงต้องก๊อปปี้เข้ามา
            val internalDir = context.getDir("engine_libs", Context.MODE_PRIVATE)
            val internalSo = File(internalDir, "libllama.so")
            
            FileInputStream(externalSo).use { fis ->
                FileOutputStream(internalSo).use { fos ->
                    fis.copyTo(fos)
                }
            }
            
            System.load(internalSo.absolutePath)
            Log.i(TAG, "Engine loaded successfully!")
            return true
        } catch (e: Exception) {
            Log.e(TAG, "Failed to load Engine: ${e.message}")
            return false
        }
    }

    // Phase 2 & 3: เช็คว่าไฟล์สมองพร้อมไหม
    fun isBrainReady(): Boolean {
        val brain = File(Environment.getExternalStorageDirectory(), "/002/models/brain_llama3.gguf")
        return brain.exists()
    }
}
