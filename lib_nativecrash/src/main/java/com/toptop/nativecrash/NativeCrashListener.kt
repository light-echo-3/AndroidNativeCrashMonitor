package com.toptop.nativecrash

import androidx.annotation.Keep

@Keep
fun interface NativeCrashListener {
    @Keep
    fun onCrash(threadName: String?, error: Error?)
}
