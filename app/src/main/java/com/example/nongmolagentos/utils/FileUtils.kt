package com.example.nongmolagentos.utils
import java.io.*

class FileUtils {
    fun copyAssetToFile(inputStream: InputStream, outputFile: File) {
        inputStream.use { input ->
            FileOutputStream(outputFile).use { output ->
                input.copyTo(output)
            }
        }
    }
}