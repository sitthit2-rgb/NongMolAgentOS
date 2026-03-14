package com.nongmol.core;
import okhttp3.*;
import java.io.IOException;
public class TeacherEngine {
    private String apiKey = "ใส่_API_KEY_จริง_ของพี่ตรงนี้"; 
    private final OkHttpClient client = new OkHttpClient();
    public TeacherEngine(String key) { if(key != null) this.apiKey = key; }
    public void ask(String prompt, Callback cb) {
        RequestBody body = RequestBody.create("{\"contents\":[{\"parts\":[{\"text\":\"" + prompt + "\"}]}]}", 
            MediaType.get("application/json; charset=utf-8"));
        Request req = new Request.Builder()
            .url("https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash:generateContent?key=" + apiKey)
            .post(body).build();
        client.newCall(req).enqueue(cb);
    }
}
