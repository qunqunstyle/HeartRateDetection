//package com.example.heartratedect.Activity;
//
//import android.app.Activity;
//import android.content.Intent;
//import android.support.v7.app.AppCompatActivity;
//import android.os.Bundle;
//import android.widget.TextView;
//
//import com.example.heartratedect.R;
//
//public class ResultActivity extends Activity {
//    private TextView resultwave;
//    private TextView resultshow;
//    private TextView resultheartdateshow;
//    private TextView resulttext1;
//    private TextView resulttext2;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_result);
//        Intent intent = getIntent();
//        Bundle bundle = intent.getExtras();
//        String heartRate = bundle.getString("heartRate");
//        float[] data = bundle.getFloatArray("data");
//        resultheartdateshow=(TextView)findViewById(R.id.resultHeartRateShow);
//        resultshow = (TextView)findViewById(R.id.resultshow);
//        resulttext1 = (TextView)findViewById(R.id.resulttext1);
//        resulttext2 = (TextView)findViewById(R.id.resulttext2);
//        resultwave = (TextView)findViewById(R.id.resultwave1);
//
//        resultshow.setText("次/秒");
//        resultheartdateshow.setText(heartRate);
//        int heart =Integer.parseInt(heartRate);
//        if(heart<=60){
//            resulttext1.setText("您的心率属于亚健康水平");
//            resulttext2.setText("根据大数据分析得，您的心率偏低，请注意安全，并坚持量测");
//        }
//        if(heart>60 && heart<=100){
//            resulttext1.setText("您的心率属于健康水平");
//            resulttext2.setText("根据大数据分析得，您的心率属于健康水平，请注意保持，并坚持量测");
//        }
//        if(heart>100){
//            resulttext1.setText("您的心率属于亚健康水平");
//            resulttext2.setText("根据大数据分析得，您的心率偏高，请注意安全并就医咨询，同时还需坚持量测");
//        }
//
//    }
//
//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//        this.finish();
//    }
//}

package com.example.heartratedect.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.heartratedect.R;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.Utils;

import java.util.ArrayList;

import static com.example.heartratedect.FormatUtil.MAX_LENGTH;

public class ResultActivity extends AppCompatActivity implements OnChartGestureListener,
        OnChartValueSelectedListener{

    private TextView unitLabel;
    private TextView heartRateValueLabel;
    private TextView description;
    private TextView suggestion;
    private LineChart lineChart;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String heartRate = bundle.getString("heartRate");
        float[] bvp= bundle.getFloatArray("data");

        unitLabel = findViewById(R.id.unitLabel);
        heartRateValueLabel= findViewById(R.id.heartRateValueLabel);
        description = findViewById(R.id.description);
        suggestion = findViewById(R.id.suggestion);
        lineChart = findViewById(R.id.lineChart);

        unitLabel.setText("次/秒");
        heartRateValueLabel.setText(heartRate);
        description.setText("您的心率属于健康水平");
        suggestion.setText("根据大数据分析得，您的心率属于健康水平，请注意保持，并坚持量测");
        // Set listener of the chart.
        lineChart.setOnChartGestureListener(this);
        lineChart.setOnChartValueSelectedListener(this);
        //Set the attributes associated with the line chart.
        lineChart.setDrawGridBackground(false);
        lineChart.setTouchEnabled(false);
        lineChart.setDragEnabled(false);
        lineChart.setScaleEnabled(false);
        lineChart.setScaleXEnabled(true);
        lineChart.setScaleYEnabled(true);
        lineChart.setPinchZoom(true);
        lineChart.setDescription("");

        XAxis xAxis = lineChart.getXAxis();
        xAxis.setDrawAxisLine(false);
        YAxis leftAxis = lineChart.getAxisLeft();
        leftAxis.removeAllLimitLines();
        leftAxis.enableGridDashedLine(10f,10f,0f);
        leftAxis.setDrawZeroLine(false);
        leftAxis.setDrawLimitLinesBehindData(true);

        lineChart.getAxisRight().setEnabled(true);
        lineChart.getAxisLeft().setEnabled(true);
        lineChart.getXAxis().setEnabled(false);

        setLineChartData(bvp);
        lineChart.animateX(2500, Easing.EasingOption.EaseInOutQuart);
        Legend legend = lineChart.getLegend();
        legend.setForm(Legend.LegendForm.LINE);
    }

    private void setLineChartData(float[] bvp){
        ArrayList<String> xVals = new ArrayList<String>();
        ArrayList<Entry> yVals = new ArrayList<Entry>();
        for (int index = 1;index<MAX_LENGTH;index++){
            xVals.add((index)+"");
            yVals.add(new Entry(bvp[index],index));
        }
        LineDataSet lineDataSet = new LineDataSet(yVals,"BVP WAVE");
        lineDataSet.enableDashedLine(10f,5f,0f);
        lineDataSet.enableDashedHighlightLine(10f,5f,0f);
        lineDataSet.setColor(Color.BLACK);
        lineDataSet.setCircleColor(Color.BLACK);
        lineDataSet.setLineWidth(3f);
        lineDataSet.setCircleRadius(0f);
        lineDataSet.setDrawCircleHole(false);
        lineDataSet.setValueTextSize(0f);
        lineDataSet.setDrawFilled(false);

        if(Utils.getSDKInt()>=18){
            lineDataSet.setFillColor(Color.BLACK);
        }else{
            lineDataSet.setFillColor(Color.BLACK);
        }
        ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
        dataSets.add(lineDataSet);
        LineData data = new LineData(xVals,dataSets);
        lineChart.setData(data);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus){
        super.onWindowFocusChanged(hasFocus);
    }

    @Override
    public void onChartGestureStart(MotionEvent motionEvent,
                                    ChartTouchListener.ChartGesture lastPerformedGesture){
    }

    @Override
    public void onChartGestureEnd(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {
        Log.i("Gesture", "END, lastGesture: " + lastPerformedGesture);

        // un-highlight values after the gesture is finished and no single-tap
        if(lastPerformedGesture != ChartTouchListener.ChartGesture.SINGLE_TAP)
            lineChart.highlightValues(null); // or highlightTouch(null) for callback to onNothingSelected(...)
    }

    @Override
    public void onChartLongPressed(MotionEvent me) {
        Log.i("LongPress", "Chart longpressed.");
    }

    @Override
    public void onChartDoubleTapped(MotionEvent me) {
        Log.i("DoubleTap", "Chart double-tapped.");
    }

    @Override
    public void onChartSingleTapped(MotionEvent me) {
        Log.i("SingleTap", "Chart single-tapped.");
    }

    @Override
    public void onChartFling(MotionEvent me1, MotionEvent me2, float velocityX, float velocityY) {
        Log.i("Fling", "Chart flinged. VeloX: " + velocityX + ", VeloY: " + velocityY);
    }

    @Override
    public void onChartScale(MotionEvent me, float scaleX, float scaleY) {
        Log.i("Scale / Zoom", "ScaleX: " + scaleX + ", ScaleY: " + scaleY);
    }

    @Override
    public void onChartTranslate(MotionEvent me, float dX, float dY) {
        Log.i("Translate / Move", "dX: " + dX + ", dY: " + dY);
    }

    @Override
    public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
        Log.i("Entry selected", e.toString());
        Log.i("LOWHIGH", "low: " + lineChart.getLowestVisibleXIndex() + ", high: " + lineChart.getHighestVisibleXIndex());
        Log.i("MIN MAX", "xmin: " + lineChart.getXChartMin() + ", xmax: " + lineChart.getXChartMax() + ", ymin: " + lineChart.getYChartMin() + ", ymax: " + lineChart.getYChartMax());
    }

    @Override
    public void onNothingSelected() {
        Log.i("Nothing selected", "Nothing selected.");
    }


}