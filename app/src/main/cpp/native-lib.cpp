#include <jni.h>
#include <string>
//#include "include/hello_world.h"
#include "include/planning_main.h"

extern "C" JNIEXPORT jstring

JNICALL
Java_com_example_androidcpptest_MainActivity_stringFromJNI(
        JNIEnv *env,
        jobject /* this */) {

//    myFunction();
//    int ad = add(4, 13);
    int us = useSo();
    std::string str = std::to_string(us);
    std::string hello = "Hello from ";
    std::string h2 = hello + " " + str;

    return env->NewStringUTF(h2.c_str());
}