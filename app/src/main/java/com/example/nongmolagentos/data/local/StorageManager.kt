package com.example.nongmolagentos.data.local
import android.content.Context
import java.io.File

class StorageManager(private val context: Context) {
    fun getModelPath(): String {
        val modelDir = File(context.filesDir, "models")
        if (!modelDir.exists()) modelDir.mkdirs()
        return modelDir.absolutePath
    }
    fun listModels(): List<String> = File(getModelPath()).list()?.toList() ?: emptyList()
}