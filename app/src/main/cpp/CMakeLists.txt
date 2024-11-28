#cmake_minimum_required(VERSION 3.22.1)
#project("androidcpptest")
#set(LIBRARY_PATH ${CMAKE_SOURCE_DIR}/lib)
#
#add_library(${CMAKE_PROJECT_NAME} SHARED
#        native-lib.cpp)
##### 添加第三方头文件
#target_include_directories(native-lib PRIVATE ${CMAKE_SOURCE_DIR}/src/main/cpp/include)
#
##### 引入第三方共享库
##add_library(planning_main SHARED IMPORTED)
##set_target_properties(planning_main PROPERTIES IMPORTED_LOCATION ${CMAKE_SOURCE_DIR}/src/main/jniLibs/${ANDROID_ABI}/libplanning_main.so)
#
#add_library(planning_main SHARED IMPORTED)
#set_target_properties(planning_main PROPERTIES IMPORTED_LOCATION ${CMAKE_SOURCE_DIR}/src/main/jniLibs/${ANDROID_ABI}/libplanning_main.so)
#
#target_link_libraries(${CMAKE_PROJECT_NAME}
#        android
#        planning_main
#        log
#        ${LIBRARY_PATH}/libnative_lib.so
#)

cmake_minimum_required(VERSION 3.22.1)
project("androidcpptest")

# 定义库路径
#set(LIBRARY_PATH ${CMAKE_SOURCE_DIR}/lib)
set(LIBRARY_PATH ${CMAKE_SOURCE_DIR}/../jniLibs/${ANDROID_ABI})

# 添加目标库
add_library(${CMAKE_PROJECT_NAME} SHARED native-lib.cpp)

# 添加第三方头文件
target_include_directories(${CMAKE_PROJECT_NAME} PRIVATE ${CMAKE_SOURCE_DIR}/src/main/cpp/include)

# 引入第三方共享库 libplanning_main
add_library(planning_main SHARED IMPORTED)
set_target_properties(planning_main PROPERTIES IMPORTED_LOCATION ${LIBRARY_PATH}/libplanning_main.so)
#set_target_properties(planning_main PROPERTIES IMPORTED_LOCATION ${LIBRARY_PATH}/${ANDROID_ABI}/libplanning_main.so)

# 链接库
target_link_libraries(${CMAKE_PROJECT_NAME}
        android
        log
        planning_main
)
