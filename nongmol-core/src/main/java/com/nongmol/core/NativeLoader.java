package com.nongmol.core;
import java.io.File;
public class NativeLoader {
    public static boolean load(String path) {
        File f = new File(path);
        if (f.exists()) {
            try { System.load(f.getAbsolutePath()); return true; } 
            catch (Exception e) { return false; }
        }
        return false;
    }
}
