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
    private fun getStackInfoByThreadName(threadName: String): String {
//        return "---xxx---threadName=$threadName"
        return  SystemThreadGroupUtils.getStackInfoByThreadName(threadName)
    }

    external fun stringFromJNI(): String

    private external fun nativeSetup()

    private external fun nativeCrashInit(
        nativeCrashMonitor:NativeCrashMonitor,
        callback: NativeCrashListener
    )

    external fun nativeCrashCreate()
}