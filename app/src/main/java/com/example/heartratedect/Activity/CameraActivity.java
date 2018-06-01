package com.example.heartratedect.Activity;


import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.hardware.Camera;
import android.hardware.camera2.CameraManager;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.Surface;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.heartratedect.CameraProgressBar;
import com.example.heartratedect.CustomProgressDialog;
import com.example.heartratedect.FormatUtil;
import com.example.heartratedect.MainActivity;
import com.example.heartratedect.MySurfaceView;
import com.example.heartratedect.R;

import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

import static com.example.heartratedect.FormatUtil.MAX_LENGTH;
import static com.example.heartratedect.FormatUtil.deleteFile;
import static com.example.heartratedect.FormatUtil.writeTxtToFile;

/**
 * main layout Activity
 * Created by VideoMedicine Group on 2017/9/3.
 * 心率检测-录视频-检测-出数据
 *
 * @author GqGAO
 */

public class CameraActivity extends Fragment {
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


    private final static String TAG_CAMERA_ACTIVITY = "CameraActivity";
    private SurfaceHolder surfaceHolder;
    private Button testButton;
    private Camera camera;
    private CameraProgressBar mProgressbar;
    private Camera.Parameters mParams;
    private MediaRecorder mediaRecorder;     //录制视频类
    protected boolean isPreview = false;             //摄像区域是否准备良好  
    private boolean isRecording = true;           // true表示没有录像，点击开始；false表示正在录像，点击暂停  

    private File mRecVideoPath;
    private File mRecAudioFile;
    private MySurfaceView surfaceView;
    private SurfaceHolder cameraSurfaceHolder;
    private static volatile int heartRateValue;
    private static TextView Prompt;
    private TextView show;
    private String heartRate = "0";      //心率值
    private static volatile boolean completeHRD = false;
    private TextView heartRateLabel; //心率显示框
    private static CustomProgressDialog dialog;
    private static Handler mTimeHandler1;
    private static Handler mTimeHandler2;
    private static float data[] = new float[MAX_LENGTH];
    private static final int MAX_RECORD_TIME = 10 * 1000;
    private static final int PLUSH_PROGRESS = 100;
    private final int max = MAX_RECORD_TIME / PLUSH_PROGRESS;

    public static float[] getData() {
        return data;
    }
    public View onCreateView(final LayoutInflater inflater,
                             final ViewGroup container, Bundle savedInstanceState) {
        mTimeHandler1 = new Handler() {
            @Override
            public void handleMessage(android.os.Message msg) {
                super.handleMessage(msg);
                if (msg.what == 0) {
                    Prompt.setText("正在计算中..."); //View.ininvalidate()
                    sendEmptyMessageDelayed(0,1000);

                }
            }
        };
        mTimeHandler2 = new Handler() {
            @Override
            public void handleMessage(android.os.Message msg) {
                super.handleMessage(msg);
                if (msg.what == 0) {
                    System.out.println("heartRateValue = "+heartRateValue);
                    if(heartRateValue!=0){
                        Prompt.setVisibility(View.INVISIBLE);
                        heartRateLabel.setVisibility(View.VISIBLE);
                        show.setVisibility(View.VISIBLE);
                        heartRateLabel.setText(heartRate);
                        testButton.setVisibility(View.VISIBLE);
                    }
                    sendEmptyMessageDelayed(0,0);
                }
            }
        };

        super.onCreate(savedInstanceState);
        final Handler handler = new Handler();
        final View CameraLayout = inflater.inflate(R.layout.activity_camera,container,false);
        surfaceView = null;
        mRecVideoPath = new File(Environment.getExternalStorageDirectory()
                .getAbsolutePath() + "/HeartRateDect/video/ErrorVideo/");
        if (!mRecVideoPath.exists()) {
            mRecVideoPath.mkdirs();
        }
        testButton = (Button)CameraLayout.findViewById(R.id.testButton);
        testButton.setOnClickListener(new MyListener());
        mProgressbar = (CameraProgressBar)CameraLayout.findViewById(R.id.camera_ProgressBar);
        heartRateLabel = (TextView)CameraLayout. findViewById(R.id.heartRateLabel);
        heartRateLabel.setVisibility(View.INVISIBLE);
        Prompt = (TextView)CameraLayout.findViewById(R.id.Prompt);
        show = (TextView)CameraLayout.findViewById(R.id.show);
        show.setVisibility(View.INVISIBLE);

        // mTimeHandler.sendEmptyMessageDelayed(0, 1000);
        Prompt.setText("请点击圆框开始");
        mProgressbar.setMaxProgress(max);
        initView();
        surfaceView = (MySurfaceView)CameraLayout. findViewById(R.id.camera_mysurfaceview);
        cameraSurfaceHolder = surfaceView.getHolder();
        cameraSurfaceHolder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                try {
                    initView();
                    camera.setPreviewDisplay(holder);
                    isPreview = true;
                    camera.startPreview();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                cameraSurfaceHolder = holder;
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
                cameraSurfaceHolder = holder;
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                endRecord();
                releaseCamera();
            }
        });
        // This method was deprecated in API level 11. this is ignored, this value is set automatically when needed.   
        cameraSurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        surfaceView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testButton.setVisibility(View.INVISIBLE);
                mTimeHandler1.removeMessages(0);
                mTimeHandler2.removeMessages(0);
                //isRecording = true;
                heartRateValue = 0;
                heartRateLabel.setVisibility(View.INVISIBLE);
                show.setVisibility(View.INVISIBLE);
                Prompt.setVisibility(View.VISIBLE);
                Prompt.setText("正在录制...");

                // mTimeHandler1.sendEmptyMessageDelayed(0, 1000);
                Toast.makeText(getContext(), "请将脸部置于圆框中，并保持不动", Toast.LENGTH_LONG).show();

                if (isRecording) {
                    if (isPreview) {
                        camera.stopPreview();
                        camera.release();
                        camera = null;
                    }

                    if (null == mediaRecorder) {
                        mediaRecorder = new MediaRecorder();
                    } else {
                        mediaRecorder.reset();
                    }

                    camera = Camera.open(Camera.CameraInfo.CAMERA_FACING_FRONT);
                    camera.setDisplayOrientation(90);
                    camera.enableShutterSound(false);
                    Camera.Parameters parameters = camera.getParameters();
                    parameters.setPreviewFrameRate(25);
                    camera.setParameters(parameters);
                    camera.unlock();
                    mediaRecorder.setCamera(camera);
                    mediaRecorder.setOrientationHint(270);
                    mediaRecorder.setPreviewDisplay(cameraSurfaceHolder.getSurface());
                    mediaRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
                    //
                    mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.DEFAULT);
                    mediaRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.H264);
                    mediaRecorder.setVideoSize(640, 480);

                    Log.i(TAG_CAMERA_ACTIVITY, "mediaRecorder set sucess");

                    try {
                        mRecAudioFile = File.createTempFile("Vedio", ".avi", mRecVideoPath);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Log.i(TAG_CAMERA_ACTIVITY, "..." + mRecAudioFile.getAbsolutePath());
                    mediaRecorder.setOutputFile(mRecAudioFile.getAbsolutePath());
                    try {
                        mediaRecorder.prepare();
                        mediaRecorder.start();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    isRecording = !isRecording;
                    Log.i(TAG_CAMERA_ACTIVITY, "=====开始录制视频=====");
                }

                Observable.interval(100,
                        TimeUnit.MILLISECONDS,
                        AndroidSchedulers.mainThread()).take(max).subscribe(new Subscriber<Long>() {
                    @Override
                    public void onCompleted() {
                        // stop();
                        //mediaRecorder.reset();

                        mediaRecorder.stop();
                        mediaRecorder.reset();
                        mediaRecorder.release();
                        mediaRecorder = null;

                        FormatUtil.videoRename(mRecAudioFile);
                        Log.e(TAG_CAMERA_ACTIVITY, "=====录制完成，已保存=====");
                        isRecording = !isRecording;

                        mTimeHandler1.sendEmptyMessageDelayed(0,0);

                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                heartRateValue = getHeartRateValue();
                                heartRate= Integer.toString(heartRateValue);
                                Log.d(TAG_CAMERA_ACTIVITY,"HeartRateValue = "+heartRate);
                            }
                        }).start();
                        mTimeHandler2.sendEmptyMessageDelayed(0,0);
                        mProgressbar.reset();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Long aLong) {
                        mProgressbar.setProgress(mProgressbar.getProgress() + 1);
                        System.out.println("11");
                    }
                });

            }

        });
        return CameraLayout;
    }

    private void initView(){
        // 初始化摄像头  
        camera = Camera.open(Camera.CameraInfo.CAMERA_FACING_FRONT);
        Camera.Parameters parameters = camera.getParameters();
        // 每秒30帧  
        parameters.setPreviewFrameRate(30);
        camera.setParameters(parameters);
        camera.setDisplayOrientation(90);
    }

    public void stop(){
        if (mediaRecorder != null) {
            try {
                mediaRecorder.stop();
            } catch (IllegalStateException e) {
                // TODO 如果当前java状态和jni里面的状态不一致，
                //e.printStackTrace();
                mediaRecorder = null;
                mediaRecorder = new MediaRecorder();
                mediaRecorder.stop();
            }
            mediaRecorder.release();
            mediaRecorder = null;
        }
    }

    @Override
    public void onPause() {

        super.onPause();
    }
    private void endRecord() {
        //反正多次进入，比如surface的destroy和界面onPause
      /*  if (!isRecording) {
            return;
        }
        isRecording = false;*/
        try {
            if (mediaRecorder != null) {
                mediaRecorder.stop();
                mediaRecorder.reset();
                mediaRecorder.release();
                //orientationEventListener.enable();
                mediaRecorder = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 释放摄像头资源
     */
    private void releaseCamera() {
        try {
            if (camera != null) {
                camera.setPreviewCallback(null);
                camera.stopPreview();
                camera.lock();
                camera.release();
                camera = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public static int getHeartRateValue(){
        int heartRate = 0;
        String content;
        // Test video address:
        // /storage/emulated/0/HeartRateDect/video/SucessVideo/videoMJPG.avi
        FormatUtil.videoPath = FormatUtil.videoPath + FormatUtil.videoName;
        Log.d("TRANSCODEC","videoPath"+FormatUtil.videoPath);
        // get the HRD and BVP。
        data = FormatUtil.heartRateDetection(FormatUtil.videoPath);
        heartRate = (int)data[0];
        // Log data to a local file.
        content =Integer.toString(heartRate)+",";
        for(int i=1;i<data.length;i++){
            content += Float.toString(data[i])+",";
        }
        Log.i(TAG_CAMERA_ACTIVITY, "content:  " +content);
        writeTxtToFile(content);
        // Delete the video.
        deleteFile(FormatUtil.videoPath);
        Log.i(TAG_CAMERA_ACTIVITY, "data: "+heartRate);
        completeHRD = true;
        return heartRate;
    }

    class MyListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            mTimeHandler1.removeMessages(0);
            mTimeHandler2.removeMessages(0);
            Intent intent=new Intent(getActivity(),ResultActivity.class);
            intent.putExtra("heartRate",heartRate);
            intent.putExtra("data",data);
            startActivity(intent);
        }
    }
}
