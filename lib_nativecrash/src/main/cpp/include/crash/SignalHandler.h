//
// Created by admin on 2021/7/9.
//

#ifndef NDKACTUALCOMBAT_SIGNALHANDLER_H
#define NDKACTUALCOMBAT_SIGNALHANDLER_H
#include <signal.h>
#include <string>
#include <unistd.h>
#include "../include/crash/CrashAnalyser.h"
namespace native_crash_monitor {

extern bool installSignalHandlers();
extern void installAlternateStack();

} // namespace native_crash_monitor

#endif //NDKACTUALCOMBAT_SIGNALHANDLER_H
