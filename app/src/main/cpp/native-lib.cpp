#include <jni.h>
#include <HRD.h>
int transCodec(const char*input,const char*output);

extern "C"
JNIEXPORT jfloatArray JNICALL
Java_com_example_heartratedect_FormatUtil_heartRateDetection(
        JNIEnv *env,
        jobject /* this */,
        jstring jString) {
    // Parse the Java string inputted by the JNI interface.
    const char*h264 = env->GetStringUTFChars(jString, NULL);
    const char*mjpeg = "/storage/emulated/0/HeartRateDect/video/SucessVideo/MJPEG.avi";
    // The demo video address.
    // const char*test = "/storage/emulated/0/HeartRateDect/video/SucessVideo/Video.avi";

    jboolean jb = JNI_FALSE;// Mark the JNI array to create a copy type of false
    // Create the float array of Java by JNI function.
    jfloatArray jfArray = env->NewFloatArray(MAX_LENGTH+1);
    jfloat *jf = env->GetFloatArrayElements(jfArray,&jb);
    // Perform video transcoding, H264 transcoding MJPEG
    int transCodecFlag = transCodec(h264,mjpeg);
    // Transcoding failed, indicating video recording failed, jumping out of the JNI interface.
    if(transCodecFlag){
        jf[0] = -2.0f;
    }

    HRD hrd;
    if(hrd.videoGet(mjpeg)){
        // Gaussian pyramid drop sampling.
        hrd.gaussPyramid();
        // Ideal band pass filtering.
        hrd.idealBandPass();
        // Calculate the heart rate value and the bvp wave.
        hrd.caculateHRValue();
        jf[0] = hrd.getHRValue();
        for(int index = 1;index<=MAX_LENGTH;index++){
            jf[index] =perFrame_BVP[index-1];
        }
    }else{
        jf[0] = -3.0f;
    }
    env->ReleaseFloatArrayElements(jfArray,jf,0);
    return jfArray;
}
