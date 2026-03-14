#include <jni.h>
#include <string>

extern "C" JNIEXPORT jstring JNICALL
Java_com_nongmol_agent_v2_MainActivity_stringFromJNI(JNIEnv* env, jobject /* this */) {
    return env->NewStringUTF("Neural Engine V3.0 (Qwen-Optimized) Linked");
}
