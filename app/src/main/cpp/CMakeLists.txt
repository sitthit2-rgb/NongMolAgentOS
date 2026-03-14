cmake_minimum_required(VERSION 3.10.2)
project("nongmol-native")
add_library(nongmol-core SHARED nongmol-core.cpp)
target_link_libraries(nongmol-core log)
