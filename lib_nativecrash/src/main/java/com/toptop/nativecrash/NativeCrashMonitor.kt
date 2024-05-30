package com.toptop.nativecrash

import androidx.annotation.Keep

object NativeCrashMonitor {

    @Volatile
    var isInit = false

    init {
        System.loadLibrary("nativecrash")
    }

    fun init(callback: NativeCrashListener) {
        if (isInit) {
            return
        }
        isInit = true
        nativeCrashInit(this, callback)
        nativeSetup()
    }

    @Keep
    @JvmStatic
    private fun getStackInfoByThreadName(threadName: String?, javaThreadName: String?): String {
        javaThreadName?.takeIf {
            it.isNotBlank()
        } ?: threadName?.takeIf {
            it.isNotBlank()
        }?.let {
            return SystemThreadGroupUtils.getStackInfoByThreadName(it)
        }
        return ""
    }

    @Keep
    @JvmStatic
    private fun getCurrentJavaThreadName(): String {
        return  Thread.currentThread().name
    }

    external fun stringFromJNI(): String

    private external fun nativeSetup()

    private external fun nativeCrashInit(
        nativeCrashMonitor:NativeCrashMonitor,
        callback: NativeCrashListener
    )

    external fun nativeCrashCreate()
}