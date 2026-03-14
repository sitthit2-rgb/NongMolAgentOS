cmake_minimum_required(VERSION 3.22.1)
project("nongmolagentos")

# เชื่อมโยง Library Binary ที่คัดลอกมา
add_library(llama SHARED IMPORTED)
set_target_properties(llama PROPERTIES IMPORTED_LOCATION ${CMAKE_SOURCE_DIR}/../jniLibs/arm64-v8a/libllama.so)

add_library(native-lib SHARED native-lib.cpp)
target_link_libraries(native-lib llama log)
