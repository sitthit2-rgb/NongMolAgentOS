#include <jni.h>
#include <string>

extern "C" JNIEXPORT jstring JNICALL
Java_com_nongmol_agent_v2_LlamaEngine_doInference(JNIEnv* env, jobject thiz, jstring prompt) {
    return env->NewStringUTF("NongMol Native Engine V6: Ready");
}
