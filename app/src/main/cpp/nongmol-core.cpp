#include <jni.h>
#include <string>

extern "C" JNIEXPORT jstring JNICALL
Java_com_nongmol_agent_v2_LlamaEngine_doInference(JNIEnv* env, jobject thiz, jstring prompt) {
    const char* p = env->GetStringUTFChars(prompt, 0);
    std::string response = "NongMol AI Local Engine V6: Active";
    env->ReleaseStringUTFChars(prompt, p);
    return env->NewStringUTF(response.c_str());
}
