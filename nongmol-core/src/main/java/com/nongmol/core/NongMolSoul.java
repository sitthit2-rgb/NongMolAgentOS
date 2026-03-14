package com.nongmol.core;

public class NongMolSoul {
    public static String processResponse(String rawAiResponse) {
        if (rawAiResponse.contains("[ACTION]")) {
            return rawAiResponse.trim();
        }
        return "TALK: " + rawAiResponse;
    }
}
