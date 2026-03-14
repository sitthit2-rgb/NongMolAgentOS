#include <jni.h>
#include <string>

extern "C" JNIEXPORT jstring JNICALL
Java_com_nongmol_agent_v2_MainActivity_nativeInference(JNIEnv* env, jobject thiz, jstring prompt) {
    // อนาคตใส่โค้ดเรียก Qwen GGUF ตรงนี้
    return env->NewStringUTF("Local Engine V6.1 Active");
}
