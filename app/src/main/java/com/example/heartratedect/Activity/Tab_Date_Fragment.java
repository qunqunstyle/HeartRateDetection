package com.example.heartratedect.Activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.heartratedect.R;

public class Tab_Date_Fragment extends Fragment {

    /*private RelativeLayout wave;
    private RelativeLayout heartdate;*/

    private TextView wave1;
    private TextView show;
    private TextView heartdateshow;
    private TextView result1;
    private TextView result2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View DateLayout = inflater.inflate(R.layout.tab_date, container,
                false);
       /* wave = (RelativeLayout) DateLayout.findViewById(R.id.wave);
        heartdate = (RelativeLayout)DateLayout.findViewById(R.id.heartdate) ;*/
        wave1 = (TextView)DateLayout.findViewById(R.id.wave1);
        show = (TextView)DateLayout.findViewById(R.id.show1);
        heartdateshow = (TextView)DateLayout.findViewById(R.id.HeartRateShow1);
        result1 = (TextView)DateLayout.findViewById(R.id.result1);
        result2 = (TextView)DateLayout.findViewById(R.id.result2);
        show.setText("次/秒");
        heartdateshow.setText("70");
        result1.setText("您的心率属于健康水平");
        result2.setText("根据大数据分析得，您的心率属于健康水平，请注意保持，并坚持量测");

        /*Intent intent1= this.getActivity().getIntent();
        //Bundle bundle = this.getActivity().getIntent().getExtras();
        //Intent intent = Intent.getIntent();
       // String heartrate = bundle.getString("heartrate");
        int id = intent1.getIntExtra("id",0);
        if(id==1){
            Fragment fragment = new Fragment();
            FragmentManager fmanger = getFragmentManager();
            FragmentTransaction ftarn = fmanger.beginTransaction();
            ftarn.replace(R.id.content, fragment);
            ftarn.commit();
            info
        }
        String data = intent1.getStringExtra("heartrate");
        heartdateshow.setText(data);*/
        return DateLayout;
    }






}
