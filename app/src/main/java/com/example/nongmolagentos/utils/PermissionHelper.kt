package com.example.nongmolagentos.utils

import android.content.Context
import android.provider.Settings
import android.content.Intent
import android.net.Uri

class PermissionHelper(private val context: Context) {
    fun hasOverlayPermission(): Boolean {
        return Settings.canDrawOverlays(context)
    }
}
