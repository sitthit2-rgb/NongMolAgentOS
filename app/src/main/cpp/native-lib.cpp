#include <jni.h>
#include <string>
#include <android/log.h>

#define LOG_TAG "NongMolNative"
#define LOGD(...) __android_log_print(ANDROID_LOG_DEBUG, LOG_TAG, __VA_ARGS__)

extern "C" JNIEXPORT jstring JNICALL
Java_com_example_nongmolagentos_ai_LlamaWrapper_stringFromJNI(
        JNIEnv* env,
        jobject /* this */) {
    std::string hello = "NongMol Agent Engine: Llama System Online";
    LOGD("Native bridge initialized successfully");
    return env->NewStringUTF(hello.c_str());
}

// จุดนี้จะเป็นที่สำหรับ loadModel และ inferLocal ในอนาคต

extern "C" JNIEXPORT jboolean JNICALL
Java_com_example_nongmolagentos_ai_LlamaWrapper_loadModel(
        JNIEnv* env, jobject thiz, jstring model_path) {
    // โค้ดสำหรับโหลดโมเดลจริงจะอยู่ตรงนี้
    LOGD("Model loading requested...");
    return JNI_TRUE; 
}

extern "C" JNIEXPORT jstring JNICALL
Java_com_example_nongmolagentos_ai_LlamaWrapper_inferLocal(
        JNIEnv* env, jobject thiz, jstring prompt) {
    // โค้ดสำหรับส่ง Prompt ให้ Llama
    LOGD("Inference started...");
    return env->NewStringUTF("AI Response Placeholder");
}
