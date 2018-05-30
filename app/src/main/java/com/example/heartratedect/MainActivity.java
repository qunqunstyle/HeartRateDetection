package com.example.heartratedect;

import android.Manifest;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View.OnClickListener;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.heartratedect.Activity.BaseActivity;
import com.example.heartratedect.Activity.CameraActivity;
import com.example.heartratedect.Activity.Tab_Data_Fragment;
import com.example.heartratedect.Activity.Tab_Setting_Fragment;

import java.util.ArrayList;
import java.util.List;


/**
 * welcome layout Activity
 * 设置APP启动动画
 * @author GqGAO
 */
//@RuntimePermissions
public class MainActivity extends BaseActivity implements OnClickListener {



    String[] permissions = new String[]{Manifest.permission.CAMERA , Manifest.permission.WRITE_EXTERNAL_STORAGE};
    List<String> mPermissionList = new ArrayList<>();

   // private ImageView welcomeImg = null;
    private static final int PERMISSION_REQUEST = 1;
        // 检查权限

    private void checkPermission() {
        mPermissionList.clear();

         //判断哪些权限未授予
        for(int i = 0; i < permissions.length; i++) {
            if (ContextCompat.checkSelfPermission(this, permissions[i]) != PackageManager.PERMISSION_GRANTED) {
                mPermissionList.add(permissions[i]);
            }
        }
        /**
         * 判断是否为空
                */
        if (mPermissionList.isEmpty()) {//未授予的权限为空，表示都授予了

        } else {//请求权限方法
            String[] permissions = mPermissionList.toArray(new String[mPermissionList.size()]);//将List转为数组
            ActivityCompat.requestPermissions(MainActivity.this, permissions, PERMISSION_REQUEST);
        }
    }

 /*
     * 响应授权
     * 这里不管用户是否拒绝，都进入首页，不再重复申请权限
    **/
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_REQUEST:

                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
                break;
        }
    }

  //  private Tab_Data_Fragment dataFragment;
    private Tab_Setting_Fragment settingFragment;
    private CameraActivity cameraActivity;
   // private Tab_Date_Fragment datefragment;

    private View camera_view;
    private View data_view;
    private View setting_view;

    //private ImageView img_data;
    private ImageView img_camera;
    private ImageView img_setting;

    //private TextView tv_data;
    private TextView tv_camera;
    private TextView tv_setting;

    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;

    private Bundle loginBundle;
    private String userName;
    private Bundle DataBundle;
    //private Bundle dateBundle;
    private Bundle SettingBundle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkPermission();
        initViews();

        fragmentManager = getFragmentManager();
        // 第一次启动时选中第0个tab
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        setTabSelection(0);

       /*// Intent intent1= this.getIntent();
       // String data = intent1.getStringExtra("heartrate");
        int id = getIntent().getIntExtra("id",0);
        if(id == 1){

            // FragmentManager fmanger = getFragmentManager();
            // 每次选中之前先清除掉上次的选中状态
            clearSelection();
            // 开启一个Fragment事务
            FragmentTransaction transaction1 = fragmentManager.beginTransaction();
            // 先隐藏掉所有的Fragment，以防止有多个Fragment显示在界面上的情况
            hideFragments(transaction1);
            transaction1.replace(R.id.content, datefragment);
            transaction1.commit();
            img_camera.setImageResource(R.drawable.news_selected);
            tv_camera.setTextColor(Color.parseColor("#1296db"));


        }*/
    }


    /*String data = intent1.getStringExtra("heartrate");
        heartdateshow.setText(data);*/
    private void initViews() {
        //camera_view = findViewById(R.id.activity_camera);
        camera_view = findViewById(R.id.news_layout);
       // data_view = findViewById(R.id.date_layout);
        setting_view = findViewById(R.id.setting_layout);
        img_camera = (ImageView) findViewById(R.id.news_image);
       // img_data = (ImageView)findViewById(R.id.date_image);
        img_setting = (ImageView) findViewById(R.id.setting_image);
        tv_camera = (TextView) findViewById(R.id.news_text);
        //tv_data = (TextView) findViewById(R.id.date_text);
        tv_setting = (TextView) findViewById(R.id.setting_text);
       // camera_view.setOnClickListener(this);
        camera_view.setOnClickListener(this);
        setting_view.setOnClickListener(this);
      //  data_view.setOnClickListener(this);

        // 获取从LoginActivity传过来的用户名，显示在设置里面
        loginBundle = getIntent().getExtras();
        DataBundle = new Bundle();
      //  dateBundle = new Bundle();
        SettingBundle = new Bundle();
        if (loginBundle != null) {
            userName = loginBundle.getString("userName");
            if (userName == null) {
                userName = loginBundle.getString("name");
            }
            Log.i("TAG", "穿过来的值为" + userName);
        } else {
            Log.i("TAG", "extras为空");
        }
        DataBundle.putString("userName", userName);
       // dateBundle.putString("userName", userName);
        SettingBundle.putString("userName", userName);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.news_layout:
                Log.i("TAG", "模拟点击了");
                setTabSelection(0);
                break;
          /*  case R.id.date_layout:
                setTabSelection(2);
                break;*/
            case R.id.setting_layout:
                setTabSelection(1);
                break;
            default:
                break;
        }
    }

   /* @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == -1) {
            MainActivity.this.findViewById(R.id.iv_refresh).performClick();
        }
    }*/

    public void setTabSelection(int i) {
        // 每次选中之前先清除掉上次的选中状态
        clearSelection();
        // 开启一个Fragment事务
        transaction = fragmentManager.beginTransaction();
        // 先隐藏掉所有的Fragment，以防止有多个Fragment显示在界面上的情况
        hideFragments(transaction);
        switch (i) {
            case 0:
                Log.i("TAG", "单击走的显示流程");
                // 当点击了消息tab时，改变控件的图片和文字颜色
                img_camera.setImageResource(R.drawable.news_selected);
                tv_camera.setTextColor(Color.parseColor("#1296db"));
                if (cameraActivity == null) {
                    // 如果dataFragment为空，则创建一个并添加到界面上
                    cameraActivity = new CameraActivity();
                    cameraActivity.setArguments(DataBundle);
                    transaction.add(R.id.content, cameraActivity);
                    Log.i("TAG", "dataFragment为空");
                } else {
                    // 如果dataFragment不为空，则直接将它显示出来
                    Log.i("TAG", "dataFragment不为空。。。。。。");
                    // dataFragment.setArguments(DataBundle);
                    transaction.show(cameraActivity);
                }
                break;

            case 1:
                // 当点击了设置tab时，改变控件的图片和文字颜色
                img_setting.setImageResource(R.drawable.setting_selected);
                tv_setting.setTextColor(Color.parseColor("#1296db"));
                if (settingFragment == null) {
                    // 如果settingFragment为空，则创建一个并添加到界面上
                    settingFragment = new Tab_Setting_Fragment();
                    // 从activity传递给Fragment里面去
                    settingFragment.setArguments(SettingBundle);
                    transaction.add(R.id.content, settingFragment);
                    Log.i("TAG", "settingFragment为空。。。。。。");
                } else {
                    // 如果settingFragment不为空，则直接将它显示出来
                    // settingFragment.setArguments(bundle);
                    transaction.show(settingFragment);
                    Log.i("TAG", "settingFragment不为空。。。。。。");
                }
                break;
            /*case 2:
                // 当点击了结果页tab时，改变控件的图片和文字颜色
                img_data.setImageResource(R.drawable.date_select);
                tv_data.setTextColor(Color.parseColor("#1296db"));
                if (datefragment == null) {
                    // 如果dateFragment为空，则创建一个并添加到界面上
                    datefragment = new Tab_Date_Fragment();
                    // 从activity传递给Fragment里面去
                    datefragment.setArguments(dateBundle);
                    transaction.add(R.id.content, datefragment);
                    Log.i("TAG", "settingFragment为空。。。。。。");
                } else {
                    // 如果dateFragment不为空，则直接将它显示出来
                    // dateFragment.setArguments(bundle);
                    transaction.show(datefragment);
                    Log.i("TAG", "dateFragment不为空。。。。。。");
                }
                break;*/

            default:
                break;
        }
        transaction.commit();
    }


    private void clearSelection() {
        img_camera.setImageResource(R.drawable.news_unselected);
        tv_camera.setTextColor(Color.parseColor("#82858b"));
        img_setting.setImageResource(R.drawable.setting_unselected);
        //img_data.setImageResource(R.drawable.date_select);
        tv_setting.setTextColor(Color.parseColor("#82858b"));
        //tv_data.setTextColor(Color.parseColor("#82858b"));
    }

   /* *
     * 将所有的Fragment都置为隐藏状态。
     *
     * @param transaction
     *            用于对Fragment执行操作的事务*/

    private void hideFragments(FragmentTransaction transaction) {
        if (cameraActivity != null) {
            transaction.hide(cameraActivity);
        }
        if (settingFragment != null) {
            transaction.hide(settingFragment);
        }
       /* if (datefragment != null) {
            transaction.hide(datefragment);
        }*/
    }

    @Override
    protected void onRestart() {
        super.onRestart();

    }
}


   /* String[] permissions = new String[]{Manifest.permission.CAMERA , Manifest.permission.WRITE_EXTERNAL_STORAGE};
    List<String> mPermissionList = new ArrayList<>();

    private ImageView welcomeImg = null;
    private static final int PERMISSION_REQUEST = 1;
    *//**
     * 检查权限
     *//*
    private void checkPermission() {
        mPermissionList.clear();
        *//**
         * 判断哪些权限未授予
         *//*
        for (int i = 0; i < permissions.length; i++) {
            if (ContextCompat.checkSelfPermission(this, permissions[i]) != PackageManager.PERMISSION_GRANTED) {
                mPermissionList.add(permissions[i]);
            }
        }
        *//**
         * 判断是否为空
         *//*
        if (mPermissionList.isEmpty()) {//未授予的权限为空，表示都授予了
            initImage();
        } else {//请求权限方法
            String[] permissions = mPermissionList.toArray(new String[mPermissionList.size()]);//将List转为数组
            ActivityCompat.requestPermissions(MainActivity.this, permissions, PERMISSION_REQUEST);
        }
    }

    *//**
     * 响应授权
     * 这里不管用户是否拒绝，都进入首页，不再重复申请权限
     *//*
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_REQUEST:
                initImage();
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
                break;
        }
    }
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        checkPermission();

    }
    private void initImage(){
        welcomeImg = (ImageView)findViewById(R.id.welcome_img);
        welcomeImg.setBackgroundResource(R.drawable.tubiao);
        ScaleAnimation scaleAnimation =new ScaleAnimation( 1.4f, 1.0f, 1.4f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scaleAnimation.setDuration(1100);
        scaleAnimation.setFillAfter(true);
        scaleAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }
            @Override
            public void onAnimationEnd(Animation animation) {
                startActivity();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        welcomeImg.startAnimation(scaleAnimation);
    }

    private void startActivity(){
        Intent intent =new Intent(MainActivity.this,CameraActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.zoomin, R.anim.zoomout);
        finish();
    }*/




