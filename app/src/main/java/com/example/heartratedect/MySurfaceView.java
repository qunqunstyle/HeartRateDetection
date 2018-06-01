package com.example.heartratedect;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Region;
import android.hardware.Camera;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceView;

/**
 * 圆形SurfaceView
 * 这个SurfaceView 使用时 必须设置其background，可以设置全透明背景
 * @author GqGAO
 */
public class MySurfaceView extends SurfaceView {

    private Paint paint;
    private int widthSize;
    private Camera camera;
    private int height; // 圆的半径



    public MySurfaceView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView();
    }

    public MySurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public MySurfaceView(Context context) {
        super(context);
        initView();
    }


    private void initView() {
        this.setFocusable(true);
        this.setFocusableInTouchMode(true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
         widthSize = MeasureSpec.getSize(widthMeasureSpec);
      //  int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int screenWidth = CommonUtils.getScreenWidth(getContext());
        int screenHeight = CommonUtils.getScreenHeight(getContext());
        height=600;
        double screenWidth1= 0.55*screenWidth;
        double screenHeight1= 0.55*screenHeight;
        Log.e("onMeasure", "widthSize="+widthSize);
        Log.e("onMeasure", "draw: widthMeasureSpec = " +screenWidth + "  heightMeasureSpec = " + screenHeight);
        setMeasuredDimension((int) screenWidth1, (int) screenHeight1);
        //setMeasuredDimension(widthSize, heightSize);



    }

    @Override
    public void draw(Canvas canvas) {
        Log.e("onDraw", "draw: test");
        Path path = new Path();

      /*  mpaint.setColor(white);
        mpaint.setStrokeWidth(50);
        canvas.drawCircle(height / 2, height / 2, height / 2,mpaint );*/
        path.addCircle(widthSize / 2, height / 2, height / 2, Path.Direction.CCW);
        canvas.clipPath(path, Region.Op.REPLACE);
        super.draw(canvas);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Log.e("onDraw", "onDraw");
        super.onDraw(canvas);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {

        int screenWidth = CommonUtils.getScreenWidth(getContext());
        int screenHeight = CommonUtils.getScreenHeight(getContext());
        Log.d("screenWidth",Integer.toString(screenWidth));
        Log.d("screenHeight",Integer.toString(screenHeight));
        w = screenWidth;
        h = screenHeight;
        super.onSizeChanged(w, h, oldw, oldh);

    }
}
