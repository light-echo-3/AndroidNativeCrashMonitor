//
// Created by admin on 2021/8/20.
//

#ifndef VIDEO_AUDIO_CRASHANALYSER_H
#define VIDEO_AUDIO_CRASHANALYSER_H

#include <pthread.h>
#include <signal.h> // 添加对 siginfo_t 的定义
#include <unistd.h> // 添加对 pid_t 的定义
#include "log.h"
#include <string>

#define BACKTRACE_FRAMES_MAX 32

namespace native_crash_monitor {

    typedef struct native_handler_context_struct {
        int code;
        siginfo_t *si;
        void *sc;
        pid_t pid;
        pid_t tid;
        const char *processName;
        const char *threadName;
        std::string javaThreadName;
        int frame_size;
        uintptr_t frames[BACKTRACE_FRAMES_MAX];
    } native_handler_context;

    void initCondition();
    void *threadCrashMonitor(void *argv);
    void waitForSignal();
    void analysisNativeException();
    void notifyCaughtSignal(int code, siginfo_t *si, void *sc);
    void copyInfo2Context(int code, siginfo_t *si, void *sc);

} // namespace native_crash_monitor

#endif // VIDEO_AUDIO_CRASHANALYSER_H
