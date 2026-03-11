package com.example.nongmolagentos.ai
class HybridRouter {
    fun shouldLinkToCloud(hasInternet: Boolean, isRamLow: Boolean): Boolean {
        // ถ้าแรมต่ำกว่า 1GB หรือเน็ตแรง อาจจะโยนไป Cloud
        return !hasInternet || isRamLow
    }
}