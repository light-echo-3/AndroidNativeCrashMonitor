package com.toptop.nativecrash

fun interface NativeCrashListener {
    fun onCrash(threadName: String?, error: Error?)
}
