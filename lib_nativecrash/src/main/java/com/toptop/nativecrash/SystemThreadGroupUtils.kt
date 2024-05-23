package com.toptop.nativecrash

import android.os.Looper
import android.text.TextUtils
import android.util.Log
import androidx.annotation.Keep

object SystemThreadGroupUtils {

    private var systemThreadGroup: ThreadGroup? = null

    init {
        try {
            val threadGroupClass = Class.forName("java.lang.ThreadGroup")
            val threadGroupField = threadGroupClass.getDeclaredField("systemThreadGroup")
            threadGroupField.isAccessible = true
            systemThreadGroup = threadGroupField[null] as ThreadGroup
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    /**
     * 根据线程名获取当前线程的堆栈信息
     */
    fun getStackInfoByThreadName(threadName: String): String {
        val thread = getThreadByName(threadName)
        val sb = StringBuilder()
        val stackTraceElements = thread?.getStackTrace()
        stackTraceElements?.let {
            for (stackTraceElement in stackTraceElements) {
                sb.append(stackTraceElement.toString()).append("\r\n")
            }
        }
        return sb.toString()
    }

    private fun getThreadByName(threadName: String): Thread? {
        if (TextUtils.isEmpty(threadName)) {
            return null
        }
        var theThread: Thread? = null
        if (threadName == "main") {
            theThread = Looper.getMainLooper().thread
        } else {
            var threadArray = arrayOf<Thread?>()
            try {
                val threadSet = getAllStackTraces().keys
                threadArray = threadSet.toTypedArray<Thread?>()
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
            }
            for (thread in threadArray) {
                if (thread?.name == threadName) {
                    theThread = thread
                    Log.e("TAG", "find it.$threadName")
                }
            }
        }
        return theThread
    }

    //获取线程堆栈的map.
    private fun getAllStackTraces(): Map<Thread?, Array<StackTraceElement>?> {
        return if (systemThreadGroup == null) {
            Thread.getAllStackTraces()
        } else {
            val map: MutableMap<Thread?, Array<StackTraceElement>?> = HashMap()
            var count: Int = systemThreadGroup?.activeCount() ?: 0
            val threads = arrayOfNulls<Thread>(count + count / 2)
            Log.d("TAG", "activeCount: $count")
            //赋值所有存活对象到threads
            count = systemThreadGroup?.enumerate(threads) ?: 0
            for (i in 0 until count) {
                try {
                    map[threads[i]] = threads[i]?.getStackTrace()
                } catch (e: Throwable) {
                    Log.e("TAG", "fail threadName: " + threads[i]?.name, e)
                }
            }
            map
        }
    }
}