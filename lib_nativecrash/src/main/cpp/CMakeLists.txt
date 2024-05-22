
cmake_minimum_required(VERSION 3.4.1)


project("nativecrash")
include_directories(./include)

set(source_dir ./src)
aux_source_directory(${source_dir} src_list)
aux_source_directory(${source_dir}/crash src_list)
add_library( # Sets the name of the library.
        nativecrash

        # Sets the library as a shared library.
        SHARED

        # Provides a relative path to your source file(s).
        nativecrash.cpp
        ${src_list})



find_library(
        log-lib

        log)
find_library(  jnigraphics-lib
        jnigraphics )

target_link_libraries( # Specifies the target library.
        nativecrash

        # Links the target library to the log library
        # included in the NDK.
        ${log-lib}
        ${jnigraphics-lib}
        android)