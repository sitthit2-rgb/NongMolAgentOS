package com.nongmol.core;

public class NongMolSoul {
    // ระบบวิเคราะห์คำสั่ง (Action Parser) แบบ mobile-use
    public static String interpret(String aiResponse) {
        if (aiResponse.contains("[CLICK]")) return "ACTION_CLICK";
        if (aiResponse.contains("[TYPE]")) return "ACTION_TYPE";
        if (aiResponse.contains("[OPEN]")) return "ACTION_OPEN";
        return "ACTION_TALK";
    }
}
