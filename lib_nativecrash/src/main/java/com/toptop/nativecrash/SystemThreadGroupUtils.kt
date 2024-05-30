package com.toptop.nativecrash

import android.os.Looper
import android.text.TextUtils
import android.util.Log

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
        stackTraceElements?.forEach { stackTraceElement->
            sb.append(stackTraceElement.toString()).append("\r\n")
        }
        return sb.toString()
    }

    private fun getThreadByName(threadName: String): Thread? {
        if (TextUtils.isEmpty(threadName)) {
            return null
        }
        return if (threadName == "main") {
            Looper.getMainLooper().thread
        } else {
            getAllActiveThread()?.find{ thread ->
                thread?.name?.startsWith(threadName) ?: false
            }
        }
    }

    //获取线程堆栈的map.
    private fun getAllActiveThread(): Array<Thread?>? {
        return systemThreadGroup?.let {
            var count: Int = it.activeCount()
            val threads = arrayOfNulls<Thread>(count + count / 2)
            Log.d("TAG", "activeCount: $count")
            //赋值所有存活对象到threads
            count = it.enumerate(threads)
            return threads.sliceArray(0 until count)
        }
    }
}