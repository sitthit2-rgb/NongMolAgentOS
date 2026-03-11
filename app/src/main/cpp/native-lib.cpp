#include <jni.h>
#include <string>
extern "C" JNIEXPORT jstring JNICALL
Java_com_example_nongmolagentos_ai_LlamaWrapper_inferLocal(JNIEnv* env, jobject thiz, jstring prompt) {
    return env->NewStringUTF("NongMol Engine: Processing with Qwen 3.5...");
}