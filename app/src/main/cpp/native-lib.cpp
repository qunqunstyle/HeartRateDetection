#include <jni.h>
#include <HRD.h>
int transCodec(const char*input,const char*output);

extern "C"
JNIEXPORT jfloatArray JNICALL
Java_com_example_heartratedect_FormatUtil_heartRateDetection(
        JNIEnv *env,
        jobject /* this */,
        jstring jstring) {
    // 解析传入的string
    const char*h264 = env->GetStringUTFChars(jstring, NULL);
    const char*mjpeg = "/storage/emulated/0/HeartRateDect/video/SucessVideo/MJPEG.avi";
    // 测试视频地址
    // const char*test = "/storage/emulated/0/HeartRateDect/video/SucessVideo/Video.avi";

    // 创建jfloatArray数组
    jboolean jb = JNI_FALSE;//标记JNI数组创建的复制类型为false
    jfloatArray jfarray = env->NewFloatArray(MAX_LENGTH+1);
    jfloat *jf = env->GetFloatArrayElements(jfarray,&jb);
    // 进行视频转码，H264转MJPEG
    int transCodecFlag = transCodec(h264,mjpeg);
    // 转码失败，说明视频录制失败，从JNI接口中跳出
    if(transCodecFlag){
        jf[0] = -2.0f;
    }

    HRD hrd;
    if(hrd.videoGet(mjpeg)){
        // 高斯金字塔降采样
        hrd.gaussPyramid();
        // 理想带通滤波
        hrd.idealBandPass();
        // 计算心率值
        hrd.caculateHRValue();
        jf[0] = hrd.getHRValue();
        for(int index = 1;index<=MAX_LENGTH;index++){
            jf[index] =perFrame_BVP[index-1];
        }
    }else{
        jf[0] = -3.0f;
    }
    env->ReleaseFloatArrayElements(jfarray,jf,0);
    return jfarray;
}
