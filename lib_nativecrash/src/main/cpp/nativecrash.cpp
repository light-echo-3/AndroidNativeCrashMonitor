#include <jni.h>
#include <string>
#include "native-lib.h"


extern "C" JNIEXPORT jstring JNICALL
Java_com_toptop_nativecrash_NativeCrashMonitor_stringFromJNI(
        JNIEnv* env,
        jobject /* this */) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}
extern "C"
JNIEXPORT void JNICALL
Java_com_toptop_nativecrash_NativeCrashMonitor_nativeSetup(JNIEnv *env, jobject thiz) {
    installAlternateStack();
    installSignalHandlers();
}
extern "C"
JNIEXPORT void JNICALL
Java_com_toptop_nativecrash_NativeCrashMonitor_nativeCrashInit(JNIEnv *env, jobject thiz,
                                                               jobject nativeCrashMonitor,
                                                               jobject callback) {
    //获取全局的jvm
    JavaVM *javaVm;
    env->GetJavaVM(&javaVm);
    //生成全局对象
    callback = env->NewGlobalRef(callback);
    jclass nativeCrashMonitorClass = env->GetObjectClass(nativeCrashMonitor);
    nativeCrashMonitorClass = (jclass) env->NewGlobalRef(nativeCrashMonitorClass);
    auto *jniBridge = new JNIBridge(javaVm, callback, nativeCrashMonitorClass);
    pthread_t pthread;
    //创建一个线程
    initCondition();
    //ret=0代表创建成功
    int ret = pthread_create(&pthread, NULL, threadCrashMonitor, jniBridge);
    if (ret < 0) {
        LOGE("%s", "pthread_create error");
    }
}

extern "C"
JNIEXPORT void JNICALL
Java_com_toptop_nativecrash_NativeCrashMonitor_nativeCrashCreate(JNIEnv *env, jobject thiz) {
    int *num = NULL;
    *num = 100;
}