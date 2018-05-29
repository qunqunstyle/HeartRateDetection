#include <jni.h>
#include <HRD.h>
int transCodec(const char*input,const char*output);

extern "C"
JNIEXPORT jfloat JNICALL
Java_com_example_heartratedect_FormatUtil_stringFromJNI(
        JNIEnv *env,
        jobject /* this */,
        jstring jstr) {
    // 解析传入的string
    float heartRate;
    const char*h264 = env->GetStringUTFChars(jstr, NULL);
    const char*mjpeg = "/storage/emulated/0/HeartRateDect/video/SucessVideo/MJPEG.avi";
    //const char*test = "/storage/emulated/0/HeartRateDect/video/SucessVideo/Video.avi";
    int transCodecFlag = transCodec(h264,mjpeg);
    if(transCodecFlag){
        return -2.0f;
    }
    HRD hrd;
    if(hrd.videoGet(mjpeg)){
        hrd.gaussPyramid();
        hrd.idealBandPass();
        hrd.caculateHRValue();
        heartRate = hrd.getHRValue();
    }else{
        heartRate = -3.0f;
    }
    return heartRate;
}
