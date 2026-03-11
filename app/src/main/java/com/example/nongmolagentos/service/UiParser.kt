package com.example.nongmolagentos.service
import android.view.accessibility.AccessibilityNodeInfo

class UiParser {
    fun parseScreen(root: AccessibilityNodeInfo?): String {
        val nodes = mutableListOf<String>()
        extract(root, nodes)
        return nodes.joinToString("\n")
    }
    private fun extract(node: AccessibilityNodeInfo?, list: MutableList<String>) {
        if (node == null) return
        if (node.isClickable && node.text != null) {
            list.add("ID: ${node.viewIdResourceName} | Text: ${node.text} | Action: Click")
        }
        for (i in 0 until node.childCount) extract(node.getChild(i), list)
    }
}