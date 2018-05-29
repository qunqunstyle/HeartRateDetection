package com.example.heartratedect;

import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.RandomAccessFile;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * welcome layout Activity
 * Created by VideoMedicine Group on 2017/9/3.
 * 设置APP video存储，进行心率计算
 * @author GqGAO
 */

public class FormatUtil {
    static {
        System.loadLibrary("opencv_java3");
        System.loadLibrary("swscale-4");
        System.loadLibrary("swresample-2");
        System.loadLibrary("avutil-55");
        System.loadLibrary("avformat-57");
        System.loadLibrary("avfilter-6");
        System.loadLibrary("avcodec-57");
        System.loadLibrary("native-lib");
        Log.d("TRANSCODEC","load library:nativelib");
    }

    public static native float stringFromJNI(String video);
    public static String videopath;
    private static String Tag = "FormatUtil";
    public static String videoName;
    public static String Videopath;
    private static String datepath;
    private static String datename;

    private static final int MSG_PROGRESS_UPDATE = 0x110;
    //文件存贮
    public static  void videoRename(File recAudioFile) {

        videopath = Environment.getExternalStorageDirectory()
        .getAbsolutePath()+  "/HeartRateDect/video/"+ "SucessVideo" + "/";
        videoName = new SimpleDateFormat("yyyyMMddHHmmss")
        .format(new Date()) + ".avi";
        File out = new File(videopath);
        if (!out.exists()){
            out.mkdirs();
            }
        out = new File(videopath, videoName);
        if(recAudioFile.exists())
        recAudioFile.renameTo(out);
        }

    //计时器 一位补位
     public static String format(int num){
        String s = num + "";
        if (s.length() == 1) {
            s = "0" + s;
            }
        return s;
        }

    /*  The most important method to compute
     the heartrate and return the value*/
    public static int GetRate(){
       // String Videopath;
        int heartRate = 0;
        float Dates[] = {89.25f,45.36f,78.78f};
        String content =null;
        // 测试视频的地址：/storage/emulated/0/HeartRateDect/video/SucessVideo/videoMJPG.avi
        Videopath = videopath + videoName;
        Log.d("TRANSCODEC","videopath"+Videopath);
        heartRate = (int)stringFromJNI(Videopath);
        content =Float.toString(Dates[0])+",";
        for(int i=1;i<Dates.length;i++){
            content += Float.toString(Dates[i])+",";
        }
        Log.i(Tag, "content:  " +content);
        writeTxtToFile(Dates,content);
        deleteFile(Videopath);
        Log.i(Tag, "date: "+heartRate);
        return heartRate;

    }

    public static void writeTxtToFile(float date[],String strcontent ){

        datepath = Environment.getExternalStorageDirectory()
                .getAbsolutePath()+  "/HeartRateDect/"+"date"+"/" ;
        datename = "date.txt";
        String strContent = strcontent +"              "+new SimpleDateFormat("yyyy年MM月dd日   HH:mm:ss").format(new Date())+ "\r\n";
        try{
            File file = new File(datepath,datename);
            if (!file.exists()) {

                Log.d("TestFile", "Create the file:" + datepath);
                file.getParentFile().mkdir();
                file.createNewFile();
                System.out.println(file.createNewFile());
            }

            RandomAccessFile raf = new RandomAccessFile(file, "rwd");
            raf.seek(file.length());
            raf.write(strContent.getBytes());
            raf.close();
        }catch (Exception e){
            Log.e("File","Error on write File:"+ e);
        }
    }
  /*  public File makeFilePath(String datepath) {
        File file = null;

        try {
            file = new File(datepath);
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }
*/

    /**
     * 删除单个文件
     * @param   filePath    被删除文件的文件名
     * @return 文件删除成功返回true，否则返回false
     */
    public static boolean deleteFile(String filePath) {
        File file = new File(filePath);
        if (file.isFile() && file.exists()) {
            Log.i(Tag,"delete sucess..");
            file.delete();
            return true;
        }
        return false;
    }
}
