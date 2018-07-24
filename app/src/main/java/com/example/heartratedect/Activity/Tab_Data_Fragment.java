package com.example.heartratedect.Activity;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
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
import com.example.heartratedect.R;

import java.util.ArrayList;

//import static com.example.heartratedect.FormatUtil.MAX_LENGTH;

public class Tab_Data_Fragment extends Fragment{

    private TextView unitLabel;
    private TextView heartRateValueLabel;
    private TextView description;
    private TextView suggestion;
    private TextView suggestion1;
    private TextView suggestion2;
    private TextView suggestion3;
    private TextView suggestion4;
    private LineChart lineChart;

    @Override
    public  View onCreateView(LayoutInflater inflater, ViewGroup container,
                                  Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View dataLayout = inflater.inflate(R.layout.tab_data, container,
                false);


        unitLabel = (TextView)dataLayout.findViewById(R.id.unitLabel);
        heartRateValueLabel= (TextView)dataLayout.findViewById(R.id.heartRateValueLabel);
        description =(TextView)dataLayout. findViewById(R.id.description);
        suggestion = (TextView)dataLayout.findViewById(R.id.suggestion);
        suggestion1 = (TextView)dataLayout.findViewById(R.id.suggestion1);
        suggestion2 = (TextView)dataLayout.findViewById(R.id.suggestion2);
        suggestion3 = (TextView)dataLayout.findViewById(R.id.suggestion3);
        suggestion4 = (TextView)dataLayout.findViewById(R.id.suggestion4);
        lineChart = (LineChart) dataLayout.findViewById(R.id.chart1);

        unitLabel.setText("次/秒");
        heartRateValueLabel.setText("70 ");
        description.setText("您的心率属于健康水平");
        suggestion.setText("根据大数据分析得，您的心率.........属于健康水平，请注意保持，并坚持量测");
        suggestion1.setText("根据大数据分析得，您的心率.........属于健康水平，请注意保持，并坚持量测");
        suggestion2.setText("根据大数据分析得，您的心率.........属于健康水平，请注意保持，并坚持量测");
      /*  // Set listener of the chart.
        lineChart.setOnChartGestureListener(getContext());
        lineChart.setOnChartValueSelectedListener(this);
        //Set the attributes associated with the line chart.
        lineChart.setDrawGridBackground(false);
        lineChart.setTouchEnabled(false);
        lineChart.setDragEnabled(false);
        lineChart.setScaleEnabled(false);
        lineChart.setScaleXEnabled(true);
        lineChart.setScaleYEnabled(true);
        lineChart.setPinchZoom(true);*/

      return dataLayout;
    }

    /*private void setLineChartData(float[] bvp){
        ArrayList<String> xVals = new ArrayList<String>();
        ArrayList<Entry> yVals = new ArrayList<Entry>();
        for (int index = 1;index<MAX_LENGTH;index++){
            xVals.add((index)+"");
            yVals.add(new Entry(bvp[index],index));
        }
        LineDataSet lineDataSet = new LineDataSet(yVals,"");
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
    }*/
/*

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
*/


}