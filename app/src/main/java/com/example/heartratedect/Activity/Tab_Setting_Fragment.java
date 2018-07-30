package com.example.heartratedect.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.heartratedect.R;


public class Tab_Setting_Fragment extends Fragment {

	private RelativeLayout infoRelativeLayout;
	private RelativeLayout msgRelativeLayout;
	private RelativeLayout updateRelativeLayout;
	private RelativeLayout tucaoRelativeLayout;
	private RelativeLayout aboutRelativeLayout;
	private TextView tv_name;
	private Button btnLoginout;

//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		Bundle bundle = this.getIntent().getExtras();
//		if(bundle!=null && bundle.getString("from")!=null){
//			Util.showToast(s_instance, bundle.getString("from"), Toast.LENGTH_LONG);
//		}
//	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View SettingLayout = inflater.inflate(R.layout.tab_setting, container,
				false);
		tv_name = (TextView) SettingLayout.findViewById(R.id.tv_name);
		btnLoginout = (Button) SettingLayout.findViewById(R.id.btnLoginout);
		infoRelativeLayout = (RelativeLayout) SettingLayout
				.findViewById(R.id.infoRelativeLayout);
		msgRelativeLayout = (RelativeLayout) SettingLayout
				.findViewById(R.id.RealtiveLayout1);
		updateRelativeLayout = (RelativeLayout) SettingLayout
				.findViewById(R.id.RealtiveLayout2);
		tucaoRelativeLayout = (RelativeLayout) SettingLayout
				.findViewById(R.id.RealtiveLayout3);
		aboutRelativeLayout = (RelativeLayout) SettingLayout
				.findViewById(R.id.RealtiveLayout4);
		String result = "";
		Bundle bundle = getArguments();// 从activity传过来的Bundle
		if (bundle != null) {
			tv_name.setText(bundle.getString("userName"));
		}

		btnLoginout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Dialog alertDialog = new AlertDialog.Builder(getActivity())
						.setMessage("确定退出当前帐号并删除登录信息吗？")
						.setPositiveButton("确定",
								new DialogInterface.OnClickListener() {

									public void onClick(DialogInterface arg0,
														int arg1) {
										Intent intent = new Intent();
										intent.setClass(getActivity(),
												LoginActivity.class);
										startActivity(intent);
										getActivity().finish();
										SharedPreferences sharedPreferences = getActivity()
												.getSharedPreferences("test",
														Activity.MODE_PRIVATE);
										sharedPreferences.edit().clear().commit();
									}
								})
						.setNegativeButton("取消",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
														int which) {
										// TODO Auto-generated method stub
									}
								}).create();
				alertDialog.show();

			}
		});
		infoRelativeLayout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Toast.makeText(getActivity(), "暂未开发，敬请期待", Toast.LENGTH_SHORT)
						.show();
			}
		});
		msgRelativeLayout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent1 = new Intent();
				intent1.setClass(getActivity(), HistoryActivity.class);
				//getActivity().startActivityForResult(intent,1);
				startActivity(intent1);
			}
		});
		updateRelativeLayout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Toast.makeText(getActivity(), "暂未开发，敬请期待", Toast.LENGTH_SHORT)
				.show();
				/*Intent intent = new Intent(Intent.ACTION_MAIN);
				intent.addCategory(Intent.CATEGORY_LAUNCHER);
				ComponentName comp = new ComponentName("com.Personal.com", "com.Personal.com.UnityPlayerActivity");
				intent.setComponent(comp);
				int launchFlags = Intent.FLAG_ACTIVITY_NEW_TASK
						| Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED;
				intent.setFlags(launchFlags);
				intent.setAction("android.intent.action.VIEW");
				Bundle bundle = new Bundle();
				bundle.putString("from", "来自测试应用");
				intent.putExtras(bundle);
				startActivity(intent);*/

			}
		});
		tucaoRelativeLayout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Toast.makeText(getActivity(), "暂未开发，敬请期待", Toast.LENGTH_SHORT)
						.show();
				/*Intent intent = new Intent();
				intent.setClass(getActivity(), TuCaoActivity.class);
				intent.putExtra("userName", tv_name.getText().toString());
				startActivity(intent);*/
			}
		});
		aboutRelativeLayout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				Intent intent = new Intent();
				intent.setClass(getActivity(), AboutUsActivity.class);
				//getActivity().startActivityForResult(intent,1);
				startActivity(intent);
			}
		});
		return SettingLayout;
	}
}
