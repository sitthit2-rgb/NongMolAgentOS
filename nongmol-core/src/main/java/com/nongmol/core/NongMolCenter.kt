package com.nongmol.core
import java.io.File

class NongMolCenter {
    companion object {
        private const val BASE_PATH = "/storage/emulated/0/002/"
        fun loadEngine(): Boolean = try { System.loadLibrary("llama"); true } catch (e: Exception) { false }
        fun checkFile(path: String): Boolean = File(BASE_PATH + path).exists()
        fun getStatusColor(path: String): String = if (checkFile(path)) "#4ADE80" else "#F87171"
    }
}
