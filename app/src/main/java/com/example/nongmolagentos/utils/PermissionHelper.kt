package com.example.nongmolagentos.utils
import android.content.Context
import android.content.Intent
import android.provider.Settings
import android.net.Uri

class PermissionHelper(private val context: Context) {
    fun hasOverlayPermission(): Boolean = Settings.canDrawOverlays(context)
    fun requestOverlayPermission() {
        val intent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:${context.packageName}"))
        context.startActivity(intent)
    }
}