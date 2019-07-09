
#include <jni.h>

#include <cstdlib>
#include <cstring>

#include <random>
#include <string>

#ifndef FUNCTION_NAME
#define FUNCTION_NAME(fn) Java_com_jflopezfernandez_rng_MainActivity_ ## fn
#else
#error "FUNCTION_NAME macro has been defined by something else already"
#endif // FUNCTION_NAME

#ifndef FUNCTION
#define FUNCTION(returnType, name) extern "C" JNIEXPORT returnType JNICALL FUNCTION_NAME(name)(JNIEnv* env, jobject /* this */)
#else
#error "FUNCTION macro has already been defined somewhere else"
#endif // FUNCTION

FUNCTION(jstring, nativeCall)
{
    return env->NewStringUTF("Macro worked");
}

static std::random_device entropy;

FUNCTION(jint, uniformRandomInt)
{
    const auto max = 100;
    const auto min =   0;

    std::mt19937_64 generator(entropy());
    std::uniform_int_distribution<jint> distribution(min, max);

    return distribution(generator);
}

FUNCTION(jdouble, uniformRandomReal)
{
    std::mt19937_64 generator(entropy());
    std::uniform_real_distribution<jdouble> distribution(-100.0, 100.0);

    return distribution(generator);
}

FUNCTION(jdouble, normalRandomReal)
{
    std::mt19937_64 generator(entropy());
    std::normal_distribution<jdouble> distribution(0.0, 1.0);

    return distribution(generator);
}
