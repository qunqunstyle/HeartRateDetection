package com.example.heartratedect.Activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.heartratedect.R;

public class ResultActivity extends Activity {
    private TextView resultwave;
    private TextView resultshow;
    private TextView resultheartdateshow;
    private TextView resulttext1;
    private TextView resulttext2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String heartrate = bundle.getString("heartrate");
        resultheartdateshow=(TextView)findViewById(R.id.resultHeartRateShow);
        resultshow = (TextView)findViewById(R.id.resultshow);
        resulttext1 = (TextView)findViewById(R.id.resulttext1);
        resulttext2 = (TextView)findViewById(R.id.resulttext2);
        resultwave = (TextView)findViewById(R.id.resultwave1);
        resultwave.setText("boxing");
        resultshow.setText("次/秒");
        resultheartdateshow.setText(heartrate);
        int heart =Integer.parseInt(heartrate);
        if(heart<=60){
            resulttext1.setText("您的心率属于亚健康水平");
            resulttext2.setText("根据大数据分析得，您的心率偏低，请注意安全，并坚持量测");
        }
        if(heart>60 && heart<=100){
            resulttext1.setText("您的心率属于健康水平");
            resulttext2.setText("根据大数据分析得，您的心率属于健康水平，请注意保持，并坚持量测");
        }
        if(heart>100){
            resulttext1.setText("您的心率属于亚健康水平");
            resulttext2.setText("根据大数据分析得，您的心率偏高，请注意安全并就医咨询，同时还需坚持量测");
        }

    }

}
