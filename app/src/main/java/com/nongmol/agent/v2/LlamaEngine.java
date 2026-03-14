package com.nongmol.agent.v2;
public class LlamaEngine {
    static { System.loadLibrary("nongmol-core"); }
    public native String doInference(String prompt);
}
